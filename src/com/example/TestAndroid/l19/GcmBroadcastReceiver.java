package com.example.TestAndroid.l19;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by ZOG on 2/12/2015.
 */
public class GcmBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(L19Activity.TAG, "Got message: " + intent);
		L19Activity.showNotification(context, intent);
	}

}
