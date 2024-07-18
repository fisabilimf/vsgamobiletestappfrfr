package com.example.vsgatestmobileapp1.ui.dailynotes;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.navigation.Navigation;

import com.example.vsgatestmobileapp1.R;
import com.example.vsgatestmobileapp1.adapter.NotesAdapter;
import com.example.vsgatestmobileapp1.model.Note;
import com.example.vsgatestmobileapp1.databinding.FragmentDailyNotesBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class DailyNotesFragment extends Fragment {

    private static final int REQUEST_WRITE_STORAGE = 112;
    private FragmentDailyNotesBinding binding;
    private NotesViewModel notesViewModel;
    private NotesAdapter notesAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notesViewModel =
                new ViewModelProvider(this).get(NotesViewModel.class);

        binding = FragmentDailyNotesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerViewNotes;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        notesAdapter = new NotesAdapter();
        recyclerView.setAdapter(notesAdapter);

        FloatingActionButton fab = binding.fabAddNote;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_dailyNotesFragment_to_noteEditorActivity);
            }
        });

        notesAdapter.setOnItemClickListener(new NotesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(getActivity(), NoteDetailActivity.class);
                intent.putExtra(NoteDetailActivity.EXTRA_NOTE_ID, note.getId());
                intent.putExtra(NoteDetailActivity.EXTRA_NOTE_TITLE, note.getTitle());
                intent.putExtra(NoteDetailActivity.EXTRA_NOTE_SUBTITLE, note.getSubtitle());
                intent.putExtra(NoteDetailActivity.EXTRA_NOTE_CONTENT, note.getContent());
                intent.putExtra(NoteDetailActivity.EXTRA_NOTE_IMAGE_URL, note.getImageUrl());
                intent.putExtra(NoteDetailActivity.EXTRA_NOTE_USERNAME, note.getUsername());
                intent.putExtra(NoteDetailActivity.EXTRA_NOTE_DATE_CREATED, note.getDateCreated());
                intent.putExtra(NoteDetailActivity.EXTRA_NOTE_DATE_UPDATED, note.getDateUpdated());
                startActivity(intent);
            }

            @Override
            public void onEditClick(Note note) {
                Intent intent = new Intent(getActivity(), NoteEditorActivity.class);
                intent.putExtra(NoteEditorActivity.EXTRA_NOTE_ID, note.getId());
                intent.putExtra(NoteEditorActivity.EXTRA_NOTE_TITLE, note.getTitle());
                intent.putExtra(NoteEditorActivity.EXTRA_NOTE_SUBTITLE, note.getSubtitle());
                intent.putExtra(NoteEditorActivity.EXTRA_NOTE_CONTENT, note.getContent());
                intent.putExtra(NoteEditorActivity.EXTRA_NOTE_IMAGE_URL, note.getImageUrl());
                intent.putExtra(NoteEditorActivity.EXTRA_NOTE_USERNAME, note.getUsername());
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Note note) {
                notesViewModel.deleteNote(note);
            }
        });

        notesViewModel.getNotes().observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                notesAdapter.setNotes(notes);
            }
        });

        // Button to generate TXT file
        binding.buttonGenerateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionsAndGenerateTxt();
            }
        });

        // Button to read TXT file
        binding.buttonReadTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TxtReaderActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        notesViewModel.loadNotes(); // Reload the notes when the fragment is resumed
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void checkPermissionsAndGenerateTxt() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
        } else {
            generateNotesTxtFile(getContext(), notesViewModel.getNotes().getValue());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                generateNotesTxtFile(getContext(), notesViewModel.getNotes().getValue());
            } else {
                Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void generateNotesTxtFile(Context context, List<Note> notes) {
        if (notes == null || notes.isEmpty()) {
            Toast.makeText(context, "No notes to save", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(context.getExternalFilesDir(null), "notes.txt");

        try (FileOutputStream fos = new FileOutputStream(file)) {
            for (Note note : notes) {
                String noteData = "Title: " + note.getTitle() + "\n" +
                        "Subtitle: " + note.getSubtitle() + "\n" +
                        "Content: " + note.getContent() + "\n" +
                        "Image URL: " + note.getImageUrl() + "\n" +
                        "Username: " + note.getUsername() + "\n" +
                        "Date Created: " + note.getDateCreated() + "\n" +
                        "Date Updated: " + note.getDateUpdated() + "\n\n";
                fos.write(noteData.getBytes());
            }
            Toast.makeText(context, "Notes saved to " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(context, "Failed to save notes", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
