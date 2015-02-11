package com.example.TestAndroid.l12;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by ZOG on 1/19/2015.
 */
public class MyIntentService extends IntentService {

    private static final String TAG = "intent_service";

    public MyIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String dataString = intent.getDataString();
        Log.d(TAG, "Got data: " + dataString);
    }
}
