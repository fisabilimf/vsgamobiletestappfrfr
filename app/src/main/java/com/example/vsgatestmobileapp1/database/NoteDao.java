package com.example.vsgatestmobileapp1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.vsgatestmobileapp1.model.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NoteDao {
    private SQLiteDatabase database;
    private NoteDatabaseHelper dbHelper;
    private String[] allColumns = {
            NoteDatabaseHelper.COLUMN_ID,
            NoteDatabaseHelper.COLUMN_TITLE,
            NoteDatabaseHelper.COLUMN_SUBTITLE,
            NoteDatabaseHelper.COLUMN_CONTENT,
            NoteDatabaseHelper.COLUMN_IMAGE_URL,
            NoteDatabaseHelper.COLUMN_DATE_CREATED,
            NoteDatabaseHelper.COLUMN_DATE_UPDATED,
            NoteDatabaseHelper.COLUMN_USERNAME
    };

    public NoteDao(Context context) {
        dbHelper = new NoteDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public Note createOrUpdateNote(String title, String subtitle, String content, String imageUrl, String username) {
        Note existingNote = getNoteByTitle(title);

        if (existingNote != null) {
            // Update existing note
            existingNote.setSubtitle(subtitle);
            existingNote.setContent(content);
            existingNote.setImageUrl(imageUrl);
            existingNote.setUsername(username);
            existingNote.setDateUpdated(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
            updateNote(existingNote);
            return existingNote;
        } else {
            // Create new note
            ContentValues values = new ContentValues();
            values.put(NoteDatabaseHelper.COLUMN_TITLE, title);
            values.put(NoteDatabaseHelper.COLUMN_SUBTITLE, subtitle);
            values.put(NoteDatabaseHelper.COLUMN_CONTENT, content);
            values.put(NoteDatabaseHelper.COLUMN_IMAGE_URL, imageUrl);
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            values.put(NoteDatabaseHelper.COLUMN_DATE_CREATED, date);
            values.put(NoteDatabaseHelper.COLUMN_DATE_UPDATED, date);
            values.put(NoteDatabaseHelper.COLUMN_USERNAME, username);

            long insertId = database.insert(NoteDatabaseHelper.TABLE_NOTES, null, values);
            Cursor cursor = database.query(NoteDatabaseHelper.TABLE_NOTES, allColumns,
                    NoteDatabaseHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
            cursor.moveToFirst();
            Note newNote = cursorToNote(cursor);
            cursor.close();
            return newNote;
        }
    }

    public void updateNote(Note note) {
        long id = note.getId();
        ContentValues values = new ContentValues();
        values.put(NoteDatabaseHelper.COLUMN_TITLE, note.getTitle());
        values.put(NoteDatabaseHelper.COLUMN_SUBTITLE, note.getSubtitle());
        values.put(NoteDatabaseHelper.COLUMN_CONTENT, note.getContent());
        values.put(NoteDatabaseHelper.COLUMN_IMAGE_URL, note.getImageUrl());
        values.put(NoteDatabaseHelper.COLUMN_DATE_UPDATED, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
        values.put(NoteDatabaseHelper.COLUMN_USERNAME, note.getUsername());

        database.update(NoteDatabaseHelper.TABLE_NOTES, values, NoteDatabaseHelper.COLUMN_ID + " = " + id, null);
    }

    public void deleteNote(Note note) {
        long id = note.getId();
        database.delete(NoteDatabaseHelper.TABLE_NOTES, NoteDatabaseHelper.COLUMN_ID + " = " + id, null);
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        Cursor cursor = database.query(NoteDatabaseHelper.TABLE_NOTES, allColumns, null, null, null, null, NoteDatabaseHelper.COLUMN_DATE_CREATED + " DESC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Note note = cursorToNote(cursor);
            notes.add(note);
            cursor.moveToNext();
        }
        cursor.close();
        return notes;
    }

    private Note cursorToNote(Cursor cursor) {
        Note note = new Note();
        note.setId(cursor.getLong(0));
        note.setTitle(cursor.getString(1));
        note.setSubtitle(cursor.getString(2));
        note.setContent(cursor.getString(3));
        note.setImageUrl(cursor.getString(4));
        note.setDateCreated(cursor.getString(5));
        note.setDateUpdated(cursor.getString(6));
        note.setUsername(cursor.getString(7));
        return note;
    }

    private Note getNoteByTitle(String title) {
        Cursor cursor = database.query(NoteDatabaseHelper.TABLE_NOTES, allColumns,
                NoteDatabaseHelper.COLUMN_TITLE + " = ?", new String[]{title}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Note note = cursorToNote(cursor);
            cursor.close();
            return note;
        } else {
            return null;
        }
    }
}
