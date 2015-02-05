package com.example.TestAndroid;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by ZOG on 12/25/2014.
 */
public class MyFastAdapter extends BaseAdapter {

	private Context mContex;
	private ArrayList<ContactObject> mContacts;
	private LayoutInflater mLayoutInflater;

	public MyFastAdapter(Context _context, ArrayList<ContactObject> _contacts) {
		mContex = _context;
		mContacts = _contacts;
		mLayoutInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mContacts.size();
	}

	@Override
	public Object getItem(int position) {
		return mContacts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {

		ViewHolder viewHolder;

		if (view == null) {
			view = mLayoutInflater.inflate(R.layout.list_item_contact, viewGroup, false);

			viewHolder = new ViewHolder();
			viewHolder.tvName = (TextView) view.findViewById(R.id.tvName);
			viewHolder.tvPhone = (TextView) view.findViewById(R.id.tvPhone);

			view.setTag(viewHolder);

			Log.d("adapter", "created view at pos: " + position);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		ContactObject contactObject = mContacts.get(position);

		viewHolder.tvName.setText(contactObject.name);
		viewHolder.tvPhone.setText(contactObject.phone);

		return view;
	}

}
