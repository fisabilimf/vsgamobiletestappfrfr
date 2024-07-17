package com.example.vsgatestmobileapp1.ui.dailynotes;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.vsgatestmobileapp1.database.NoteDao;
import com.example.vsgatestmobileapp1.model.Note;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {
    private MutableLiveData<List<Note>> notesLiveData;
    private NoteDao noteDao;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        noteDao = new NoteDao(application);
        notesLiveData = new MutableLiveData<>();
        loadNotes();
    }

    public LiveData<List<Note>> getNotes() {
        return notesLiveData;
    }

    public void loadNotes() {
        List<Note> notes = noteDao.getAllNotes();
        notesLiveData.postValue(notes);
    }

    public void addNote(Note note) {
        long id = noteDao.createNote(note);
        if (id != -1) {
            loadNotes(); // Refresh the notes list
        } else {
            Log.d("NotesViewModel", "Failed to add note");
        }
    }

    public void updateNote(Note note) {
        int rowsAffected = noteDao.updateNote(note);
        if (rowsAffected > 0) {
            loadNotes(); // Refresh the notes list
        } else {
            Log.d("NotesViewModel", "Failed to update note");
        }
    }

    public void deleteNote(Note note) {
        noteDao.deleteNote(note);
        loadNotes(); // Refresh the notes list
    }
}
