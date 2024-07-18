package com.example.vsgatestmobileapp1.ui.dailynotes;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vsgatestmobileapp1.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TxtReaderActivity extends AppCompatActivity {

    private TextView textViewNotesContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_txt_reader);

        textViewNotesContent = findViewById(R.id.text_view_notes_content);

        readNotesFromFile();
    }

    private void readNotesFromFile() {
        File file = new File(getExternalFilesDir(null), "notes.txt");

        if (!file.exists()) {
            Toast.makeText(this, "No notes file found", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder text = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e) {
            Toast.makeText(this, "Failed to read notes", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        textViewNotesContent.setText(text.toString());
    }
}
