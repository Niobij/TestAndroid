package com.example.TestAndroid.l19;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.TestAndroid.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.ArrayList;

/**
 * Created by ZOG on 2/12/2015.
 */
public class L19Activity extends Activity {

    public static final String TAG = L19Activity.class.getSimpleName();

    private ListView lvNotification;
    private static MyDbOpenHelper mMyDbOpenHelper;
    private static ArrayList<NotificationModel> notificationList;
    public static NotifAdapter myAdapter;

    private static final int NOTIFICATION_ID = 1234;

    private GoogleCloudMessaging gcm;
    private String regid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l19);


        lvNotification = (ListView) findViewById(R.id.lvNotification);
        mMyDbOpenHelper = new MyDbOpenHelper(this);
        notificationList = new ArrayList<NotificationModel>();
        loadNotificationsFromDB();



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
    protected void onStop() {
        super.onStop();


    }

    public static void writeNotificationToDb(Context context, final Intent intent) {

        NotificationModel currentNotification = new NotificationModel();

        currentNotification.message=intent.getStringExtra("message");
        currentNotification.title = intent.getStringExtra("title");
        currentNotification.subtitle = intent.getStringExtra("subtitle");
        currentNotification.tickerText = intent.getStringExtra("tickerText");
        currentNotification.vibrate = Integer.valueOf(intent.getStringExtra("vibrate"));
        currentNotification.sound = Integer.valueOf(intent.getStringExtra("sound"));

        SQLiteDatabase db = mMyDbOpenHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(MyDbOpenHelper.KEY_MESSAGE, currentNotification.message);
        contentValues.put(MyDbOpenHelper.KEY_TITLE, currentNotification.title);
        contentValues.put(MyDbOpenHelper.KEY_SUBTITLE, currentNotification.subtitle);
        contentValues.put(MyDbOpenHelper.KEY_TICKER_TEXT, currentNotification.tickerText);
        contentValues.put(MyDbOpenHelper.KEY_VIBRATE, currentNotification.vibrate);
        contentValues.put(MyDbOpenHelper.KEY_SOUND, currentNotification.sound);
        db.insert(MyDbOpenHelper.PUSHES_TABLE_NAME, null, contentValues);

        db.close();

        showNotification(currentNotification, context);

        notificationList.add(currentNotification);
        myAdapter.notifyDataSetChanged();

    }

    private static void showNotification(NotificationModel notif, Context context) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle(notif.title)
                .setSubText(notif.subtitle)
                .setContentText(notif.message)
                .setTicker(notif.tickerText)
                .setAutoCancel(true);

        if (notif.vibrate == 1) builder.setVibrate(new long[] {0, 100, 200, 300});
        if (notif.sound == 1) builder.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" +
                R.raw.smb_jump_small));

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void loadNotificationsFromDB(){

        SQLiteDatabase db = mMyDbOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(MyDbOpenHelper.PUSHES_TABLE_NAME, null, null, null, null, null, null);
        int messageIndex = cursor.getColumnIndex(MyDbOpenHelper.KEY_MESSAGE);
        int titleIndex = cursor.getColumnIndex(MyDbOpenHelper.KEY_TITLE);
        int subtitleIndex = cursor.getColumnIndex(MyDbOpenHelper.KEY_SUBTITLE);
        int tickerIndex = cursor.getColumnIndex(MyDbOpenHelper.KEY_TICKER_TEXT);
        int vibrateIndex = cursor.getColumnIndex(MyDbOpenHelper.KEY_VIBRATE);
        int soundIndex = cursor.getColumnIndex(MyDbOpenHelper.KEY_SOUND);
        while (cursor.moveToNext()) {
            try {
                NotificationModel currentNotification = new NotificationModel();
                currentNotification.message = cursor.getString(messageIndex);
                currentNotification.title = cursor.getString(titleIndex);
                currentNotification.subtitle = cursor.getString(subtitleIndex);
                currentNotification.tickerText = cursor.getString(tickerIndex);
                currentNotification.vibrate = Integer.valueOf(cursor.getString(vibrateIndex));
                currentNotification.sound = Integer.valueOf(cursor.getString(soundIndex));
                notificationList.add(currentNotification);
            }
            catch (IllegalStateException e){
                Log.d("tag", "IllegalStateException"+e);
            }
        }
        db.close();

        myAdapter = new NotifAdapter(this, notificationList);
        lvNotification.setAdapter(myAdapter);
        lvNotification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showNotification(notificationList.get(position), getApplicationContext());
            }
        });
    }

}
