package com.example.TestAndroid.l19;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.TestAndroid.R;

/**
 * Created by Vasiliy on 16.02.2015.
 */
public class NotificationView extends RelativeLayout {

    private TextView tvNotifData;

    public NotificationView(Context context) {
        super(context);

        inflate(context, R.layout.view_notif, this);

        tvNotifData = (TextView) findViewById(R.id.tvNotifData_VC);
    }

    public void setNotifModel(NotificationModel _model) {

        tvNotifData.setText(

                "Title: " + _model.title + '\n' +
                        "Message: " + _model.message
        );
    }
}
