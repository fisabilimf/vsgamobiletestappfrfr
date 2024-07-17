package com.example.vsgatestmobileapp1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vsgatestmobileapp1.database.NotesDatabaseHelper;
import com.example.vsgatestmobileapp1.databinding.ActivityNoteEditorBinding;

public class NoteEditorActivity extends AppCompatActivity {

    private ActivityNoteEditorBinding binding;
    private NotesDatabaseHelper dbHelper;
    private boolean isEditMode = false;
    private long noteId = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNoteEditorBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        dbHelper = new NotesDatabaseHelper(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("note_id")) {
            isEditMode = true;
            noteId = intent.getLongExtra("note_id", -1);
            loadNoteDetails(noteId);
        }

        binding.buttonSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void loadNoteDetails(long noteId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {
                NotesDatabaseHelper.COLUMN_TITLE,
                NotesDatabaseHelper.COLUMN_CONTENT
        };
        String selection = NotesDatabaseHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(noteId)};

        Cursor cursor = db.query(NotesDatabaseHelper.TABLE_NOTES, columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow(NotesDatabaseHelper.COLUMN_TITLE));
            String content = cursor.getString(cursor.getColumnIndexOrThrow(NotesDatabaseHelper.COLUMN_CONTENT));
            binding.inputNoteTitle.setText(title);
            binding.inputNoteContent.setText(content);
            cursor.close();
        }
    }

    private void saveNote() {
        String title = binding.inputNoteTitle.getText().toString().trim();
        String content = binding.inputNoteContent.getText().toString().trim();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NotesDatabaseHelper.COLUMN_TITLE, title);
        values.put(NotesDatabaseHelper.COLUMN_CONTENT, content);

        long newRowId;
        if (isEditMode) {
            newRowId = db.update(NotesDatabaseHelper.TABLE_NOTES, values, NotesDatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(noteId)});
        } else {
            newRowId = db.insert(NotesDatabaseHelper.TABLE_NOTES, null, values);
        }

        if (newRowId == -1) {
            Toast.makeText(this, "Error saving note", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
            finish();
        }
        db.close();
    }
}
