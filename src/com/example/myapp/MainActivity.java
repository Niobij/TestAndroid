package com.example.myapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.myapp.models.PlanetModel;
import com.example.myapp.models.SolarSystemModel;
import com.example.myapp.models.SpotModel;


import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity implements View.OnClickListener, OnDataLoadListener, OnImageLoadListener  {

    private Button btnLoadPlanets;
    private ProgressBar pbLoading;
    private ImageView ivSolarSystem;
    private TextView tvSolarSystemData;
    private ListView lvPlanets;

    PlanetAdapter planetAdapter;
    ArrayList<PlanetModel> planets;



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
//        ivSolarSystem.setImageBitmap(spot.img);
        loadImage(spot.imgUrl);
        tvSolarSystemData.setText(
                "Name: " + spot.name + '\n' +
                "Age: " + spot.age + '\n' +
                "Mass: " + spot.mass + '\n' +
                "Planets: " + spot.planetsCount + '\n' +
                "Orbital Speed: " + spot.orbitalSpeed
        );

        planets = _model.planets;
        planetAdapter = new PlanetAdapter(this, planets);
        lvPlanets.setAdapter(planetAdapter);
        loadImage(planets);
    }

    private void loadImage(String imageUrl){
        LoadImageAsyncTask imageAsyncTask = new LoadImageAsyncTask(this);
        HashMap<String, String> imagePlanet = new HashMap<String, String>();
        imagePlanet.put("position", Integer.toString(-1));
        imagePlanet.put("image url", imageUrl);
        imageAsyncTask.execute(imagePlanet);
    }

    private void loadImage(ArrayList<PlanetModel> planet){
        for (int i = 0; i < planet.size(); i++){
            LoadImageAsyncTask imageAsyncTask = new LoadImageAsyncTask(this);
            HashMap<String, String> imagePlanet = new HashMap<String, String>();
            imagePlanet.put("position", Integer.toString(i));
            imagePlanet.put("image url", planet.get(i).imgUrl);
            imageAsyncTask.execute(imagePlanet);
        }

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

    @Override
    public void onLoadImage(int position, Bitmap bitmap) {
        if (position == -1) ivSolarSystem.setImageBitmap(bitmap);
        else {
            PlanetModel planetModel = planets.get(position);
            planetModel.img = bitmap;
            planets.set(position, planetModel);
            planetAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFailureLoadImage(int position) {
        if (position != -1) {
            PlanetModel planetModel = planets.get(position);
            planetModel.img = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_gallery);
            planets.set(position, planetModel);
            planetAdapter.notifyDataSetChanged();
        }
        Toast.makeText(this, "Failed load image", Toast.LENGTH_SHORT).show();

        }
}
