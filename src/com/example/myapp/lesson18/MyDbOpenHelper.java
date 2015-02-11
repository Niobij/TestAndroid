package com.example.myapp.lesson18;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDbOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "db_contact";
    private static final int DATABASE_VERSION = 1;

    public static final String DICTIONARY_TABLE_NAME = "marker";

    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String IMAGE_NAME = "image";
    public static final String MARKER_TITLE = "title";

    private static final String DICTIONARY_TABLE_CREATE =
            "CREATE TABLE " + DICTIONARY_TABLE_NAME + " (" +
                    LATITUDE + " TEXT, " +
                    LONGITUDE + " TEXT, " +
                    MARKER_TITLE + " TEXT, " +
                    IMAGE_NAME + " TEXT)";




    public MyDbOpenHelper(Context _context) {
        super(_context, DB_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("tag", "On create database");
        sqLiteDatabase.execSQL(DICTIONARY_TABLE_CREATE);


    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
