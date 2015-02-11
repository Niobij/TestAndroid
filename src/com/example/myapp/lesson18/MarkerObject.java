package com.example.myapp.lesson18;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Vasia on 10.02.2015.
 */
public class MarkerObject {
    private LatLng latLng;
    private String imageName;
    private String markerTitle;

    public LatLng getLatLng() {
        return latLng;
    }

    public String getImageName() {
        return imageName;
    }

    public String getMarkerTitle() {
        return markerTitle;
    }

    public void setLatLng(String _latitude, String _longitude) {
        this.latLng = new LatLng(Double.parseDouble(_latitude), Double.parseDouble(_longitude));
    }

    public void setLatLng(LatLng _latLng){
        latLng = _latLng;
    }

    public String getLatitude(){
        return Double.toString(latLng.latitude);
    }

    public String getLongitude(){
        return Double.toString(latLng.longitude);
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setMarkerTitle(String markerTitle) {
        this.markerTitle = markerTitle;
    }
}
