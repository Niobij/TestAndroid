package com.example.myapp;


import com.example.myapp.models.SolarSystemModel;

public interface OnDataLoadListener {
    public void onSuccess(SolarSystemModel _model);
    public void onFailure();
}
