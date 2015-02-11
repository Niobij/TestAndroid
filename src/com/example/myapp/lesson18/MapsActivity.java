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
import android.graphics.Canvas;
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
    static final int IMAGE_MARKER_WIDTH = 93;
    static final int IMAGE_MARKER_HEIGHT = 70;


    LocationManager locationManager;
    MapFragment mapFragment;
    GoogleMap googleMap;
    MyDbOpenHelper database;
    ImageView ivMarkerImage;
    ArrayList<MarkerObject> markerObjectAList;
    Bitmap bitmapMarker;


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
        Toast.makeText(this, "Save marker history", Toast.LENGTH_SHORT).show();
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

                markerObject.setImageName(cursor.getString(imageNameIndex));
                markerObject.setLatLng(cursor.getString(latitudeIndex), cursor.getString(longitudeIndex));
                markerObject.setMarkerTitle(cursor.getString(markerTitleIndex));

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
            bitmap = loadBitmap(aMarkerObjectAList.getImageName());

            if (bitmap != null) {
                marker.icon(BitmapDescriptorFactory.fromBitmap(addFrame(bitmap)))
                      .anchor((float) 0.2, 1);
            }
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

        for (MarkerObject aMarkerObjectAList : markerObjectAList) {
            contentValues.put(MyDbOpenHelper.LATITUDE, aMarkerObjectAList.getLatitude());
            contentValues.put(MyDbOpenHelper.LONGITUDE, aMarkerObjectAList.getLongitude());
            contentValues.put(MyDbOpenHelper.MARKER_TITLE, aMarkerObjectAList.getMarkerTitle());
            contentValues.put(MyDbOpenHelper.IMAGE_NAME, aMarkerObjectAList.getImageName());
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
                            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(addFrame(bitmapMarker)))
                                            .anchor((float) 0.2, 1);

                            String imageName = UUID.randomUUID().toString();
                            saveBitmap(imageName, bitmapMarker);
                            markerObject.setImageName(imageName);
                        }

                        markerObject.setLatLng(latLng);
                        markerObject.setMarkerTitle(((TextView) view.findViewById(R.id.etMarkerText)).getText().toString());

                        markerObjectAList.add(markerObject);
                        googleMap.addMarker(markerOptions).showInfoWindow();

                    }
                });

        dialog.show();

        ivMarkerImage = (ImageView)view.findViewById(R.id.ivMarkerImage);
        ImageButton ibSelectImage = (ImageButton)view.findViewById(R.id.ibSelectImage);

        ibSelectImage.setOnClickListener(this);
        ivMarkerImage.setOnClickListener(this);

    }

    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_GET);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ivMarkerImage:
                selectImage();
                break;
            case R.id.ibSelectImage:
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
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                ivMarkerImage.setImageBitmap(getOptimizeBitmap(bitmap, 64, 64));
                bitmapMarker = getOptimizeBitmap(bitmap, IMAGE_MARKER_WIDTH, IMAGE_MARKER_HEIGHT);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private Bitmap getOptimizeBitmap(Bitmap bm, int newWidth, int newHeight) {

        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

        Log.d("tag", "Bitmap width = "  + bitmap.getWidth());
        Log.d("tag", "Bitmap Height = " + bitmap.getHeight());

        return bitmap;

    }

    private Bitmap addFrame(Bitmap bitmap){
        Bitmap frame = getOptimizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_marker_frame), 100, 100);
        Bitmap newBitmap;
        Bitmap.Config config = frame.getConfig();
        if (config == null) {
            config = Bitmap.Config.ARGB_8888;
        }
        Log.d("tag", "Frame width = "  + frame.getWidth());
        Log.d("tag", "Frame Height = " + frame.getHeight());

        newBitmap = Bitmap.createBitmap(frame.getWidth(), frame.getHeight(), config);

        Canvas canvas = new Canvas(newBitmap);
        canvas.drawBitmap(frame, 0, 0, null);
        canvas.drawBitmap(bitmap, 3, 3, null);

        return newBitmap;
    }


    private boolean saveBitmap(String bitmapName, Bitmap bitmap) {
        OutputStream fOut;

        try {
            File file = new File(getCacheDir().toString(), bitmapName);
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
        Log.d("tag", "loadBitmap " + Environment.getDataDirectory() + "/" + bitmapName);
        return bitmap;
    }



}
