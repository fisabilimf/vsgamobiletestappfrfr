package com.example.vsgatestmobileapp1.ui.dailynotes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vsgatestmobileapp1.NoteEditorActivity;
import com.example.vsgatestmobileapp1.R;
import com.example.vsgatestmobileapp1.adapter.NotesAdapter;
import com.example.vsgatestmobileapp1.database.NotesDatabaseHelper;
import com.example.vsgatestmobileapp1.databinding.FragmentDailyNotesBinding;
import com.example.vsgatestmobileapp1.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DailyNotesFragment extends Fragment {

    private FragmentDailyNotesBinding binding;
    private NotesDatabaseHelper dbHelper;
    private NotesAdapter notesAdapter;
    private List<Note> noteList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDailyNotesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        dbHelper = new NotesDatabaseHelper(getContext());
        noteList = new ArrayList<>();
        notesAdapter = new NotesAdapter(noteList);

        RecyclerView recyclerView = binding.recyclerViewNotes;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(notesAdapter);

        FloatingActionButton fab = binding.fabAddNote;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NoteEditorActivity.class);
                startActivity(intent);
            }
        });

        loadNotes();

        return view;
    }

    private void loadNotes() {
        noteList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                NotesDatabaseHelper.TABLE_NOTES,
                null,
                null,
                null,
                null,
                null,
                NotesDatabaseHelper.COLUMN_TIMESTAMP + " DESC"
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(NotesDatabaseHelper.COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(NotesDatabaseHelper.COLUMN_TITLE));
                String content = cursor.getString(cursor.getColumnIndexOrThrow(NotesDatabaseHelper.COLUMN_CONTENT));
                String timestamp = cursor.getString(cursor.getColumnIndexOrThrow(NotesDatabaseHelper.COLUMN_TIMESTAMP));
                noteList.add(new Note(id, title, content, timestamp));
            }
            cursor.close();
        }

        notesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
