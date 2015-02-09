package com.example.TestAndroid.l18;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.example.TestAndroid.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by ZOG on 2/9/2015.
 */
public class Activitly_L18 extends Activity implements View.OnClickListener, OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private static final String sLocationProviderNetwork = LocationManager.NETWORK_PROVIDER;
    private static LocationManager mLocationManager;
    private Location mLocation;
    private MyLocationListener mMyLocationListener;

    private MapFragment mMapFragment;
    private GoogleMap mGoogleMap;

    private LinearLayout llMapType;
    private Button btnMapSatellite;
    private Button btnMapTerrain;
    private Button btnMapHybrid;

    protected LinearLayout llMapControl;
    private Button btnMyLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l18);

        findViews();
        setListeners();

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mMyLocationListener = new MyLocationListener();
        mMyLocationListener.activity = this;

        mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fMap_AL18);
        mMapFragment.getMapAsync(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLocationManager.requestLocationUpdates(sLocationProviderNetwork, 300, 0, mMyLocationListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationManager.removeUpdates(mMyLocationListener);
    }

    private void findViews() {
        llMapType = (LinearLayout) findViewById(R.id.llMapType_AL18);
        btnMapSatellite = (Button) findViewById(R.id.btnMapSatellite_AL18);
        btnMapTerrain = (Button) findViewById(R.id.btnMapTerrain_AL18);
        btnMapHybrid = (Button) findViewById(R.id.btnMapHybrid_AL18);

        llMapControl = (LinearLayout) findViewById(R.id.llMapControl_AL18);
        btnMyLocation = (Button) findViewById(R.id.btnMyLocation_AL18);
    }

    private void setListeners() {
        btnMapSatellite.setOnClickListener(this);
        btnMapTerrain.setOnClickListener(this);
        btnMapHybrid.setOnClickListener(this);

        btnMyLocation.setOnClickListener(this);
    }

    @Override
    public void onClick(View _view) {
        switch (_view.getId()) {
            case R.id.btnMapSatellite_AL18:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;

            case R.id.btnMapTerrain_AL18:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;

            case R.id.btnMapHybrid_AL18:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;

            case R.id.btnMyLocation_AL18:
                onClickBtnMyLocation();
                break;
        }
    }

    private void onClickBtnMyLocation() {
        Location location = mGoogleMap.getMyLocation();
        if (location != null) {
            double dLatitude = location.getLatitude();
            double dLongitude = location.getLongitude();
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(dLatitude, dLongitude), 4));
        }
    }


    @Override
    public void onMapReady(GoogleMap _googleMap) {
        mGoogleMap = _googleMap;
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.setOnMapClickListener(this);
        llMapType.setVisibility(View.VISIBLE);
//        llMapControl.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mGoogleMap.addMarker(new MarkerOptions()
            .position(latLng)
            .title("Marker " + System.currentTimeMillis() % 10000)
            .icon(BitmapDescriptorFactory.defaultMarker())
        );
    }
}
