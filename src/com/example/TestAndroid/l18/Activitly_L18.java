package com.example.TestAndroid.l18;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
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

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by ZOG on 2/9/2015.
 */
public class Activitly_L18 extends Activity implements View.OnClickListener, OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private static final String sLocationProviderNetwork = LocationManager.NETWORK_PROVIDER;
    private static LocationManager mLocationManager;
    public static Location mLocation;
    private MyLocationListener mMyLocationListener;

    private MapFragment mMapFragment;
    private GoogleMap mGoogleMap;

    private LinearLayout llMapType;
    private Button btnMapSatellite;
    private Button btnMapTerrain;
    private Button btnMapHybrid;

    protected LinearLayout llMapControl;
    private Button btnMyLocation;
    private Button btnZoomIn;
    private Button btnZoomOut;
    private Button btnInfo;

    private ArrayList<LatLng> listOfPoints;

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

        listOfPoints = new ArrayList<LatLng>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLocationManager.requestLocationUpdates(sLocationProviderNetwork, 300, 0, mMyLocationListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            // Modes: MODE_PRIVATE, MODE_WORLD_READABLE, MODE_WORLD_WRITABLE
            FileOutputStream output = openFileOutput("latlngpoints.txt",
                    Context.MODE_PRIVATE);
            DataOutputStream dout = new DataOutputStream(output);
            dout.writeInt(listOfPoints.size()); // Save line count
            for (LatLng point : listOfPoints) {
                dout.writeUTF(point.latitude + "," + point.longitude);
                Log.v("write", point.latitude + "," + point.longitude);
            }
            dout.flush(); // Flush stream ...
            dout.close(); // ... and close.
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }


    private void loadMarkers() {

        try {
            FileInputStream input = openFileInput("latlngpoints.txt");
            DataInputStream din = new DataInputStream(input);
            int sz = din.readInt(); // Read line count
            for (int i = 0; i < sz; i++) {
                String str = din.readUTF();
                Log.v("read", str);
                String[] stringArray = str.split(",");
                double latitude = Double.parseDouble(stringArray[0]);
                double longitude = Double.parseDouble(stringArray[1]);
                listOfPoints.add(new LatLng(latitude, longitude));
                mGoogleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(latitude, longitude))
                                .title("Marker " + System.currentTimeMillis() % 10000)
                                .icon(BitmapDescriptorFactory.defaultMarker())
                );
            }
            din.close();

        } catch (IOException exc) {
            exc.printStackTrace();
        }
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
        btnZoomIn = (Button) findViewById(R.id.buttonZoomIn);
        btnZoomOut = (Button) findViewById(R.id.buttonZoomOut);
        btnInfo = (Button) findViewById(R.id.buttonInfo);
    }

    private void setListeners() {
        btnMapSatellite.setOnClickListener(this);
        btnMapTerrain.setOnClickListener(this);
        btnMapHybrid.setOnClickListener(this);

        btnMyLocation.setOnClickListener(this);
        btnZoomIn.setOnClickListener(this);
        btnZoomOut.setOnClickListener(this);
        btnInfo.setOnClickListener(this);
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

            case R.id.buttonZoomIn:
                onClickBtnZoomIn();
                break;

            case R.id.buttonZoomOut:
                onClickBtnZoomOut();
                break;

            case R.id.buttonInfo:
                try {
                    onClickBtnInfo();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
    private void onClickBtnZoomIn() {

            mGoogleMap.animateCamera(CameraUpdateFactory.zoomIn());

    }

    private void onClickBtnZoomOut() {

            mGoogleMap.animateCamera(CameraUpdateFactory.zoomOut());

    }

    private void onClickBtnInfo() throws IOException {

        double dLatitude = mLocation.getLatitude();
        double dLongitude = mLocation.getLongitude();

        Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
        Address address;
        List<Address> list;
        list = geocoder.getFromLocation(dLatitude,dLongitude,1);
        address = list.get(0);
        String cityname =  address.getLocality();
        String countryname =   address.getCountryName();
        String code =   address.getPostalCode();
        String addressL =   address.getAddressLine(0);


        String info = "State: " + countryname.toString()
                + "\n" + "Postal Code: " +  code.toString()
                + "\n" + "Address: " +  addressL.toString();

        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_launcher)
                .setTitle(cityname.toString())
                .setMessage(info)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        }).show();

    }

    @Override
    public void onMapReady(GoogleMap _googleMap) {
        mGoogleMap = _googleMap;

        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.setOnMapClickListener(this);
        llMapType.setVisibility(View.VISIBLE);
//        llMapControl.setVisibility(View.VISIBLE);

        mLocation = mGoogleMap.getMyLocation();
        loadMarkers();

    }

    @Override
    public void onMapClick(LatLng latLng) {
        mGoogleMap.addMarker(new MarkerOptions()
            .position(latLng)
            .title("Marker " + System.currentTimeMillis() % 10000)
            .icon(BitmapDescriptorFactory.defaultMarker())
        );

        listOfPoints.add(latLng);
    }
}
