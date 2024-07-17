package com.example.vsgatestmobileapp1.ui.dailynotes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vsgatestmobileapp1.R;
import com.example.vsgatestmobileapp1.database.NoteDao;
import com.google.android.material.textfield.TextInputEditText;

public class NoteEditorActivity extends AppCompatActivity {
    private TextInputEditText editTextTitle;
    private TextInputEditText editTextSubtitle;
    private TextInputEditText editTextContent;
    private ImageView imageViewNote;
    private Button buttonSaveNote;
    private NoteDao noteDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        editTextTitle = findViewById(R.id.input_note_title);
        editTextSubtitle = findViewById(R.id.input_note_subtitle);
        editTextContent = findViewById(R.id.input_note_content);
        imageViewNote = findViewById(R.id.image_view_note);
        buttonSaveNote = findViewById(R.id.button_save_note);

        noteDao = new NoteDao(this);

        buttonSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String subtitle = editTextSubtitle.getText().toString();
        String content = editTextContent.getText().toString();
        String imageUrl = ""; // Handle image selection/uploading
        String username = "User"; // Replace with actual username handling

        noteDao.createNote(title, subtitle, content, imageUrl, username);

        finish();
    }
}
