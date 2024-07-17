package com.example.vsgatestmobileapp1.ui.dailynotes;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.view.View;
import android.widget.ImageView;
import androidx.lifecycle.ViewModelProvider;

import com.example.vsgatestmobileapp1.R;
import com.example.vsgatestmobileapp1.model.Note;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NoteEditorActivity extends AppCompatActivity {
    public static final String EXTRA_NOTE_ID = "com.example.vsgatestmobileapp1.EXTRA_NOTE_ID";
    public static final String EXTRA_NOTE_TITLE = "com.example.vsgatestmobileapp1.EXTRA_NOTE_TITLE";
    public static final String EXTRA_NOTE_SUBTITLE = "com.example.vsgatestmobileapp1.EXTRA_NOTE_SUBTITLE";
    public static final String EXTRA_NOTE_CONTENT = "com.example.vsgatestmobileapp1.EXTRA_NOTE_CONTENT";
    public static final String EXTRA_NOTE_IMAGE_URL = "com.example.vsgatestmobileapp1.EXTRA_NOTE_IMAGE_URL";
    public static final String EXTRA_NOTE_USERNAME = "com.example.vsgatestmobileapp1.EXTRA_NOTE_USERNAME";

    private TextInputEditText editTextTitle;
    private TextInputEditText editTextSubtitle;
    private TextInputEditText editTextContent;
    private ImageView imageViewNote;
    private Button buttonSaveNote;
    private NotesViewModel notesViewModel;
    private Note note;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        editTextTitle = findViewById(R.id.input_note_title);
        editTextSubtitle = findViewById(R.id.input_note_subtitle);
        editTextContent = findViewById(R.id.input_note_content);
        imageViewNote = findViewById(R.id.image_view_note);
        buttonSaveNote = findViewById(R.id.button_save_note);

        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_NOTE_ID)) {
            note = new Note();
            note.setId(intent.getIntExtra(EXTRA_NOTE_ID, 0));
            editTextTitle.setText(intent.getStringExtra(EXTRA_NOTE_TITLE));
            editTextSubtitle.setText(intent.getStringExtra(EXTRA_NOTE_SUBTITLE));
            editTextContent.setText(intent.getStringExtra(EXTRA_NOTE_CONTENT));
            note.setImageUrl(intent.getStringExtra(EXTRA_NOTE_IMAGE_URL));
            note.setUsername(intent.getStringExtra(EXTRA_NOTE_USERNAME));
        } else {
            note = new Note();
        }

        buttonSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void saveNote() {
        String title = editTextTitle.getText().toString().trim();
        String subtitle = editTextSubtitle.getText().toString().trim();
        String content = editTextContent.getText().toString().trim();
        String imageUrl = ""; // Update this if you implement image URL logic
        String username = ""; // Update this to actual username

        note.setTitle(title);
        note.setSubtitle(subtitle);
        note.setContent(content);
        note.setImageUrl(imageUrl);
        note.setUsername(username);
        note.setDateUpdated(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));

        if (note.getId() == 0) {
            note.setDateCreated(note.getDateUpdated());
            notesViewModel.addNote(note);
        } else {
            notesViewModel.updateNote(note);
        }

        finish();
    }
}
