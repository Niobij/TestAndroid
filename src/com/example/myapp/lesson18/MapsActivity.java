package com.example.myapp.lesson18;

import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import com.example.myapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * Created by Vasia on 09.02.2015.
 */
public class MapsActivity extends Activity implements OnMapReadyCallback {

    LocationManager locationManager;
    MapFragment mapFragment;
    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps_container_activity);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);



    }

    @Override
    public void onMapReady(GoogleMap _googleMap) {
        googleMap = _googleMap;
        googleMap.setMyLocationEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//        googleMap.animateCamera(CameraUpdateFactory);
    }

    private Location getLocation(){
        Location location = googleMap.getMyLocation();
        return location;
    }
}
