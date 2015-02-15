package com.example.myapp.lesson19;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class GcmBroadcastReceiver extends BroadcastReceiver {

    @Override
	public void onReceive(Context context, Intent intent) {
		Log.d(L19Activity.TAG, "Got message: " + intent);
		L19Activity.parseIntent(intent);
	}

}
