package com.example.myapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import org.apache.http.client.methods.HttpGet;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Vasia on 04.02.2015.
 */
public class LoadImageAsyncTask extends AsyncTask<HashMap<String, String>, Void, Bitmap> {

    OnImageLoadListener loadListener;
    int position;

    public LoadImageAsyncTask(OnImageLoadListener _loadListener) {
        this.loadListener = _loadListener;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (bitmap != null)
        loadListener.onLoadImage(position, bitmap);
        else loadListener.onFailureLoadImage(position);
    }

    @Override
    protected Bitmap doInBackground(HashMap<String, String>... params) {
        try {
            position = Integer.parseInt(params[0].get("position"));
            return getImg(params[0].get("image url"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private final Bitmap getImg(String _url) throws IOException {
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

            byte [] imageByte =  bos.toByteArray();
            return BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
        }

        return null;
    }
}
