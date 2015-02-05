package com.example.TestAndroid.l16;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.TestAndroid.R;
import com.example.TestAndroid.l16.models.PlanetModel;
import com.example.TestAndroid.l16.models.SolarSystemModel;
import com.example.TestAndroid.l16.models.SpotModel;

import java.util.ArrayList;

/**
 * Created by ZOG on 2/2/2015.
 */
public class L16Activity extends Activity implements View.OnClickListener, OnDataLoadListener {

    private Button btnLoadPlanets;
    private ProgressBar pbLoading;
    private ImageView ivSolarSystem;
    private TextView tvSolarSystemData;
    private ListView lvPlanets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l16);

        findViews();
        btnLoadPlanets.setOnClickListener(this);
    }


    private void findViews() {
        btnLoadPlanets = (Button) findViewById(R.id.btnLoadPlanets_AL16);
        pbLoading = (ProgressBar) findViewById(R.id.pbLoading_AL16);
        ivSolarSystem = (ImageView) findViewById(R.id.ivSolarSystem_AL16);
        tvSolarSystemData = (TextView) findViewById(R.id.tvSolarSystemData_AL16);
        lvPlanets = (ListView) findViewById(R.id.lvPlanets_AL16);
    }

    @Override
    public void onClick(View view) {
        if (view == btnLoadPlanets) {
            pbLoading.setVisibility(View.VISIBLE);
            new LoadAsyncTask(this, this).execute();
        }
    }


    private void setSolarSystemModel(SolarSystemModel _model) {
        SpotModel spot = _model.spot;
        ivSolarSystem.setImageBitmap(spot.img);
        tvSolarSystemData.setText(
                "Name: " + spot.name + '\n' +
                "Age: " + spot.age + '\n' +
                "Mass: " + spot.mass + '\n' +
                "Planets: " + spot.planetsCount + '\n' +
                "Orbital Speed: " + spot.orbitalSpeed
        );

        ArrayList<PlanetModel> planets = _model.planets;
        PlanetAdapter planetAdapter = new PlanetAdapter(this, planets);
        lvPlanets.setAdapter(planetAdapter);
    }


    @Override
    public void onSuccess(SolarSystemModel _model) {
        btnLoadPlanets.setVisibility(View.GONE);
        pbLoading.setVisibility(View.GONE);
        setSolarSystemModel(_model);
    }

    @Override
    public void onFailure() {
        btnLoadPlanets.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.GONE);
        Toast.makeText(this, "Failed load data", Toast.LENGTH_SHORT).show();
    }
}
