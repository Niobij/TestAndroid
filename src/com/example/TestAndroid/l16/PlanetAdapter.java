package com.example.TestAndroid.l16;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.example.TestAndroid.l16.models.PlanetModel;

import java.util.ArrayList;

/**
 * Created by ZOG on 2/2/2015.
 */
public class PlanetAdapter extends BaseAdapter {

    private ArrayList<PlanetModel> mPlanets;
    private Context mContext;

    public PlanetAdapter(Context _context, ArrayList<PlanetModel> _planets) {
        mContext = _context;
        mPlanets = _planets;
    }

    @Override
    public int getCount() {
        return mPlanets.size();
    }

    @Override
    public Object getItem(int _pos) {
        return mPlanets.get(_pos);
    }

    @Override
    public long getItemId(int _pos) {
        return _pos;
    }

    @Override
    public View getView(int _pos, View _view, ViewGroup _viewGroup) {
        PlanetModel planetModel = mPlanets.get(_pos);
        PlanetView planetView = new PlanetView(mContext);
        planetView.setPlanetModel(planetModel);
        return planetView;
    }


}
