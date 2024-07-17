package com.example.vsgatestmobileapp1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "notes.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NOTES = "notes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_SUBTITLE = "subtitle";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_IMAGE_URL = "image_url";
    public static final String COLUMN_DATE_CREATED = "date_created";
    public static final String COLUMN_DATE_UPDATED = "date_updated";
    public static final String COLUMN_USERNAME = "username";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NOTES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_SUBTITLE + " TEXT, " +
                    COLUMN_CONTENT + " TEXT, " +
                    COLUMN_IMAGE_URL + " TEXT, " +
                    COLUMN_DATE_CREATED + " TEXT, " +
                    COLUMN_DATE_UPDATED + " TEXT, " +
                    COLUMN_USERNAME + " TEXT" +
                    ")";

    public NoteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }
}
