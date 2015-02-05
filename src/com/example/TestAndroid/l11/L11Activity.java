package com.example.TestAndroid.l11;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.TestAndroid.R;

import java.io.*;

/**
 * Created by ZOG on 1/15/2015.
 */
public class L11Activity extends Activity implements View.OnClickListener {

    private static final String PREFS_NAME = "preferences";
    private static final String FILENAME = "my_file";

    private EditText etKey;
    private EditText etValue;
    private Button btnSave;
    private Button btnRead;
    private TextView tvSpData;

    private EditText etFileData;
    private Button btnWriteFile;
    private Button btnReadFile;
    private TextView tvFileData;

    private EditText etWord;
    private EditText etDefinition;
    private Button btnAddDb;
    private Button btnReadDb;
    private Button btnUpdate;
    private TextView tvDbData;

    private MyDbOpenHelper mMyDbOpenHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l11);

        findViews();
        setListeners();

        mMyDbOpenHelper = new MyDbOpenHelper(this);
    }

    private void findViews() {
        etKey = (EditText) findViewById(R.id.etKey);
        etValue = (EditText) findViewById(R.id.etValue);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnRead = (Button) findViewById(R.id.btnRead);
        tvSpData = (TextView) findViewById(R.id.tvSpData);

        etFileData = (EditText) findViewById(R.id.etFileData);
        btnWriteFile = (Button) findViewById(R.id.btnWriteFile);
        btnReadFile = (Button) findViewById(R.id.btnReadFile);
        tvFileData = (TextView) findViewById(R.id.tvFileData);

        etWord = (EditText) findViewById(R.id.etWord);
        etDefinition = (EditText) findViewById(R.id.etDefinition);
        btnAddDb = (Button) findViewById(R.id.btnAddDb);
        btnReadDb = (Button) findViewById(R.id.btnReadDb);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        tvDbData = (TextView) findViewById(R.id.tvDbData);
    }

    private void setListeners() {
        btnSave.setOnClickListener(this);
        btnRead.setOnClickListener(this);
        btnWriteFile.setOnClickListener(this);
        btnReadFile.setOnClickListener(this);
        btnAddDb.setOnClickListener(this);
        btnReadDb.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                onClickBtnSave();
                break;

            case R.id.btnRead:
                onClickBtnRead();
                break;

            case R.id.btnWriteFile:
                onClickBtnWriteFileExternal();
                break;

            case R.id.btnReadFile:
                onClickBtnReadFileExternal();
                break;

            case R.id.btnAddDb:
                onClickBtnAddDb();
                break;

            case R.id.btnReadDb:
                onClickBtnReadDb();
                break;

            case R.id.btnUpdate:
                onClickBtnUpdateDb();
                break;
        }
    }

    //region Shared Preferences
    private void onClickBtnSave() {
        final String key = etKey.getText().toString();
        final String value = etValue.getText().toString();

        if (key.isEmpty() || value.isEmpty()) {
            Toast.makeText(this, "Fill key and value", Toast.LENGTH_SHORT).show();
            return;
        }

//        SharedPreferences sharedPreferences = getPreferences(Activity.MODE_PRIVATE);
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private void onClickBtnRead() {
        final String key = etKey.getText().toString();

        if (key.isEmpty()) {
            Toast.makeText(this, "Fill key", Toast.LENGTH_SHORT).show();
            return;
        }

//        SharedPreferences sp = getPreferences(Activity.MODE_PRIVATE);
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        String value = sp.getString(key, "Not found");
        tvSpData.setText(value);
    }
    //endregion

    //region Files
    private void onClickBtnWriteFile() {
        String data = etFileData.getText().toString();

        if (data.isEmpty()) {
            Toast.makeText(this, "Fill file data", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(getFilesDir(), FILENAME);

        try {
//            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            FileOutputStream fos = openFileOutput(file.getName(), Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void onClickBtnReadFile() {
        try {
            File file = new File(getFilesDir(), FILENAME);

//            FileInputStream fis = openFileInput(FILENAME);
            FileInputStream fis = openFileInput(file.getName());

            StringBuilder sb = new StringBuilder();
            int letter;

            while ((letter = fis.read()) != -1) {
                sb.append(((char) letter));
            }
            fis.close();

            tvFileData.setText(sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onClickBtnWriteFileExternal() {
        String data = etFileData.getText().toString();

        if (data.isEmpty()) {
            Toast.makeText(this, "Fill file data", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(Environment.getExternalStorageDirectory(), FILENAME);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(data.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void onClickBtnReadFileExternal() {
        File file = new File(Environment.getExternalStorageDirectory(), FILENAME);

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            StringBuilder sb = new StringBuilder();
            int content;
            while ((content = fis.read()) != -1) {
                sb.append((char) content);
            }
            fis.close();

            tvFileData.setText(sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //endregion

    //region Database
    private void onClickBtnAddDb() {
        String word = etWord.getText().toString();
        String definition = etDefinition.getText().toString();

        if (word.isEmpty() || definition.isEmpty()) {
            Toast.makeText(this, "Fill word and definition", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = mMyDbOpenHelper.getWritableDatabase();

//        ContentValues contentValues = new ContentValues();
//        contentValues.put(MyDbOpenHelper.KEY_WORD, word);
//        contentValues.put(MyDbOpenHelper.KEY_DEFINITION, definition);
//
//        db.insert(
//                MyDbOpenHelper.DICTIONARY_TABLE_NAME,
//                null,
//                contentValues);

        String insert = "INSERT INTO " + MyDbOpenHelper.DICTIONARY_TABLE_NAME + " (" +
                MyDbOpenHelper.KEY_WORD + ", " +
                MyDbOpenHelper.KEY_DEFINITION + ") VALUES (" +
                "'" + word + "', '" + definition + "')";

        db.execSQL(insert);
        db.close();
    }

    private void onClickBtnReadDb() {
        SQLiteDatabase db = mMyDbOpenHelper.getReadableDatabase();

        String[] projection = {
                MyDbOpenHelper.KEY_WORD,
                MyDbOpenHelper.KEY_DEFINITION
        };

//        Cursor c = db.query(
//                MyDbOpenHelper.DICTIONARY_TABLE_NAME,
//                projection,
//                null,
//				null,
//				null,
//				null,
//				null);

        String select = "SELECT * FROM " + MyDbOpenHelper.DICTIONARY_TABLE_NAME;
        Cursor c = db.rawQuery(select, null);

        StringBuilder sb = new StringBuilder();
        int wordIndex = c.getColumnIndex(MyDbOpenHelper.KEY_WORD);
        int definitionIndex = c.getColumnIndex(MyDbOpenHelper.KEY_DEFINITION);

        c.moveToFirst();
        while (c.moveToNext()) {
            String word = c.getString(wordIndex);
            String definition = c.getString(definitionIndex);
            sb.append(word + ": " + definition + "\n");
        }
        db.close();

        tvDbData.setText(sb.toString());
    }

    private void onClickBtnUpdateDb() {
        String word = etWord.getText().toString();
        String definition = etDefinition.getText().toString();

        if (word.isEmpty() || definition.isEmpty()) {
            Toast.makeText(this, "Fill word and definition", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = mMyDbOpenHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MyDbOpenHelper.KEY_WORD, word);
        values.put(MyDbOpenHelper.KEY_DEFINITION, definition);

        db.update(MyDbOpenHelper.DICTIONARY_TABLE_NAME,
                values,
                MyDbOpenHelper.KEY_WORD + " = '" + word + "'",
                null);
        db.close();
    }
    //endregion

}