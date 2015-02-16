package com.example.TestAndroid.l18;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.TestAndroid.R;
import com.example.TestAndroid.l18.models.MarkersModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by ZOG on 2/9/2015.
 */
public class Activitly_L18 extends Activity implements View.OnClickListener, OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private static final String sLocationProviderNetwork = LocationManager.NETWORK_PROVIDER;
    private static final String MARKERS_FILENAME = "markers.json";
    private static LocationManager mLocationManager;
    private Location mLocation;
    private MyLocationListener mMyLocationListener;

    private MapFragment mMapFragment;
    private GoogleMap mGoogleMap;

    private LinearLayout llMapType;
    private Button btnMapSatellite;
    private Button btnMapTerrain;
    private Button btnMapHybrid;
    private Button btnLocationInfo;

    protected LinearLayout llZoomControl;
    private ImageView ivZoomIn;
    private ImageView ivZoomOut;

    protected LinearLayout llMapControl;
    private Button btnMyLocation;

    protected LinearLayout llProgress;
    private ProgressBar pbProgress;

    private ArrayList<CustomMapMarker> mCustomMarkersArray = new ArrayList<CustomMapMarker>();
    private HashMap<Marker, CustomMapMarker> mMarkersHashMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l18);

        // Initialize the HashMap for Markers and CustomMarker object
