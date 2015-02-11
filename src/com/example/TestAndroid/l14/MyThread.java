package com.example.TestAndroid.l14;

import android.util.Log;

/**
 * Created by ZOG on 1/26/2015.
 */
public class MyThread extends Thread {

    @Override
    public void run() {
        Log.d(L14Activity.TAG, "MyThread#run");
        Worker.doWork();
    }

}
