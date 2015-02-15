package com.example.TestAndroid.l19;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vasiliy on 19.01.2015.
 */
public class MyDbOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "db_pushes";
    private static final int DATABASE_VERSION = 1;

    public static final String PUSHES_TABLE_NAME = "pushes";

    public static final String KEY_MESSAGE = "key_message";
    public static final String KEY_TITLE = "key_title";
    public static final String KEY_SUBTITLE = "key_subtitle";
    public static final String KEY_TICKER_TEXT = "key_tickerText";
    public static final String KEY_VIBRATE = "key_vibrate";
    public static final String KEY_SOUND = "key_sound";

    private static final String PUSHES_TABLE_CREATE =
            "CREATE TABLE " + PUSHES_TABLE_NAME + " (" +
                    KEY_MESSAGE + " TEXT, " +
                    KEY_TITLE + " TEXT, " +
                    KEY_SUBTITLE + " TEXT, " +
                    KEY_TICKER_TEXT + " TEXT, " +
                    KEY_VIBRATE + " TEXT, " +
                    KEY_SOUND + " TEXT)";

    public MyDbOpenHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(PUSHES_TABLE_CREATE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
