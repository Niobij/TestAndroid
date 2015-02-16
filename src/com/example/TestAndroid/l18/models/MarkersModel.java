package com.example.TestAndroid.l18.models;

import com.example.TestAndroid.l18.CustomMapMarker;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Maksym on 15.02.2015.
 * Model of list of Markers for GSON json download
 */
public class MarkersModel implements Serializable {
    private ArrayList<CustomMapMarker> markers;

    public MarkersModel(ArrayList<CustomMapMarker> _customMarkersArray) {
        this.markers = _customMarkersArray;

    }

    public ArrayList<CustomMapMarker> getMarkers() {
        return markers;
    }

    public void setMarkers(ArrayList<CustomMapMarker> markers) {
        this.markers = markers;
    }
}
