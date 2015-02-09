package com.example.TestAndroid.l18;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * Created by ZOG on 2/4/2015.
 */
public class MyLocationListener implements LocationListener {

	public Activitly_L18 activity;

	@Override
	public void onLocationChanged(Location location) {
		activity.llMapControl.setVisibility(View.VISIBLE);
        Log.d(Activitly_L18.class.getSimpleName(), "Location: " + location);
    }

	@Override
	public void onStatusChanged(String s, int i, Bundle bundle) {

	}

	@Override
	public void onProviderEnabled(String s) {

	}

	@Override
	public void onProviderDisabled(String s) {

	}
}
