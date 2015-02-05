package com.example.TestAndroid.l14;

import android.util.Log;

/**
 * Created by ZOG on 1/26/2015.
 */
abstract class Worker {

    protected static void doWork() {
        for (int i = 0; i < L14Activity.STEPS; i++) {
            try {

                Thread.sleep(1000);
                Log.d(L14Activity.TAG, "Working... step " + i);
                if (i + 1 == L14Activity.STEPS) Log.d(L14Activity.TAG, "Work finished");

            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.d(L14Activity.TAG, "Work interrupted on step " + i);
                return;
            }
        }
    }

}
