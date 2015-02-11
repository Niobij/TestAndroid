package com.example.TestAndroid.l14;

import android.util.Log;

/**
 * Created by ZOG on 1/26/2015.
 */
public class MyRunnable implements Runnable {

    @Override
    public void run() {
        Log.d(L14Activity.TAG, "MyRunnable#run");
        Worker.doWork();
    }

}
