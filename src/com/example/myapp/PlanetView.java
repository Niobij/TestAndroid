package com.example.myapp;

import android.content.Context;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.myapp.models.PlanetModel;


public class PlanetView extends RelativeLayout {

    private ImageView ivPlanetImg;
    private TextView tvPlanetData;
    private ProgressBar progressBar;

    public PlanetView(Context context) {
        super(context);

        inflate(context, R.layout.view_planet, this);

        findViews();
    }

    private void findViews() {
        ivPlanetImg = (ImageView) findViewById(R.id.ivPlanetImg_VP);
        tvPlanetData = (TextView) findViewById(R.id.tvPlanetData_VP);
        progressBar = (ProgressBar)findViewById(R.id.progressLoadImage);
    }

    public void setPlanetModel(PlanetModel _model) {
        if (_model.img != null) {
            ivPlanetImg.setImageBitmap(_model.img);
            progressBar.setVisibility(INVISIBLE);
        }

        tvPlanetData.setText(
                "Name: " + _model.name + '\n' +
                "Place: " + _model.place + '\n' +
                "Type: " + _model.type + '\n' +
                "Mass: " + _model.mass + '\n' +
                "Orbital Speed: " + _model.orbitalSpeed
        );
    }

}
