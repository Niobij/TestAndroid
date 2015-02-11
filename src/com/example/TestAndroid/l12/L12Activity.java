package com.example.TestAndroid.l12;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.TestAndroid.R;

/**
 * Created by ZOG on 1/19/2015.
 */
public class L12Activity extends Activity implements View.OnClickListener {

    private Button btnIntentService;
    private Button btnStartService;
    private Button btnStopService;
    private Button btnBind;
    private Button btnDoBinded;


    private MyService mMyService;
    private boolean mBound = false;

    private MediaPlayer mPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.activity_l12);

        findViewById(R.id.btnIntentService).setOnClickListener(this);
        findViewById(R.id.btnStartService).setOnClickListener(this);
        findViewById(R.id.btnStopService).setOnClickListener(this);
        findViewById(R.id.btnBind).setOnClickListener(this);
        findViewById(R.id.btnDoBinded).setOnClickListener(this);
        findViewById(R.id.btnUnbind).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnIntentService:
                onClickBtnIntentService();
                break;

            case R.id.btnStartService:
                onClickBtnStartService();
                break;

            case R.id.btnStopService:
                onClickBtnStopService();
                break;

            case R.id.btnBind:
                onClickBtnBind();
                break;

            case R.id.btnDoBinded:
                onClickBtnDoBound();
                break;

            case R.id.btnUnbind:
                onClickBtnUnbind();
                break;
        }
    }


    private final void onClickBtnIntentService() {
        Intent intent = new Intent(this, MyIntentService.class);
        intent.setData(Uri.parse("data string"));
        startService(intent);
    }


    private void onClickBtnStartService() {
        mPlayer = MediaPlayer.create(this, R.raw.super_mario_theme);
        mPlayer.start();
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }


    private void onClickBtnStopService() {
        if (mPlayer != null && mPlayer.isPlaying()) mPlayer.stop();
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }


    private void onClickBtnBind() {
        Intent intent = new Intent(this, MyService.class);
        mBound = bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }


    private void onClickBtnDoBound() {
        if (mBound && mMyService != null) mMyService.methodInService();
    }


    private void onClickBtnUnbind() {
        if (mBound) {
            unbindService(mServiceConnection);
            mBound = false;
            mMyService = null;
        }
    }


    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("my_service", "ServiceConnection#onServiceConnected");
            MyService.MyBinder myBinder = (MyService.MyBinder) iBinder;
            mMyService = myBinder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d("my_service", "ServiceConnection#onServiceDisconnected");
            mBound = false;
        }
    };

}
