package com.example.TestAndroid.l19;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.example.TestAndroid.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Created by ZOG on 2/12/2015.
 */
public class L19Activity extends Activity implements View.OnClickListener {

    public static final String TAG = L19Activity.class.getSimpleName();

    private EditText etLedColor;

    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 1234;

    private GoogleCloudMessaging gcm;
    private String regid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l19);

        etLedColor = (EditText) findViewById(R.id.etLedColor_AL19);

        findViewById(R.id.btnAddNotification_AL19).setOnClickListener(this);
        findViewById(R.id.btnUpdateNotification_AL19).setOnClickListener(this);
        findViewById(R.id.btnRemoveNotification_AL19).setOnClickListener(this);

        if (!GcmPushHelper.checkPlayServices(this)) return;

        gcm = GoogleCloudMessaging.getInstance(this);
        regid = GcmPushHelper.getRegistrationId(this);
        if (regid.isEmpty()) {
            Log.d(TAG, "regid is empty");
            GcmPushHelper.registerGcmAsync(this, gcm);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        GcmPushHelper.checkPlayServices(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddNotification_AL19:
                onClickAddNotification();
                break;

            case R.id.btnUpdateNotification_AL19:
                onClickUpdateNotification();
                break;

            case R.id.btnRemoveNotification_AL19:
                onClickRemoveNotification();
                break;
        }
    }


    private void onClickAddNotification() {
        mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setAutoCancel(true)
//                .setLights(Color.GREEN, 100, 100)
                ;

        Intent resultIntent = new Intent(this, L19Activity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, resultIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        mBuilder.setContentIntent(pendingIntent);

        Notification notification = mBuilder.build();
        notification.ledARGB = etLedColor.getText().toString().isEmpty() ? 0xffff00ff : Color.parseColor(etLedColor.getText().toString());
        notification.ledOnMS = 100;
        notification.ledOffMS = 100;
        notification.flags = Notification.FLAG_SHOW_LIGHTS;

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, notification);
    }


    private void onClickUpdateNotification() {
        if (mBuilder == null || mNotificationManager == null) return;

        mBuilder.setContentText("Updated: " + System.currentTimeMillis() % 10000);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }


    private void onClickRemoveNotification() {
        if (mNotificationManager == null) return;

        mNotificationManager.cancel(NOTIFICATION_ID);
    }


    public static void showNotification(Context context, final Intent intent) {
        String message = intent.getStringExtra("message");
        String title = intent.getStringExtra("title");
        String subtitle = intent.getStringExtra("subtitle");
        String tickerText = intent.getStringExtra("tickerText");
        int vibrate = Integer.valueOf(intent.getStringExtra("vibrate"));
        int sound = Integer.valueOf(intent.getStringExtra("sound"));

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle(title)
                .setSubText(subtitle)
                .setContentText(message)
                .setTicker(tickerText)
                .setAutoCancel(true);

        if (vibrate == 1) builder.setVibrate(new long[] {0, 100, 200, 300});
        if (sound == 1) builder.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" +
        R.raw.smb_jump_small));

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

}
