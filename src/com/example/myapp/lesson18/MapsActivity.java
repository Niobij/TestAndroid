package com.example.myapp.lesson18;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.myapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

/**
 * Created by Vasia on 09.02.2015.
 */
public class MapsActivity extends Activity implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnMapClickListener {

    static final int REQUEST_IMAGE_GET = 1;
    static final int IMAGE_MARKER_SIZE = 64;
    LocationManager locationManager;
    MapFragment mapFragment;
    GoogleMap googleMap;

    ImageView ivMarkerImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps_container_activity);
        findView();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mapFragment.getMapAsync(this);



    }

    private void findView() {
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        ivMarkerImage = (ImageView) findViewById(R.id.ivMarkerImage);

    }

    @Override
    public void onMapReady(GoogleMap _googleMap) {
        googleMap = _googleMap;
        googleMap.setMyLocationEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setOnMapClickListener(this);
    }

    private Location getLocation(){
        Location location = googleMap.getMyLocation();
        return location;
    }

    private void startMarkerDialog(final LatLng latLng){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        final RelativeLayout view = (RelativeLayout) getLayoutInflater().inflate(R.layout.marker_dialog, null);

        dialog.setTitle(R.string.marker_dialog_title)
                .setIcon(R.drawable.ic_marker)
                .setView(view)
                .setNegativeButton(R.string.button_cancel, null)

                .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MarkerOptions markerOptions = new MarkerOptions()
                                .position(latLng)
                                .title(((TextView) view.findViewById(R.id.etMarkerText)).getText().toString());

                        if (ivMarkerImage.getDrawable() != null)
                            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(((BitmapDrawable) ivMarkerImage.getDrawable()).getBitmap()));

                        googleMap.addMarker(markerOptions).showInfoWindow();


                    }
                });

        dialog.show();

        ivMarkerImage = (ImageView)view.findViewById(R.id.ivMarkerImage);
        ivMarkerImage.setOnClickListener(this);

    }

    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_GET);

    }

    private Bitmap getOptimizeBitmap(Bitmap bm, int newHeight, int newWidth) {

        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

        return bitmap;

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ivMarkerImage:
                selectImage();
                break;

        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        startMarkerDialog(latLng);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
            try {
                Bitmap bitmap = getOptimizeBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData()), IMAGE_MARKER_SIZE, IMAGE_MARKER_SIZE );

                ivMarkerImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
