package com.example.vsgatestmobileapp1.ui.dailynotes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vsgatestmobileapp1.R;
import com.example.vsgatestmobileapp1.database.NoteDao;
import com.example.vsgatestmobileapp1.model.Note;
import com.google.android.material.button.MaterialButton;

public class NoteEditorActivity extends AppCompatActivity {

    public static final String EXTRA_NOTE_ID = "com.example.vsgatestmobileapp1.ui.dailynotes.EXTRA_NOTE_ID";
    public static final String EXTRA_NOTE_TITLE = "com.example.vsgatestmobileapp1.ui.dailynotes.EXTRA_NOTE_TITLE";
    public static final String EXTRA_NOTE_SUBTITLE = "com.example.vsgatestmobileapp1.ui.dailynotes.EXTRA_NOTE_SUBTITLE";
    public static final String EXTRA_NOTE_CONTENT = "com.example.vsgatestmobileapp1.ui.dailynotes.EXTRA_NOTE_CONTENT";
    public static final String EXTRA_NOTE_IMAGE_URL = "com.example.vsgatestmobileapp1.ui.dailynotes.EXTRA_NOTE_IMAGE_URL";
    public static final String EXTRA_NOTE_USERNAME = "com.example.vsgatestmobileapp1.ui.dailynotes.EXTRA_NOTE_USERNAME";

    private EditText editTextTitle;
    private EditText editTextSubtitle;
    private EditText editTextContent;
    private ImageView imageViewNote;
    private NoteDao noteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        editTextTitle = findViewById(R.id.input_note_title);
        editTextSubtitle = findViewById(R.id.input_note_subtitle);
        editTextContent = findViewById(R.id.input_note_content);
        imageViewNote = findViewById(R.id.image_view_note);

        noteDao = new NoteDao(this);

        MaterialButton buttonSave = findViewById(R.id.button_save_note);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

        // Load existing note data if editing
        if (getIntent().hasExtra(EXTRA_NOTE_ID)) {
            editTextTitle.setText(getIntent().getStringExtra(EXTRA_NOTE_TITLE));
            editTextSubtitle.setText(getIntent().getStringExtra(EXTRA_NOTE_SUBTITLE));
            editTextContent.setText(getIntent().getStringExtra(EXTRA_NOTE_CONTENT));
            // Load the image URL if necessary
        }
    }

    private void saveNote() {
        String title = editTextTitle.getText().toString().trim();
        String subtitle = editTextSubtitle.getText().toString().trim();
        String content = editTextContent.getText().toString().trim();
        String imageUrl = ""; // Implement image URL handling as needed
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String username = preferences.getString("user_username", "Username");

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
            Toast.makeText(this, "Please enter a title and content", Toast.LENGTH_SHORT).show();
            return;
        }

        noteDao.createOrUpdateNote(title, subtitle, content, imageUrl, username);
        Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}