//        mMarkersHashMap = new HashMap<Marker, CustomMapMarker>();
//        mCustomMarkersArray.add(new CustomMapMarker("Brasil", "icon1", Double.parseDouble("-28.5971788"), Double.parseDouble("-52.7309824")));
//        mCustomMarkersArray.add(new CustomMapMarker("United States", "icon2", Double.parseDouble("33.7266622"), Double.parseDouble("-87.1469829")));
//        mCustomMarkersArray.add(new CustomMapMarker("Canada", "icon3", Double.parseDouble("51.8917773"), Double.parseDouble("-86.0922954")));
//        mCustomMarkersArray.add(new CustomMapMarker("England", "icon4", Double.parseDouble("52.4435047"), Double.parseDouble("-3.4199249")));
//        mCustomMarkersArray.add(new CustomMapMarker("España", "icon5", Double.parseDouble("41.8728262"), Double.parseDouble("-0.2375882")));
//        mCustomMarkersArray.add(new CustomMapMarker("Portugal", "icon6", Double.parseDouble("40.8316649"), Double.parseDouble("-4.936009")));
//        mCustomMarkersArray.add(new CustomMapMarker("Deutschland", "icon7", Double.parseDouble("51.1642292"), Double.parseDouble("10.4541194")));
//        mCustomMarkersArray.add(new CustomMapMarker("Atlantic Ocean", "icondefault", Double.parseDouble("-13.1294607"), Double.parseDouble("-19.9602353")));


        findViews();
        setListeners();

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mMyLocationListener = new MyLocationListener();
        mMyLocationListener.activity = this;

        mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fMap_AL18);
        mMapFragment.getMapAsync(this);
        mMarkersHashMap = new HashMap<Marker, CustomMapMarker>();

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMarkers();
    }
    @Override
    protected void onPause() {
        super.onPause();
        saveMarkers();
    }

    private void loadMarkers() {
        String jsonMarkersString = null;

        try {
            FileInputStream is = openFileInput(MARKERS_FILENAME);
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            r.close();
            is.close();
            jsonMarkersString = total.toString();
            Gson gson = new Gson();
            MarkersModel markersModel = gson.fromJson(jsonMarkersString, MarkersModel.class);
            mCustomMarkersArray = markersModel.getMarkers();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void saveMarkers() {
        FileOutputStream fos = null;
        MarkersModel markers = new MarkersModel(mCustomMarkersArray);
        Gson gson = new Gson();
        String jsonMarkersString = gson.toJson(markers);
        try {
            fos = openFileOutput(MARKERS_FILENAME, Context.MODE_PRIVATE);
            fos.write(jsonMarkersString.getBytes());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        btnLocationInfo = (Button) findViewById(R.id.btnLocationInfo_AL18);

        llZoomControl = (LinearLayout) findViewById(R.id.llZoomControl_AL18);
        ivZoomIn = (ImageView) findViewById(R.id.ivZoomIn_AL18);
        ivZoomOut = (ImageView) findViewById(R.id.ivZoomOut_AL18);

        llProgress =(LinearLayout) findViewById(R.id.llProgres_AL18);
        pbProgress = (ProgressBar) findViewById(R.id.pbProgress_AL18);
    }

    private void setListeners() {
        btnMapSatellite.setOnClickListener(this);
        btnMapTerrain.setOnClickListener(this);
        btnMapHybrid.setOnClickListener(this);

        btnMyLocation.setOnClickListener(this);
        btnLocationInfo.setOnClickListener(this);

        ivZoomIn.setOnClickListener(this);
        ivZoomOut.setOnClickListener(this);
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
            case R.id.ivZoomIn_AL18:
                onClickIvZoomIn();
                break;
            case R.id.ivZoomOut_AL18:
                onClickIvZoomOut();
                break;
            case R.id.btnLocationInfo_AL18:
                onClickBtnLocationInfo();
                break;
        }
    }

    private void onClickBtnLocationInfo() {
        Location location = mGoogleMap.getMyLocation();

        if (location != null) {
            double dLatitude = location.getLatitude();
            double dLongitude = location.getLongitude();
            alertGeoInfo(getGeoInfo(dLatitude, dLongitude));
        }
    }


    private String getGeoInfo(double dLatitude, double dLongitude) {
        GetGeoInfoTask task = new GetGeoInfoTask();
        String retStr = null;
        try {
            retStr = task.execute(dLatitude, dLongitude).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return retStr;
//        DOESN'T WORK :( USING WORKAROUND
//        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//        if (geocoder.isPresent ()) {
//            try {
//                List<Address> addresses = geocoder.getFromLocation(dLatitude, dLongitude, 1);
//                if (addresses == null || addresses.size() == 0) {
//                    return getString(R.string.no_address_found);
//                }
//                StringBuilder geoInfo = new StringBuilder();
//                geoInfo.append(addresses.get(0).getCountryName()).append("\n");
//                geoInfo.append(addresses.get(0).getPostalCode()).append("\n");
//                for (int i = 0; i < addresses.get(0).getMaxAddressLineIndex(); i++) {
//                    geoInfo.append(addresses.get(0).getAddressLine(i)).append("\n");
//                }
//                geoInfo.append(addresses.get(0).getFeatureName()).append("\n");
//                return geoInfo.toString();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
    }

    private void alertGeoInfo(String geoInfo) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.geo_info_alert_AL18)
                .setMessage(geoInfo)
                .setPositiveButton(android.R.string.yes, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void onClickIvZoomOut() {
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomOut());
    }

    private void onClickIvZoomIn() {
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomIn());
    }

    private void onClickBtnMyLocation() {
        Location location = mGoogleMap.getMyLocation();
        if (location != null) {
            double dLatitude = location.getLatitude();
            double dLongitude = location.getLongitude();
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(dLatitude, dLongitude), 15));
        }
    }


    @Override
    public void onMapReady(GoogleMap _googleMap) {
        mGoogleMap = _googleMap;
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.setOnMapClickListener(this);
//        llMapType.setVisibility(View.VISIBLE);
//        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);  // Can't position this controls properly :(
//        llMapControl.setVisibility(View.VISIBLE);
        setUpMap();
        plotMarkers(mCustomMarkersArray);
    }

    @Override
    public void onMapClick(LatLng latLng) {

        MarkerOptions markerOption = new MarkerOptions().position(latLng);
        markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.currentlocation_icon));
        Marker currentMarker = mGoogleMap.addMarker(markerOption);
        String label = "lat: " + latLng.latitude + "\n" + "lon: " + latLng.longitude;
        CustomMapMarker currentCustomMarker = new CustomMapMarker(label, "", latLng.latitude , latLng.longitude );
        mMarkersHashMap.put(currentMarker, currentCustomMarker);
        mCustomMarkersArray.add(currentCustomMarker);

        mGoogleMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());
    }

    private void plotMarkers(ArrayList<CustomMapMarker> markers)
    {
        if(markers.size() > 0)
        {
            for (CustomMapMarker customMapMarker : markers)
            {

                // Create user marker with custom icon and other options
                MarkerOptions markerOption = new MarkerOptions().position(new LatLng(customMapMarker.getLatitude(), customMapMarker.getLongitude()));
                markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.currentlocation_icon));

                Marker currentMarker = mGoogleMap.addMarker(markerOption);
                mMarkersHashMap.put(currentMarker, customMapMarker);

                mGoogleMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());
            }
        }
    }

    private int manageMarkerIcon(String markerIcon) {
//        if (markerIcon.equals("icon1"))
//            return R.drawable.icon1;
//        else if(markerIcon.equals("icon2"))
//            return R.drawable.icon2;
//        else if(markerIcon.equals("icon3"))
//            return R.drawable.icon3;
//        else if(markerIcon.equals("icon4"))
//            return R.drawable.icon4;
//        else if(markerIcon.equals("icon5"))
//            return R.drawable.icon5;
//        else if(markerIcon.equals("icon6"))
//            return R.drawable.icon6;
//        else if(markerIcon.equals("icon7"))
//            return R.drawable.icon7;
//        else
            return R.drawable.icondefault;
    }


    private void setUpMap()
    {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mGoogleMap == null)
        {
            // Try to obtain the map from the SupportMapFragment.
            mGoogleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.fMap_AL18)).getMap();

            // Check if we were successful in obtaining the map.

            if (mGoogleMap != null)
            {
                mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
                {
                    @Override
                    public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker)
                    {
                        marker.showInfoWindow();
                        return true;
                    }
                });
            }
            else
                Toast.makeText(getApplicationContext(), "Unable to create Maps", Toast.LENGTH_SHORT).show();
        }
    }

    public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter
    {
        public MarkerInfoWindowAdapter()
        {
        }

        @Override
        public View getInfoWindow(Marker marker)
        {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker)
        {
            View v  = getLayoutInflater().inflate(R.layout.infowindow_layout, null);

            CustomMapMarker customMapMarker = mMarkersHashMap.get(marker);

            ImageView markerIcon = (ImageView) v.findViewById(R.id.marker_icon);

            TextView markerLabel = (TextView)v.findViewById(R.id.marker_label);

            TextView anotherLabel = (TextView)v.findViewById(R.id.another_label);

            markerIcon.setImageResource(manageMarkerIcon(customMapMarker.getIcon()));

            markerLabel.setText(customMapMarker.getLabel());
            anotherLabel.setText(getGeoInfo(customMapMarker.getLatitude(),customMapMarker.getLongitude()));

            return v;
        }
    }


}
