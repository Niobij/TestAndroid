package com.example.TestAndroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

/**
 * Created by ZOG on 12/25/2014.
 */
public class MyListActivity extends Activity implements View.OnClickListener {

	private Button btnFill;
	private ListView lvList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_list);

		btnFill = (Button) findViewById(R.id.btnFill);
		lvList = (ListView) findViewById(R.id.lvList);

		btnFill.setOnClickListener(this);

//		Typeface typeface = Typeface.createFromFile("//assets/font.ttf");
//		btnFill.setTypeface(typeface);
	}

	@Override
	public void onClick(View view) {
		if (view == btnFill) {
//			prepareListWithArray();
//			prepareListCursor();
//			prepareListNameNumber();
//			prepareCustomAdapter();
			prepareFastAdapter();
		}
	}

	private void prepareListWithArray() {
		String[] array = new String[] {
			"First",
			"Second",
			"Third",
			"Fourth"
		};

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				this,
				android.R.layout.simple_list_item_1,
				array);

		lvList.setAdapter(arrayAdapter);
		lvList.setOnItemClickListener(onItemClickListener);
	}

	private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
			Toast.makeText(MyListActivity.this, "Clicked: " + position, Toast.LENGTH_SHORT).show();
		}
	};

	private void prepareListCursor() {
		Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_1,
				cursor,
				new String[] { ContactsContract.Contacts.DISPLAY_NAME_PRIMARY },
				new int[] { android.R.id.text1 });

		lvList.setAdapter(adapter);
	}

	private final void prepareListNameNumber() {
		ArrayList<String> contactsArray = new ArrayList<String>();

		Cursor cursorContacts = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		int contactNameIndex = cursorContacts.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY);
		while (cursorContacts.moveToNext()) {
			String contactName = cursorContacts.getString(contactNameIndex);

			String contactId = cursorContacts.getString(cursorContacts.getColumnIndex(ContactsContract.Contacts._ID));

			Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
					null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
			int phoneIndex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

			String number = "";
			while (phones.moveToNext()) {
				number += " " + phones.getString(phoneIndex);
			}
			phones.close();

			contactsArray.add(contactName + "\n" + number);
		}
		cursorContacts.close();

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,
				contactsArray);
		lvList.setAdapter(arrayAdapter);
	}

	private ArrayList<ContactObject> getContactsArray() {
		ArrayList<ContactObject> contactsArray = new ArrayList<ContactObject>();

		Cursor cursorContacts = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		int contactNameIndex = cursorContacts.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY);
		while (cursorContacts.moveToNext()) {
			String contactName = cursorContacts.getString(contactNameIndex);

			String contactId = cursorContacts.getString(cursorContacts.getColumnIndex(ContactsContract.Contacts._ID));

			Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
					null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
			int phoneIndex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

			String number = "";
			while (phones.moveToNext()) {
				number += " " + phones.getString(phoneIndex);
			}
			phones.close();

			contactsArray.add(new ContactObject(contactName, number));
		}
		cursorContacts.close();

		return  contactsArray;
	}

	private void prepareCustomAdapter() {
		ArrayList<ContactObject> contactObjects = getContactsArray();

		MyListAdapter myListAdapter = new MyListAdapter(this, contactObjects);
		lvList.setAdapter(myListAdapter);
	}

	private void prepareFastAdapter() {
		ArrayList<ContactObject> contactObjects = getContactsArray();

		MyFastAdapter myFastAdapter = new MyFastAdapter(this, contactObjects);
		lvList.setAdapter(myFastAdapter);
	}
}
