package com.example.vsgatestmobileapp1.ui.dailynotes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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

import java.util.List;

public class DailyNotesFragment extends Fragment {

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

        notesViewModel.getNotes().observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                notesAdapter.setNotes(notes);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
