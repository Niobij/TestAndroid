package com.example.myapp.lesson16;


import com.example.myapp.lesson16.models.SolarSystemModel;

public interface OnDataLoadListener {
    public void onSuccess(SolarSystemModel _model);
    public void onFailure();
}
