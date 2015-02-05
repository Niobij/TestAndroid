package com.example.TestAndroid.l13;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by ZOG on 1/22/2015.
 */
public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(L13Activity.TAG, "MyReceiver#onReceive: intent: " + intent);

        String action = intent.getAction();

        if (action.equals(L13Activity.actionStartService)) {
            MyObject myObject = (MyObject) intent.getSerializableExtra("name");

            Intent startIntent = new Intent(context, MyBroadcastService.class);
            context.startService(startIntent);

        } else if (action.equals(L13Activity.actionStopService)) {
            Intent stopIntent = new Intent(context, MyBroadcastService.class);
            context.stopService(stopIntent);
        }
    }

}
