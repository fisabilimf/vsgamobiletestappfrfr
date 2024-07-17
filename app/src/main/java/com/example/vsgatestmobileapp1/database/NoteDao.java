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

    public long createNote(Note note) {
        ContentValues values = new ContentValues();
        values.put(NoteDatabaseHelper.COLUMN_TITLE, note.getTitle());
        values.put(NoteDatabaseHelper.COLUMN_SUBTITLE, note.getSubtitle());
        values.put(NoteDatabaseHelper.COLUMN_CONTENT, note.getContent());
        values.put(NoteDatabaseHelper.COLUMN_IMAGE_URL, note.getImageUrl());
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        values.put(NoteDatabaseHelper.COLUMN_DATE_CREATED, date);
        values.put(NoteDatabaseHelper.COLUMN_DATE_UPDATED, date);
        values.put(NoteDatabaseHelper.COLUMN_USERNAME, note.getUsername());

        return database.insert(NoteDatabaseHelper.TABLE_NOTES, null, values);
    }

    public int updateNote(Note note) {
        ContentValues values = new ContentValues();
        values.put(NoteDatabaseHelper.COLUMN_TITLE, note.getTitle());
        values.put(NoteDatabaseHelper.COLUMN_SUBTITLE, note.getSubtitle());
        values.put(NoteDatabaseHelper.COLUMN_CONTENT, note.getContent());
        values.put(NoteDatabaseHelper.COLUMN_IMAGE_URL, note.getImageUrl());
        values.put(NoteDatabaseHelper.COLUMN_DATE_UPDATED, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
        values.put(NoteDatabaseHelper.COLUMN_USERNAME, note.getUsername());

        return database.update(NoteDatabaseHelper.TABLE_NOTES, values, NoteDatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(note.getId())});
    }

    public void deleteNote(Note note) {
        database.delete(NoteDatabaseHelper.TABLE_NOTES, NoteDatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(note.getId())});
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        Cursor cursor = database.query(NoteDatabaseHelper.TABLE_NOTES, allColumns, null, null, null, null, NoteDatabaseHelper.COLUMN_DATE_CREATED + " DESC");

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Note note = cursorToNote(cursor);
                notes.add(note);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return notes;
    }

    private Note cursorToNote(Cursor cursor) {
        Note note = new Note();
        note.setId(cursor.getLong(cursor.getColumnIndexOrThrow(NoteDatabaseHelper.COLUMN_ID)));
        note.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(NoteDatabaseHelper.COLUMN_TITLE)));
        note.setSubtitle(cursor.getString(cursor.getColumnIndexOrThrow(NoteDatabaseHelper.COLUMN_SUBTITLE)));
        note.setContent(cursor.getString(cursor.getColumnIndexOrThrow(NoteDatabaseHelper.COLUMN_CONTENT)));
        note.setImageUrl(cursor.getString(cursor.getColumnIndexOrThrow(NoteDatabaseHelper.COLUMN_IMAGE_URL)));
        note.setDateCreated(cursor.getString(cursor.getColumnIndexOrThrow(NoteDatabaseHelper.COLUMN_DATE_CREATED)));
        note.setDateUpdated(cursor.getString(cursor.getColumnIndexOrThrow(NoteDatabaseHelper.COLUMN_DATE_UPDATED)));
        note.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(NoteDatabaseHelper.COLUMN_USERNAME)));
        return note;
    }
}
