package com.example.TestAndroid.l11;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ZOG on 1/15/2015.
 */
public class MyDbOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "db_name";
    private static final int DATABASE_VERSION = 1;

    public static final String DICTIONARY_TABLE_NAME = "dictionary";

    public static final String KEY_WORD = "key_word";
    public static final String KEY_DEFINITION = "key_definition";

    private static final String DICTIONARY_TABLE_CREATE =
            "CREATE TABLE " + DICTIONARY_TABLE_NAME + " (" +
                    KEY_WORD + " TEXT, " +
                    KEY_DEFINITION + " TEXT)";


    public MyDbOpenHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DICTIONARY_TABLE_CREATE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
