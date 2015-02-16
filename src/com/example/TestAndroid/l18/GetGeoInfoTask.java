package com.example.TestAndroid.l18;

import android.location.Address;
import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Maksym on 16.02.2015.
 * Asyncronous Adress getter from Google Maps API
 */
public class GetGeoInfoTask extends AsyncTask<Double, Void, String > {

    private static final String DEBUG_TAG = "debug";
    private static final String GOOGLE_MAP_API_URL = "http://maps.googleapis.com/maps/api/geocode/json?latlng=%1$f,%2$f&sensor=true&language=EN";
    private List<Address> mAdressList;

    @Override
    protected String doInBackground(Double... params) {
        mAdressList = getFromLocation(params[0], params[1]);
        if (mAdressList != null) {
            return mAdressList.get(0).getAddressLine(0);
        } else {
            return null;
        }
    }

    private List<Address> getFromLocation(double lat, double lng){

        String address = String.format(Locale.ENGLISH,GOOGLE_MAP_API_URL, lat, lng); //+Locale.getDefault().getCountry()
        HttpGet httpGet = new HttpGet(address);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();

        List<Address> retList = null;

        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }

            JSONObject jsonObject;
            jsonObject = new JSONObject(stringBuilder.toString());


            retList = new ArrayList<Address>();


            if("OK".equalsIgnoreCase(jsonObject.getString("status"))){
                JSONArray results = jsonObject.getJSONArray("results");
                for (int i=0;i<results.length();i++ ) {
                    JSONObject result = results.getJSONObject(i);
                    String indiStr = result.getString("formatted_address");
                    Address addr = new Address(Locale.US);
                    addr.setAddressLine(0, indiStr);
                    retList.add(addr);
                }
            }
        } catch (ClientProtocolException e) {
            Log.e(DEBUG_TAG, "Error calling Google geocode webservice.", e);
        } catch (IOException e) {
            Log.e(DEBUG_TAG, "Error calling Google geocode webservice.", e);
        } catch (JSONException e) {
            Log.e(DEBUG_TAG, "Error parsing Google geocode webservice response.", e);
        }

        return retList;
    }

}
