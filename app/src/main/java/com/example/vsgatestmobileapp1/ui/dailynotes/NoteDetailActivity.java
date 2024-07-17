package com.example.vsgatestmobileapp1.ui.dailynotes;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.ImageView;

import com.example.vsgatestmobileapp1.R;
import com.example.vsgatestmobileapp1.model.Note;

public class NoteDetailActivity extends AppCompatActivity {
    public static final String EXTRA_NOTE_ID = "com.example.vsgatestmobileapp1.EXTRA_NOTE_ID";
    public static final String EXTRA_NOTE_TITLE = "com.example.vsgatestmobileapp1.EXTRA_NOTE_TITLE";
    public static final String EXTRA_NOTE_SUBTITLE = "com.example.vsgatestmobileapp1.EXTRA_NOTE_SUBTITLE";
    public static final String EXTRA_NOTE_CONTENT = "com.example.vsgatestmobileapp1.EXTRA_NOTE_CONTENT";
    public static final String EXTRA_NOTE_IMAGE_URL = "com.example.vsgatestmobileapp1.EXTRA_NOTE_IMAGE_URL";
    public static final String EXTRA_NOTE_USERNAME = "com.example.vsgatestmobileapp1.EXTRA_NOTE_USERNAME";
    public static final String EXTRA_NOTE_DATE_CREATED = "com.example.vsgatestmobileapp1.EXTRA_NOTE_DATE_CREATED";
    public static final String EXTRA_NOTE_DATE_UPDATED = "com.example.vsgatestmobileapp1.EXTRA_NOTE_DATE_UPDATED";

    private TextView textViewTitle;
    private TextView textViewSubtitle;
    private TextView textViewContent;
    private ImageView imageViewNote;
    private TextView textViewDateCreated;
    private TextView textViewDateUpdated;
    private TextView textViewUsername;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        textViewTitle = findViewById(R.id.text_view_title);
        textViewSubtitle = findViewById(R.id.text_view_subtitle);
        textViewContent = findViewById(R.id.text_view_content);
        imageViewNote = findViewById(R.id.image_view_note);
        textViewDateCreated = findViewById(R.id.text_view_date_created);
        textViewDateUpdated = findViewById(R.id.text_view_date_updated);
        textViewUsername = findViewById(R.id.text_view_username);

        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra(EXTRA_NOTE_TITLE);
            String subtitle = intent.getStringExtra(EXTRA_NOTE_SUBTITLE);
            String content = intent.getStringExtra(EXTRA_NOTE_CONTENT);
            String imageUrl = intent.getStringExtra(EXTRA_NOTE_IMAGE_URL);
            String username = intent.getStringExtra(EXTRA_NOTE_USERNAME);
            String dateCreated = intent.getStringExtra(EXTRA_NOTE_DATE_CREATED);
            String dateUpdated = intent.getStringExtra(EXTRA_NOTE_DATE_UPDATED);

            textViewTitle.setText(title);
            textViewSubtitle.setText(subtitle);
            textViewContent.setText(content);
            textViewUsername.setText(username);
            textViewDateCreated.setText(dateCreated);
            textViewDateUpdated.setText(dateUpdated);

            // Load the image from the URL (use a library like Glide or Picasso)
            // Glide.with(this).load(imageUrl).into(imageViewNote);
        }
    }
}
