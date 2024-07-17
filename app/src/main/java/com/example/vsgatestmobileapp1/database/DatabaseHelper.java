package com.example.vsgatestmobileapp1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 2; // Increment the version number

    // Table name
    public static final String TABLE_USERS = "users";

    // Table columns
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_DOB = "dob";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_CREATED_AT = "created_at";
    public static final String COLUMN_UPDATED_AT = "updated_at";

    // SQL query to create the table
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_DOB + " TEXT, " +
                    COLUMN_GENDER + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT, " +
                    COLUMN_CREATED_AT + " TEXT, " +
                    COLUMN_UPDATED_AT + " TEXT" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_CREATED_AT + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_UPDATED_AT + " TEXT");
        }
    }
}
