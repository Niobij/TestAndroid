package com.example.myapp.lesson19;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDbOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "db_not";
    private static final int DATABASE_VERSION = 1;

    public static final String DICTIONARY_TABLE_NAME = "notification";

    public static final String MESSAGE = "latitude";
    public static final String TITLE = "longitude";
    public static final String SUBTITLE = "image";
    public static final String TICKER_TEXT = "ticker";
    public static final String VIBRATE = "vibrate";
    public static final String SOUND = "sound";

    private static final String DICTIONARY_TABLE_CREATE =
            "CREATE TABLE " + DICTIONARY_TABLE_NAME + " (" +
                    MESSAGE + " TEXT, " +
                    TITLE + " TEXT, " +
                    SUBTITLE + " TEXT, " +
                    TICKER_TEXT + " TEXT, " +
                    VIBRATE + " TEXT, " +
                    SOUND + " TEXT)";




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
