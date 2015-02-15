package com.example.TestAndroid.l19;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Vasiliy on 16.02.2015.
 */
public class NotifAdapter extends BaseAdapter {

    private ArrayList<NotificationModel> mNotifs;
    private Context mContext;

    public NotifAdapter(Context _context, ArrayList<NotificationModel> _notifs) {
        mContext = _context;
        mNotifs = _notifs;
    }


    @Override
    public int getCount() {
        return mNotifs.size();
    }

    @Override
    public Object getItem(int position) {
        return mNotifs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NotificationModel notifModel = mNotifs.get(position);
        NotificationView notifView = new NotificationView(mContext);
        notifView.setNotifModel(notifModel);
        return notifView;
    }
}
