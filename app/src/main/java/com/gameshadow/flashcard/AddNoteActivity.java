package com.gameshadow.flashcard;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@SuppressLint("RestrictedApi")
public class AddNoteActivity extends AppCompatActivity {
    private RelativeLayout addNoteBackground;
    private View viewColor, viewFontColor;
    private Button selectColor,selectFont, selectFontColor;
    private SeekBar fontSlider;
    private LinearLayout addNoteWindowBg;
    private TextView add_note_delete, addNoteText;
    private TextView close_Button;
    private NoteDao noteDatabase;
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());
    private DeleteClickListener deleteClickListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        addNoteText = findViewById(R.id.add_note_text);
        addNoteBackground = findViewById(R.id.add_note_background);
        addNoteWindowBg = findViewById(R.id.add_note_window_bg);
        selectColor = findViewById(R.id.btnSelectColor);
        viewColor = findViewById(R.id.viewColor);
        add_note_delete = findViewById(R.id.add_note_delete);
        selectFont = findViewById(R.id.btnSelectFont);
        fontSlider = findViewById(R.id.fontSlider);
        selectFontColor = findViewById(R.id.btnSelectFontColor);
        viewFontColor = findViewById(R.id.viewFontColor);

        noteDatabase = NoteDatabase.getDatabase(this).noteDao();
        String from = getIntent().getStringExtra("from");
        if (from.equals("new")) {
            add_note_delete.setVisibility(View.GONE);
        } else {
            add_note_delete.setVisibility(View.VISIBLE);
        }
        Note notedata = (Note) getIntent().getSerializableExtra("data");
        String noteDateAdded = getIntent().getStringExtra("note_date_added");
        String noteTextToEdit = getIntent().getStringExtra("note_text");
        String note_question = getIntent().getStringExtra("note_question");
        int noteBG = getIntent().getIntExtra("noteBG", 0);
        if (noteBG != 0) {
            viewColor.setBackgroundColor(noteBG);
        }
        addNoteText = findViewById(R.id.add_note_text);

        TextView add_note_question = findViewById(R.id.add_note_question);
        addNoteText.setText(noteTextToEdit != null ? noteTextToEdit : "");
        add_note_question.setText(note_question != null ? note_question : "");

        Button addNoteButton = findViewById(R.id.add_note_button);
        close_Button = findViewById(R.id.close_Button);
        Intent parentintent = new Intent(AddNoteActivity.this, ParentNotes.class);
        Intent studentIntent = new Intent(AddNoteActivity.this, StudentNotes.class);
        close_Button.setOnClickListener(v -> {
            if (Users.getIsAdmin()) {
                startActivity(parentintent);
            } else {
                startActivity(studentIntent);
            }

        });
        addNoteButton.setOnClickListener(v -> {
            if (add_note_question.getText().toString().trim().isEmpty()) {
                Toast.makeText(AddNoteActivity.this, "Please enter note", Toast.LENGTH_SHORT).show();
                return;
            }
            if (addNoteText.getText().toString().trim().isEmpty()) {
                Toast.makeText(AddNoteActivity.this, "Please enter note", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent data = new Intent();
            data.putExtra("note_date_added", noteDateAdded);
            data.putExtra("note_text", addNoteText.getText().toString().trim());
            data.putExtra("note_question", add_note_question.getText().toString().trim());
            data.putExtra("from", "edit");
            Drawable background = viewColor.getBackground();
            int color = 0;
            if (background instanceof ColorDrawable)
                color = ((ColorDrawable) background).getColor();
            data.putExtra("noteBG", color);

            setResult(Activity.RESULT_OK, data);
            onBackPressed();
        });
        selectColor.setOnClickListener(v -> showColorPickerDialog(AddNoteActivity.this));
        add_note_delete.setOnClickListener(v -> {
            Intent data = new Intent();
            data.putExtra("note_date_added", noteDateAdded);
            data.putExtra("note_text", addNoteText.getText().toString().trim());
            data.putExtra("note_question", add_note_question.getText().toString().trim());
            data.putExtra("from", "delete");
            data.putExtra("data", notedata);
            Drawable background = viewColor.getBackground();
            int color = 0;
            if (background instanceof ColorDrawable)
                color = ((ColorDrawable) background).getColor();
            data.putExtra("noteBG", color);

            setResult(Activity.RESULT_OK, data);
            onBackPressed();
        });
        fontSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update the font size of the EditText based on the SeekBar progress
                addNoteText.setTextSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Handle the start of SeekBar tracking (if needed)
                // For example, you can show a toast message to indicate tracking has stopped.
                // Toast.makeText(YourActivityName.this, "Tracking started", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Handle the end of SeekBar tracking (if needed)
                // For example, you can show a toast message to indicate tracking has stopped.
                // Toast.makeText(YourActivityName.this, "Tracking stopped", Toast.LENGTH_SHORT).show();
            }
        });
        selectFont.setOnClickListener(v -> {
            // Implement font selection behavior (show a dialog to choose a font)
            showFontSizeDialog();
        });
        selectFontColor.setOnClickListener(v -> showColorPickerDialog(AddNoteActivity.this));

    }
    @Override
    public void onBackPressed() {
        finish();
    }

    private void showColorPickerDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Pick a Color");

        // Use the provided 'context' to access resources
        String[] colorNames = context.getResources().getStringArray(R.array.colors);
        int[] colors = context.getResources().getIntArray(R.array.colors);

        ArrayAdapter<String> colorAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, colorNames) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);

                TypedArray ta = context.getResources().obtainTypedArray(R.array.colors); // Use context.getResources()

                int colorToUse = ta.getResourceId(position, 0);

                // Setting the color on a dummy TextView as a demo
                textView.setText(colorNames[position]);
                textView.setBackgroundResource(colorToUse);

                return textView;
            }
        };
        builder.setAdapter(colorAdapter, (dialog, which) -> {
//                TypedArray ta = getResources().obtainTypedArray(R.array.colors);
            int selectedColor = colors[which];
            TypedArray ta = getResources().obtainTypedArray(R.array.colors);
            int colorToUse = ta.getColor(which, 0);
            Log.d("tag====>", colorToUse + "");
            Log.d("tag====>", selectedColor + "");

            // Do something with the selected color
            viewColor.setBackgroundColor(selectedColor);
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void showFontSizeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Font Size");

        // Define a list of font size options (you can customize this list)
        String[] fontSizes = {"Small", "Medium", "Large"};

        builder.setItems(fontSizes, (dialog, which) -> {
            // Handle the selection of a font size
            int fontSize = getSelectedFontSize(which);
            addNoteText.setTextSize(fontSize);
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private int getSelectedFontSize(int position) {
        int[] fontSizes = {16, 24, 32, 40, 48}; // Define your desired font sizes
        if (position >= 0 && position < fontSizes.length) {
            return fontSizes[position]; // Return the selected font size
        } else {
            return getResources().getDimensionPixelSize(R.dimen.font_size_default); // Default font size
        }
    }
}