package com.example.TestAndroid.l16;

import com.example.TestAndroid.l16.models.SolarSystemModel;

/**
 * Created by ZOG on 2/2/2015.
 */
public interface OnDataLoadListener {
    public void onSuccess(SolarSystemModel _model);
    public void onFailure();
}
