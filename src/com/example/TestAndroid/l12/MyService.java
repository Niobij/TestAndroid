package com.example.TestAndroid.l12;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.BitSet;

/**
 * Created by ZOG on 1/19/2015.
 */
public class MyService extends Service {

    private static final String TAG = "my_service";

    private MyBinder mBinder = new MyBinder();


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "MyService#onCreate");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "MyService#onStartCommand");
//        stopSelf();
//        return super.onStartCommand(intent, flags, startId);
//        startInForeground();
        return START_STICKY;
    }



    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "MyService#onBind");
        return mBinder;
    }


    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "MyService#onUnbind");
//        return super.onUnbind(intent);
        return true;
    }


    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "MyService#onRebind");
        super.onRebind(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MyService#onDestroy");
    }


    private void startInForeground() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.sym_def_app_icon)
                        .setTicker("Ticker text")
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");

        Intent notificationIntent = new Intent(this, L12Activity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();

        startForeground(1, notification);
    }


    public final void methodInService() {
        Log.d(TAG, "MyService#methodInService");
    }


    public class MyBinder extends Binder {

        public MyService getService() {
            return MyService.this;
        }

    }

}
