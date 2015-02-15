package com.example.myapp.lesson19;


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
import android.widget.SimpleAdapter;
import com.example.myapp.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import java.util.ArrayList;
import java.util.HashMap;


public class L19Activity extends Activity{

    private static final String MESSAGE = "message";
    private static final String TITLE =  "title";
    private static final String SUBTITLE = "subtitle";
    private static final String TICKER_TEXT = "tickerText";
    private static final String VIBRATE = "vibrate";
    private static final String SOUND = "sound";
    private static ListView lvNotification;
    public static final String TAG = L19Activity.class.getSimpleName();
    private static ArrayList<HashMap<String, String>> notificationList;
    public  static SimpleAdapter simpleAdapter;

    private static final int NOTIFICATION_ID = 1234;
    private static Context context;
    private MyDbOpenHelper database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l19);

        lvNotification = (ListView) findViewById(R.id.lvNotification);

        if (!GcmPushHelper.checkPlayServices(this)) return;

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String regid = GcmPushHelper.getRegistrationId(this);
        if (regid.isEmpty()) {
            Log.d(TAG, "registered is empty");
            GcmPushHelper.registerGcmAsync(this, gcm);
        }
        context = getApplicationContext();
        database = new MyDbOpenHelper(context);
        loadDataFromDatabase();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveDataInDatabase();
    }

    private void clearDatabase(){
        SQLiteDatabase db = database.getReadableDatabase();
        db.delete(MyDbOpenHelper.DICTIONARY_TABLE_NAME, null, null);
    }

    private void saveDataInDatabase(){
        if (notificationList.isEmpty()) return;

        clearDatabase();

        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        for (HashMap<String, String> aNotificationList : notificationList) {

            contentValues.put(MyDbOpenHelper.MESSAGE, aNotificationList.get(MESSAGE));
            contentValues.put(MyDbOpenHelper.TITLE, aNotificationList.get(TITLE));
            contentValues.put(MyDbOpenHelper.SUBTITLE, aNotificationList.get(SUBTITLE));
            contentValues.put(MyDbOpenHelper.TICKER_TEXT, aNotificationList.get(TICKER_TEXT));
            contentValues.put(MyDbOpenHelper.VIBRATE, aNotificationList.get(VIBRATE));
            contentValues.put(MyDbOpenHelper.SOUND, aNotificationList.get(SOUND));

            db.insert(MyDbOpenHelper.DICTIONARY_TABLE_NAME, null, contentValues);

        }
        database.close();
    }

    private void loadDataFromDatabase(){
        createNotificationList();

        SQLiteDatabase db = database.getReadableDatabase();

        Cursor cursor = db.query(MyDbOpenHelper.DICTIONARY_TABLE_NAME, null, null, null, null, null, null);

        int messageIndex = cursor.getColumnIndex(MyDbOpenHelper.MESSAGE);
        int titleIndex = cursor.getColumnIndex(MyDbOpenHelper.TITLE);
        int subtitleIndex = cursor.getColumnIndex(MyDbOpenHelper.SUBTITLE);
        int tickerIndex = cursor.getColumnIndex(MyDbOpenHelper.TICKER_TEXT);
        int vibrateIndex = cursor.getColumnIndex(MyDbOpenHelper.VIBRATE);
        int soundIndex = cursor.getColumnIndex(MyDbOpenHelper.SOUND);

        while (cursor.moveToNext()) {
            try {

                HashMap<String, String> notificationData = new HashMap<String, String>();

                notificationData.put(MESSAGE, cursor.getString(messageIndex));
                notificationData.put(TITLE, cursor.getString(titleIndex));
                notificationData.put(SUBTITLE, cursor.getString(subtitleIndex));
                notificationData.put(TICKER_TEXT, cursor.getString(tickerIndex));
                notificationData.put(VIBRATE, cursor.getString(vibrateIndex));
                notificationData.put(SOUND, cursor.getString(soundIndex));


                addNotificationToList(notificationData);
            }
            catch (IllegalStateException e){
                Log.d("tag", "IllegalStateException"+e);
            }
        }

        db.close();
    }



    @Override
    protected void onResume() {
        super.onResume();
        GcmPushHelper.checkPlayServices(this);
    }

    public static void parseIntent(Intent intent) {

        HashMap<String, String> notificationData = new HashMap<String, String>();

        String message = intent.getStringExtra(MESSAGE);
        String title = intent.getStringExtra(TITLE);
        String subtitle = intent.getStringExtra(SUBTITLE);
        String tickerText = intent.getStringExtra(TICKER_TEXT);
        int vibrate = Integer.valueOf(intent.getStringExtra(VIBRATE));
        int sound = Integer.valueOf(intent.getStringExtra(SOUND));

        notificationData.put(MESSAGE, message);
        notificationData.put(TITLE, title);
        notificationData.put(SUBTITLE, subtitle);
        notificationData.put(TICKER_TEXT, tickerText);
        notificationData.put(VIBRATE, Integer.toString(vibrate));
        notificationData.put(SOUND, Integer.toString(sound));

        addNotificationToList(notificationData);
        showNotification(notificationData);

    }

    public static void showNotification(HashMap<String, String> notificationData){
        String title = notificationData.get(MESSAGE);
        String subtitle = notificationData.get(TITLE);
        String message = notificationData.get(SUBTITLE);
        String tickerText = notificationData.get(TICKER_TEXT);
        int vibrate = Integer.parseInt(notificationData.get(VIBRATE));
        int sound = Integer.parseInt(notificationData.get(SOUND));


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

    private void createNotificationList(){

        notificationList = new ArrayList<HashMap<String, String>>();

        String[] from = {MESSAGE, TITLE, SUBTITLE, TICKER_TEXT};
        int[] to = {R.id.tvMessage, R.id.tvTitle, R.id.tvSubTitle, R.id.tvTickerText};

        simpleAdapter = new SimpleAdapter(context, notificationList, R.layout.not_item, from, to);
        lvNotification.setAdapter(simpleAdapter);
        lvNotification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showNotification(notificationList.get(position));
            }
        });
        Log.d(TAG, "crate list");
    }

    private static void addNotificationToList(HashMap<String, String> notificationData){

        notificationList.add(notificationData);
        simpleAdapter.notifyDataSetChanged();
    }

}
