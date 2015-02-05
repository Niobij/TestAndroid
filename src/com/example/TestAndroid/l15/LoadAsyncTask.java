package com.example.TestAndroid.l15;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.*;

/**
 * Created by ZOG on 1/29/2015.
 */
public class LoadAsyncTask extends AsyncTask<String, Void, byte[]> {

//    private WeakReference<ImageView> imageViewWeak;

    private ImageView imageView;
    private ProgressBar progressBar;


    public LoadAsyncTask(ImageView _imageView, ProgressBar _progressBar) {
//        imageViewWeak = new WeakReference<ImageView>(_imageView);
        this.imageView = _imageView;
        this.progressBar = _progressBar;

//        imageViewWeak.get().setImageBitmap();
    }

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    protected byte[] doInBackground(String... strings) {

        byte[] bytes = null;

        try {

//            String randUrl = getRandUrl_HttpClient(strings[0]);
//            Log.d(L15Activity.TAG, "Got url: " + randUrl);
//            if (randUrl == null) return null;
//            bytes = getImg_HttpClient(randUrl);

            String randUrl = getRandUrl_UrlConn(strings[0]);
            Log.d(L15Activity.TAG, "Got url: " + randUrl);
            if (randUrl == null) return null;
            bytes = getImg_UrlConn(randUrl);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;
    }


    @Override
    protected void onPostExecute(byte[] bytes) {
        progressBar.setVisibility(View.INVISIBLE);

        if (bytes == null) {
            Log.d(L15Activity.TAG, "Error loading");
            Toast.makeText(imageView.getContext(), "Error loading", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            imageView.setImageBitmap(bitmap);
        } catch (OutOfMemoryError _e) {
            _e.printStackTrace();
            Log.d(L15Activity.TAG, _e.toString());
            Toast.makeText(imageView.getContext(), _e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    private String getRandUrl_HttpClient(String _url) throws URISyntaxException, IOException {
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 15000);
        HttpConnectionParams.setSoTimeout(httpParams, 15000);

        URI uri = new URI(_url);

        HttpClient httpClient = new DefaultHttpClient(httpParams);
        HttpGet get = new HttpGet(uri);
        HttpResponse response = httpClient.execute(get);

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            String randUrl = EntityUtils.toString(response.getEntity());
            return randUrl;
        }

        return null;
    }


    private byte[] getImg_HttpClient(String _url) throws URISyntaxException, IOException {
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 15000);
        HttpConnectionParams.setSoTimeout(httpParams, 15000);

        URI uri = new URI(_url);

        HttpClient httpClient = new DefaultHttpClient(httpParams);
        HttpGet get = new HttpGet(uri);
        HttpResponse response = httpClient.execute(get);

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            byte[] bytes = EntityUtils.toByteArray(response.getEntity());
            return bytes;
        }

        return null;
    }


    private String getRandUrl_UrlConn(String _url) throws IOException {
        URL url = new URL(_url);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(10000);
        conn.setRequestMethod(HttpGet.METHOD_NAME);

        int respCode = conn.getResponseCode();
        if (respCode == 200) {
            final BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            final InputStreamReader isr = new InputStreamReader(conn.getInputStream());

            StringBuilder sb = new StringBuilder();
            String line;
            while((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        }

        return null;
    }


    private byte[] getImg_UrlConn(String _url) throws IOException {
        URL url = new URL(_url);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(10000);
        conn.setRequestMethod(HttpGet.METHOD_NAME);

        int respCode = conn.getResponseCode();
        if (respCode == 200) {
            InputStream is = conn.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

//            System.currentTimeMillis();
            int len;
            byte[] buffer = new byte[4096];
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }

            return baos.toByteArray();
        }

        return null;
    }

}
