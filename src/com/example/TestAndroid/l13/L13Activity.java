package com.example.TestAndroid.l13;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import com.example.TestAndroid.R;

/**
 * Created by ZOG on 1/22/2015.
 */
public class L13Activity extends Activity implements View.OnClickListener {

    public static final String TAG = "receiver_lesson";

    public static final String actionStartService = "com.example.TestAndroid.l13.start_service";
    public static final String actionStopService = "com.example.TestAndroid.l13.stop_service";

    private final String myAction = "com.example.TestAndroid.l13.my_action";

    private MyReceiver mMyReceiver;
    private IntentFilter mIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l13);

        findViewById(R.id.btnSend).setOnClickListener(this);
        findViewById(R.id.btnStartService_l13).setOnClickListener(this);
        findViewById(R.id.btnStopService_l13).setOnClickListener(this);

        mMyReceiver = new MyReceiver();

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.intent.action.BATTERY_LOW");
        mIntentFilter.addAction("android.intent.action.BATTERY_OKAY");
        mIntentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        mIntentFilter.addAction(myAction);
        mIntentFilter.addAction(actionStartService);
        mIntentFilter.addAction(actionStopService);
    }


    @Override
    protected void onResume() {
        super.onResume();

//        registerReceiver(mMyReceiver, mIntentFilter);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMyReceiver, mIntentFilter);
    }


    @Override
    protected void onPause() {
        super.onPause();

//        unregisterReceiver(mMyReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMyReceiver);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSend:
                onClickBtnSend();
                break;

            case R.id.btnStartService_l13:
                onClickBtnStartService();
                break;

            case R.id.btnStopService_l13:
                onClickBtnStopService();
                break;
        }
    }


    private void onClickBtnSend() {
        Intent intent = new Intent();
        intent.setAction(myAction);

//        sendBroadcast(intent);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void onClickBtnStartService() {
        Intent intent = new Intent(actionStartService);

        MyObject myObject = new MyObject();
        myObject.data = "string data";
        intent.putExtra("name", myObject);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void onClickBtnStopService() {
        Intent intent = new Intent(actionStopService);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}