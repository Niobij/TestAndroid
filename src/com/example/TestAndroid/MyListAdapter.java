package com.example.TestAndroid;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ZOG on 12/25/2014.
 */
public class MyListAdapter extends BaseAdapter {

	private Context mContex;
	private ArrayList<ContactObject> mContacts;
	private LayoutInflater mLayoutInflater;

	public MyListAdapter(Context _context, ArrayList<ContactObject> _contacts) {
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

		View itemView = mLayoutInflater.inflate(R.layout.list_item_contact, viewGroup, false);

		TextView tvName = (TextView) itemView.findViewById(R.id.tvName);
		TextView tvPhone = (TextView) itemView.findViewById(R.id.tvPhone);

		ContactObject contactObject = mContacts.get(position);

		tvName.setText(contactObject.name);
		tvPhone.setText(contactObject.phone);

		Log.d("adapter", "created view at pos: " + position);

		return itemView;
	}
}
