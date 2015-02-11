package com.example.TestAndroid.l14;

import android.os.AsyncTask;
import android.widget.TextView;

/**
 * Created by ZOG on 1/26/2015.
 */
public class MyAsyncTask extends AsyncTask<String, Void, String> {

    private TextView textView;

    public MyAsyncTask(TextView _textView) {
        textView = _textView;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        textView.setText("Before task");
    }


    @Override
    protected String doInBackground(String... strings) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return strings[0];
    }


    @Override
    protected void onPostExecute(String str) {
        super.onPostExecute(str);
        textView.setText("After task, result: " + str);
    }
}
