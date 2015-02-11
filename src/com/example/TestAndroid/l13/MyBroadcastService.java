package com.example.TestAndroid.l13;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by ZOG on 1/22/2015.
 */
public class MyBroadcastService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(L13Activity.TAG, "MyBroadcastService#onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(L13Activity.TAG, "MyBroadcastService#onStartCommand");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(L13Activity.TAG, "MyBroadcastService#onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
