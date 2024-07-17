package com.example.vsgatestmobileapp1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vsgatestmobileapp1.R;
import com.example.vsgatestmobileapp1.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteHolder> {
    private List<Note> notes = new ArrayList<>();

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = notes.get(position);
        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewSubtitle.setText(currentNote.getSubtitle());
        holder.textViewDateCreated.setText(currentNote.getDateCreated());
        holder.textViewUsername.setText(currentNote.getUsername());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewSubtitle;
        private TextView textViewDateCreated;
        private TextView textViewUsername;

        public NoteHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewSubtitle = itemView.findViewById(R.id.text_view_subtitle);
            textViewDateCreated = itemView.findViewById(R.id.text_view_date_created);
            textViewUsername = itemView.findViewById(R.id.text_view_username);
        }
    }
}
