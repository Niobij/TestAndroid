package com.example.TestAndroid.l16;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import com.example.TestAndroid.l16.models.PlanetModel;
import com.example.TestAndroid.l16.models.SolarSystemModel;
import com.example.TestAndroid.l16.models.SpotModel;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ZOG on 2/2/2015.
 */
public class LoadAsyncTask extends AsyncTask<Void, Void, SolarSystemModel> {

    private Context mContext;
    private OnDataLoadListener mOnDataLoadListener;

    public LoadAsyncTask(Context _context, OnDataLoadListener _onDataLoadListener) {
        mContext = _context;
        mOnDataLoadListener = _onDataLoadListener;
    }

    @Override
    protected SolarSystemModel doInBackground(Void... voids) {
        SolarSystemModel model = null;
        try {
            model = loadData();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return model;
    }

    @Override
    protected void onPostExecute(SolarSystemModel model) {
        if (model != null) {
            mOnDataLoadListener.onSuccess(model);
        } else {
            mOnDataLoadListener.onFailure();
        }
    }


    private SolarSystemModel loadData() throws IOException, JSONException {

        SolarSystemModel solarSystemModel = new SolarSystemModel();
        solarSystemModel.planets = new ArrayList<PlanetModel>();

        String jsonString = getJsonString();

        JSONObject rootObj = new JSONObject(jsonString);
//        JSONObject rootObj = new JSONObject();
//        rootObj.put("key", "value");

        JSONObject jSpot = rootObj.getJSONObject("spot");
        solarSystemModel.spot = parseSpot(jSpot);

        JSONArray jPlanets = rootObj.getJSONArray("planets");
        for (int i = 0; i < jPlanets.length(); i++) {
            JSONObject jPlanet = jPlanets.getJSONObject(i);
            PlanetModel planet = parsePlanet(jPlanet);
            solarSystemModel.planets.add(planet);
        }

        return solarSystemModel;
    }


    private String getJsonString() throws IOException {
        InputStream is = mContext.getAssets().open("json.txt");

        final BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }


    private SpotModel parseSpot(JSONObject _jSpot) throws JSONException, IOException {
        SpotModel spot = new SpotModel();

        spot.name = _jSpot.getString("name");
        spot.age = _jSpot.getString("age");
        spot.mass = _jSpot.getString("mass");
        spot.planetsCount = _jSpot.getString("planetsCount");
        spot.orbitalSpeed = _jSpot.getString("orbitalSpeed");
        spot.imgUrl = _jSpot.getString("imgUrl");

        final byte[] imgBytes = getImg(spot.imgUrl);
        spot.img = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);

        return spot;
    }

    private PlanetModel parsePlanet(JSONObject _jPlanet) throws JSONException, IOException {
        PlanetModel planet = new PlanetModel();

        planet.place = _jPlanet.getString("place");
        planet.type = _jPlanet.getString("type");
        planet.name = _jPlanet.getString("name");
        planet.mass = _jPlanet.getString("mass");
        planet.orbitalSpeed = _jPlanet.getString("orbitalSpeed");
        planet.imgUrl = _jPlanet.getString("imgUrl");

        final byte[] imgBytes = getImg(planet.imgUrl);
        planet.img = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);

        return planet;
    }


    private final byte[] getImg(String _url) throws IOException {
        URL url = new URL(_url);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(10000);
        conn.setRequestMethod(HttpGet.METHOD_NAME);

        int respCode = conn.getResponseCode();
        if (respCode == 200) {
            InputStream inputStream = conn.getInputStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            int len;
            byte[] buffer = new byte[4096];
            while ((len = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }

            return bos.toByteArray();
        }

        return null;
    }

}
