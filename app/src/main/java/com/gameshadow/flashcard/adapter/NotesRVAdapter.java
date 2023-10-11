package com.gameshadow.flashcard.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.gameshadow.flashcard.Note;
import com.gameshadow.flashcard.R;
import com.gameshadow.flashcard.RecyclerClickListener;

public class NotesRVAdapter extends ListAdapter<Note, NotesRVAdapter.NoteHolder> {

    private RecyclerClickListener listener;

    public NotesRVAdapter(RecyclerClickListener listener) {
        super(new DiffCallback());
        this.listener = listener;
    }


    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_row, parent, false);
        final NoteHolder noteHolder = new NoteHolder(v);

        ImageView noteDelete = noteHolder.itemView.findViewById(R.id.note_delete);

        noteDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemRemoveClick(noteHolder.getAdapterPosition());
            }
        });

        CardView note = noteHolder.itemView.findViewById(R.id.note);
        TextView note_text = noteHolder.itemView.findViewById(R.id.note_text);

        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note_text.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(parent.getContext(), R.anim.middle_animation);
                note_text.setAnimation(animation);
                listener.onItemClick(noteHolder.getAdapterPosition());
            }
        });
        note.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onItemLongClick(noteHolder.getAdapterPosition());
                return false;
            }
        });


        return noteHolder;
    }


    @Override
    public void onBindViewHolder(NoteHolder holder, int position) {
        Note currentItem = getItem(position);
//        Log.d("tag====>",currentItem.getNoteBG()+"");
//        Log.d("tag====>",currentItem.getNoteText());
//        Log.d("tag====>",currentItem.getDateAdded().getDate()+"");
        TextView noteText = holder.itemView.findViewById(R.id.note_text);
        TextView noteDate = holder.itemView.findViewById(R.id.note_date);
        TextView note_question = holder.itemView.findViewById(R.id.note_question);
        noteText.setVisibility(View.GONE);
        noteDate.setText(currentItem.getDateAdded());
        CardView note = holder.itemView.findViewById(R.id.note);
        note.setCardBackgroundColor(currentItem.getNoteBG());
        noteText.setText(currentItem.getNoteText());
        note_question.setText(currentItem.getNoteQuestion());
    }


    static class NoteHolder extends RecyclerView.ViewHolder {
        NoteHolder(View view) {
            super(view);
        }
    }

    static class DiffCallback extends DiffUtil.ItemCallback<Note> {
        @Override
        public boolean areItemsTheSame(Note oldItem, Note newItem) {
            return oldItem.getDateAdded() == newItem.getDateAdded();
        }

        @Override
        public boolean areContentsTheSame(Note oldItem, Note newItem) {
            return oldItem.equals(newItem);
        }
    }
}