package com.example.TestAndroid.l16;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.TestAndroid.R;
import com.example.TestAndroid.l16.models.PlanetModel;

/**
 * Created by ZOG on 2/2/2015.
 */
public class PlanetView extends RelativeLayout {

    private ImageView ivPlanetImg;
    private TextView tvPlanetData;

    public PlanetView(Context context) {
        super(context);

        inflate(context, R.layout.view_planet, this);

        findViews();
    }

    private void findViews() {
        ivPlanetImg = (ImageView) findViewById(R.id.ivPlanetImg_VP);
        tvPlanetData = (TextView) findViewById(R.id.tvPlanetData_VP);
    }

    public void setPlanetModel(PlanetModel _model) {
        ivPlanetImg.setImageBitmap(_model.img);
        tvPlanetData.setText(
                "Name: " + _model.name + '\n' +
                "Place: " + _model.place + '\n' +
                "Type: " + _model.type + '\n' +
                "Mass: " + _model.mass + '\n' +
                "Orbital Speed: " + _model.orbitalSpeed
        );
    }

}
