package com.example.vsgatestmobileapp1.ui.dailynotes;

import android.app.Application;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.vsgatestmobileapp1.database.NoteDatabaseHelper;
import com.example.vsgatestmobileapp1.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NotesViewModel extends AndroidViewModel {
    private NoteDatabaseHelper dbHelper;
    private MutableLiveData<List<Note>> notesLiveData;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        dbHelper = new NoteDatabaseHelper(application);
        notesLiveData = new MutableLiveData<>();
        loadNotes();
    }

    public LiveData<List<Note>> getNotes() {
        return notesLiveData;
    }

    private void loadNotes() {
        List<Note> notes = new ArrayList<>();
        Cursor cursor = dbHelper.getReadableDatabase().query(
                NoteDatabaseHelper.TABLE_NOTES,
                null,
                null,
                null,
                null,
                null,
                NoteDatabaseHelper.COLUMN_DATE_CREATED + " DESC"
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(NoteDatabaseHelper.COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(NoteDatabaseHelper.COLUMN_TITLE));
                String subtitle = cursor.getString(cursor.getColumnIndexOrThrow(NoteDatabaseHelper.COLUMN_SUBTITLE));
                String content = cursor.getString(cursor.getColumnIndexOrThrow(NoteDatabaseHelper.COLUMN_CONTENT));
                String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(NoteDatabaseHelper.COLUMN_IMAGE_URL));
                String dateCreated = cursor.getString(cursor.getColumnIndexOrThrow(NoteDatabaseHelper.COLUMN_DATE_CREATED));
                String dateUpdated = cursor.getString(cursor.getColumnIndexOrThrow(NoteDatabaseHelper.COLUMN_DATE_UPDATED));
                String username = cursor.getString(cursor.getColumnIndexOrThrow(NoteDatabaseHelper.COLUMN_USERNAME));

                Note note = new Note();
                note.setId(id);
                note.setTitle(title);
                note.setSubtitle(subtitle);
                note.setContent(content);
                note.setImageUrl(imageUrl);
                note.setDateCreated(dateCreated);
                note.setDateUpdated(dateUpdated);
                note.setUsername(username);

                notes.add(note);
            }
            cursor.close();
        }

        notesLiveData.setValue(notes);
    }
}
