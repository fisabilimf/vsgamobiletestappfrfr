package com.example.vsgatestmobileapp1.ui.dailynotes;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.vsgatestmobileapp1.database.NoteDao;
import com.example.vsgatestmobileapp1.model.Note;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    private NoteDao noteDao;
    private MutableLiveData<List<Note>> notesLiveData;

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
        notesLiveData.setValue(noteDao.getAllNotes());
    }

    public void addOrUpdateNote(Note note) {
        noteDao.createOrUpdateNote(note.getTitle(), note.getSubtitle(), note.getContent(), note.getImageUrl(), note.getUsername());
        loadNotes();
    }

    public void deleteNote(Note note) {
        noteDao.deleteNote(note);
        loadNotes();
    }
}
