package com.example.myapp;

import android.graphics.Bitmap;

/**
 * Created by Vasia on 04.02.2015.
 */
public interface OnImageLoadListener {

    public void onLoadImage(int position, Bitmap bitmap);
    public void onFailureLoadImage(int position);
}
