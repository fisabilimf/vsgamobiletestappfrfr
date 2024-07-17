package com.example.vsgatestmobileapp1;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vsgatestmobileapp1.database.DatabaseHelper;
import com.example.vsgatestmobileapp1.databinding.ActivityRegisterBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        dbHelper = new DatabaseHelper(this);

        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        binding.editTextTextDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        binding.editTextTextDateOfBirth.setText(date);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void registerUser() {
        String email = binding.editTextTextEmailAddress.getText().toString().trim();
        String username = binding.editTextTextUsername.getText().toString().trim();
        String dob = binding.editTextTextDateOfBirth.getText().toString().trim();
        String password = binding.editTextTextPassword.getText().toString().trim();

        int selectedGenderId = binding.radioGroupGender.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedGenderId);
        String gender = selectedRadioButton.getText().toString();

        // Validate inputs
        if (email.isEmpty() || username.isEmpty() || dob.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get current timestamp
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Calendar.getInstance().getTime());

        // Save to database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_EMAIL, email);
        values.put(DatabaseHelper.COLUMN_USERNAME, username);
        values.put(DatabaseHelper.COLUMN_DOB, dob);
        values.put(DatabaseHelper.COLUMN_GENDER, gender);
        values.put(DatabaseHelper.COLUMN_PASSWORD, password);
        values.put(DatabaseHelper.COLUMN_CREATED_AT, currentTime);
        values.put(DatabaseHelper.COLUMN_UPDATED_AT, currentTime);

        long newRowId = db.insert(DatabaseHelper.TABLE_USERS, null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show();

            // Save username to SharedPreferences
            SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("is_logged_in", true);
            editor.putString("user_email", email);
            editor.putString("user_username", username);
            editor.apply();

            // Redirect to LoginActivity
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Error registering user", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
}
