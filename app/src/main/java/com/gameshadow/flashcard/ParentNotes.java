package com.gameshadow.flashcard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.gameshadow.flashcard.adapter.NotesRVAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParentNotes extends AppCompatActivity {

    private NotesRVAdapter adapter;
    private NoteDao ParentDatabase;
    private NoteDao notedatabase;
    private TextView noRecordFound,tvCount;
    private ViewPager2 viewPager2;
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());

    private final ActivityResultLauncher<Intent> newNoteResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        String noteDateAdded = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.getDefault()).format(new Date());
                        Log.d("tag====>", noteDateAdded + "");
                        String noteText = result.getData().getStringExtra("note_text");
                        String note_question = result.getData().getStringExtra("note_question");
                        int noteBG = result.getData().getIntExtra("noteBG", 0);
                        Note newNote = new Note(noteDateAdded, noteText != null ? noteText : "", noteBG,note_question);
                        executor.execute(() -> {
                            //Background work here
                            ParentDatabase.addNote(newNote);
                            notedatabase.addNote(newNote);
                        });
                    }
                }
            });
    private final ActivityResultLauncher<Intent> editNoteResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        String noteDateAdded = result.getData().getStringExtra("note_date_added");
                        String noteText = result.getData().getStringExtra("note_text");
                        String note_question = result.getData().getStringExtra("note_question");

                        String from = result.getData().getStringExtra("from");
                        if (Objects.equals(from, "edit")) {
                            int noteBG = result.getData().getIntExtra("noteBG", 0);
                            Note editedNote = new Note(noteDateAdded, noteText != null ? noteText : "", noteBG, note_question);
                            executor.execute(() -> {
                                //Background work here
                                ParentDatabase.updateNote(editedNote);
                                notedatabase.updateNote(editedNote);
                            });
                        }
                        else {
                            Note data = (Note) result.getData().getSerializableExtra("data");
                            List<Note> notesList = new ArrayList<>(adapter.getCurrentList());

                            notesList.remove(data);
                            adapter.submitList(notesList);
                            executor.execute(() -> {
                                //Background work here
                                ParentDatabase.deleteNote(data);
                                notedatabase.deleteNote(data);
                            });
                        }
                    }
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        ParentDatabase = NoteDatabase.getDatabase(this).noteDao();
        FloatingActionButton floating_action_button = findViewById(R.id.floating_action_button);
        noRecordFound = findViewById(R.id.noRecordFound);
        tvCount = findViewById(R.id.tvCount);
        Button btnLeft = findViewById(R.id.btnLeft);
        Button btnRight = findViewById(R.id.btnRight);

        viewPager2 = findViewById(R.id.viewPager);
        viewPager2.setUserInputEnabled(false);

        //Toolbar for Menu
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Student database?
        notedatabase = (NoteDao) notedatabase.getNotes();

        //Click Listeners
        btnLeft.setOnClickListener(v -> viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1, true));
        btnRight.setOnClickListener(v -> viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1, true));
        setRecyclerView();
        observeNotes();

        floating_action_button.setOnClickListener(v -> {
            Intent intent = new Intent(ParentNotes.this, AddNoteActivity.class);
            intent.putExtra("from","new");
            newNoteResultLauncher.launch(intent);
        });


        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (adapter.getItemCount() > 0) {
                    tvCount.setText(position + 1 + "/" + adapter.getItemCount());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }
    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_notes, menu);
        return true;
    }
    //Menu Options - https://developer.android.com/develop/ui/views/components/menus#java
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add_note_menu:
                Intent intent = new Intent(ParentNotes.this, AddNoteActivity.class);
                intent.putExtra("from","new");
                newNoteResultLauncher.launch(intent);
                return true;
            case R.id.logout_menu:
                Intent intent2 = new Intent(ParentNotes.this, GoodbyeSplash.class);
                intent2.putExtra("from","new");
                newNoteResultLauncher.launch(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setRecyclerView() {
        adapter = new NotesRVAdapter(new RecyclerClickListener() {
            @Override
            public void onItemRemoveClick(int position) {
                List<Note> notesList = new ArrayList<>(adapter.getCurrentList());
                Note removeNote = notesList.get(position);
                notesList.remove(position);
                adapter.submitList(notesList);
                executor.execute(() -> {
                    //Background work here
                    ParentDatabase.deleteNote(removeNote);
                    notedatabase.deleteNote(removeNote);
                });
            }

            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onItemLongClick(int position) {
                Intent intent = new Intent(ParentNotes.this, AddNoteActivity.class);
                List<Note> notesList = new ArrayList<>(adapter.getCurrentList());
                intent.putExtra("data",notesList.get(position));
                intent.putExtra("from","edit");
                intent.putExtra("note_date_added", notesList.get(position).getDateAdded());
                intent.putExtra("note_text", notesList.get(position).getNoteText());
                intent.putExtra("noteBG", notesList.get(position).getNoteBG());
                intent.putExtra("note_question", notesList.get(position).getNoteQuestion());
                editNoteResultLauncher.launch(intent);
            }
        });
        viewPager2.setAdapter(adapter);
    }

    private void observeNotes() {
        executor.execute(() -> {
            //Background work here
            handler.post(() -> {
                //UI Thread work here
                ParentDatabase.getNotes().observe(this, notesList -> {
                    if (notesList != null && !notesList.isEmpty()) {
                        adapter.submitList(notesList);
                        tvCount.setText(viewPager2.getCurrentItem()+1+"/"+notesList.size());
                        noRecordFound.setVisibility(View.GONE);
                    } else {
                        tvCount.setText(viewPager2.getCurrentItem()+"/0");
                        adapter.submitList(notesList);
                        noRecordFound.setVisibility(View.VISIBLE);
                    }
                });
            });
        });
    }
}
