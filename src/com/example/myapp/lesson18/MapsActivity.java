package com.example.myapp.lesson18;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
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
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Vasia on 09.02.2015.
 */
public class MapsActivity extends Activity implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnMapClickListener {

    static final int REQUEST_IMAGE_GET = 1;
    static final int IMAGE_MARKER_SIZE = 64;
    LocationManager locationManager;
    MapFragment mapFragment;
    GoogleMap googleMap;
    MyDbOpenHelper database;

    ImageView ivMarkerImage;
    ArrayList<MarkerObject> markerObjectAList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps_container_activity);
        findView();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mapFragment.getMapAsync(this);

        database = new MyDbOpenHelper(this);
        markerObjectAList = new ArrayList<MarkerObject>();



    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveDataInDatabase();
    }

    private void loadDataFromDatabase(){

        SQLiteDatabase db = database.getReadableDatabase();

        Cursor cursor = db.query("marker", null, null, null, null, null, null);

        int latitudeIndex = cursor.getColumnIndex(MyDbOpenHelper.LATITUDE);
        int longitudeIndex = cursor.getColumnIndex(MyDbOpenHelper.LONGITUDE);
        int imageNameIndex = cursor.getColumnIndex(MyDbOpenHelper.IMAGE_NAME);
        int markerTitleIndex = cursor.getColumnIndex(MyDbOpenHelper.MARKER_TITLE);

        while (cursor.moveToNext()) {
            try {

                MarkerObject markerObject = new MarkerObject();

                String imageName = cursor.getString(imageNameIndex);
                String latitude = cursor.getString(latitudeIndex);
                String longitude = cursor.getString(longitudeIndex);
                String markerTitle = cursor.getString(markerTitleIndex);
//                Log.d("tag", "load data latitude = "  + latitude + "longitude = " + longitude + "markerTitle = " + markerTitle);
                markerObject.setImageName(imageName);
                markerObject.setLatLng(latitude, longitude);
                markerObject.setMarkerTitle(markerTitle);

                markerObjectAList.add(markerObject);
            }
            catch (IllegalStateException e){
                Log.d("tag", "IllegalStateException"+e);
            }
        }

        db.close();
        addMarker();
    }

    private void addMarker(){

        for (MarkerObject aMarkerObjectAList : markerObjectAList) {

            MarkerOptions marker = new MarkerOptions();

            marker.title(aMarkerObjectAList.getMarkerTitle());
            marker.position(aMarkerObjectAList.getLatLng());
            Bitmap bitmap = null;
            if (!aMarkerObjectAList.getImageName().equals("no image"))
                bitmap = loadBitmap(aMarkerObjectAList.getImageName());


            Log.d("tag", "bitma " + bitmap);

            if (bitmap != null)
                marker.icon(BitmapDescriptorFactory.fromBitmap(bitmap));

//            Log.d("tag", "googleMap " + googleMap);
//            Log.d("tag", "marker " + marker);
            googleMap.addMarker(marker);

        }
    }

    private void clearDatabase(){
        SQLiteDatabase db = database.getReadableDatabase();
        db.delete(MyDbOpenHelper.DICTIONARY_TABLE_NAME, null, null);
    }

    private void saveDataInDatabase(){
        clearDatabase();

        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        for (int i =0; i < markerObjectAList.size(); i++ ){
            contentValues.put(MyDbOpenHelper.LATITUDE, markerObjectAList.get(i).getLatitude());
            contentValues.put(MyDbOpenHelper.LONGITUDE, markerObjectAList.get(i).getLongitude());
            contentValues.put(MyDbOpenHelper.MARKER_TITLE, markerObjectAList.get(i).getMarkerTitle());
            contentValues.put(MyDbOpenHelper.IMAGE_NAME, markerObjectAList.get(i).getImageName());


            db.insert(MyDbOpenHelper.DICTIONARY_TABLE_NAME, null, contentValues);

        }
        database.close();
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
        loadDataFromDatabase();
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
                        MarkerObject markerObject = new MarkerObject();

                        if (ivMarkerImage.getDrawable() != null) {
                            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(((BitmapDrawable) ivMarkerImage.getDrawable()).getBitmap()));
                            String imageName = UUID.randomUUID().toString();
                            saveBitmap(imageName, ((BitmapDrawable) ivMarkerImage.getDrawable()).getBitmap());
                            markerObject.setImageName(imageName);
                        }
                        else markerObject.setImageName("no image");

                        markerObject.setLatLng(latLng);
                        markerObject.setMarkerTitle(((TextView) view.findViewById(R.id.etMarkerText)).getText().toString());


                        markerObjectAList.add(markerObject);
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




    private boolean saveBitmap(String bitmapName, Bitmap bitmap) {
        OutputStream fOut;

        try {
            File file = new File(getCacheDir().toString(), bitmapName);
//            File file = new File(Environment.getDataDirectory().toString(), bitmapName);
            fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();
            Log.d("tag", "save bitmap " + bitmapName);
        }
        catch (Exception e) {
            e.getMessage();
            Log.d("tag", "Save Exception " + e);
            return false;
        }
        return true;
    }

    private Bitmap loadBitmap(String bitmapName){

        Bitmap bitmap = BitmapFactory.decodeFile(getCacheDir() + "/" + bitmapName);
//        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getDataDirectory() + "/" + bitmapName);
        Log.d("tag", "loadBitmap " + Environment.getDataDirectory() + "/" + bitmapName);
        return bitmap;
    }



}
