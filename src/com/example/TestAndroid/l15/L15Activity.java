package com.example.TestAndroid.l15;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.TestAndroid.R;

/**
 * Created by ZOG on 1/29/2015.
 */
public class L15Activity extends Activity implements View.OnClickListener {

    public static final String TAG = L15Activity.class.getSimpleName();

    private Button btnNetState;
    private Button btnLoadCat;
    private ImageView ivCat;
    private ProgressBar pbLoading;

    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l15);

        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

        findViewById(R.id.btnNetState_AL15).setOnClickListener(this);
        findViewById(R.id.btnLoadCat_AL15).setOnClickListener(this);
        ivCat = (ImageView) findViewById(R.id.ivCat_AL15);
        pbLoading = (ProgressBar) findViewById(R.id.pbLoading_AL15);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNetState_AL15:
                onClickBtnNetState();
                break;

            case R.id.btnLoadCat_AL15:
                onClickBtnLoadCat();
                break;
        }
    }

    private void onClickBtnNetState() {

        String status = "";

        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            status += "Connected: " + networkInfo.getTypeName() + '\n';
        } else {
            status += "No connection" + '\n';
        }


        NetworkInfo networkInfoWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo networkInfoMobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        status += "Wifi: " + (networkInfoWifi != null && networkInfoWifi.isConnected()) + '\n';
        status += "Mobile: " + (networkInfoMobile != null && networkInfoMobile.isConnected());

        mToast.setText(status);
        mToast.show();
    }

    private void onClickBtnLoadCat() {
        new LoadAsyncTask(ivCat, pbLoading).execute("http://random.cat/meow");
    }


    private static class MyClass {

    }

}
