package com.example.TestAndroid;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends Activity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

	public final String TAG = "debug_tag";
	private final int ADDED_MENU = 1002343;

	private Button btnDialog;
	private TextView tvText;
	private TextView tvTextPopup;
	private Button btnActionBar;
;

	private PopupMenu popupMenu;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Log.d(TAG, "onCreate");

		btnDialog = (Button) findViewById(R.id.btnDialog);
		btnDialog.setOnClickListener(this);

		tvText = (TextView) findViewById(R.id.tvText);
		registerForContextMenu(tvText);

		tvTextPopup = (TextView) findViewById(R.id.tvTextPopup);
		tvTextPopup.setOnClickListener(this);

		popupMenu = new PopupMenu(this, tvTextPopup);
		popupMenu.inflate(R.menu.popup_menu);
		popupMenu.setOnMenuItemClickListener(this);

		btnActionBar = (Button) findViewById(R.id.btnActionBar);
		btnActionBar.setOnClickListener(this);

//		getActionBar().setDisplayShowHomeEnabled(false);
//		getActionBar().setDisplayShowTitleEnabled(false);

		prepareCustomActionBar();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					test();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	//region Lifecycle
	@Override
	protected void onStart() {
		super.onStart();
		Log.e(TAG, "err onStart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i(TAG, "info onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, "onStop");
	}

	@Override
	public void onRestart() {
		super.onRestart();
		Log.d(TAG, "onRestart");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
	}
	//endregion

	private void prepareCustomActionBar() {
		ActionBar ab = getActionBar();
		ab.setCustomView(R.layout.ab_custom);
		ab.setDisplayShowCustomEnabled(true);

		ImageView ivAb = (ImageView) ab.getCustomView().findViewById(R.id.ivAb);
		TextView tvAb = (TextView) ab.getCustomView().findViewById(R.id.tvAb);
		Button btnAb = (Button) ab.getCustomView().findViewById(R.id.btnAb);

		btnAb.setOnClickListener(this);
	}


	private String test() throws IOException {
		URL url = new URL("http://www.google.com");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		int responseCode = connection.getResponseCode();

		BufferedReader in = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));

		StringBuilder stringBuilder = new StringBuilder();
		String line;
		while ((line = in.readLine()) != null) {
			stringBuilder.append(line);
		}
		return stringBuilder.toString();
	}


	@Override
	public void onClick(View view) {
		if (view == btnDialog) {
			onBtnClickDialog1();
		} else if (view == tvTextPopup){
			onClickPopup();
		} else if (view == btnActionBar) {
			onClickBtnActionBar();
		}

		if (view.getId() == R.id.btnAb) {
			onClickBtnInAb();
		}
	}

	private void onClickBtnInAb() {
		Toast.makeText(this, "onClickBtnInAb", Toast.LENGTH_SHORT).show();
	}

	private void onClickBtnActionBar() {
		ActionBar ab = getActionBar();
		ab.hide();
		ab.show();
	}

	public void onBtnClickDialog1() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Dialog Title");
		builder.setMessage("Message");

		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				dialogInterface.dismiss();
			}
		});

		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				dialogInterface.dismiss();
			}
		});

		builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				dialogInterface.cancel();
			}
		});

		builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialogInterface) {

			}
		});

		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialogInterface) {

			}
		});

		builder.show();
	}

	public void onBtnClickDialog2() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Dialog Title");
		builder.setMessage("Pick color");
		builder.setItems(R.array.dialog_colors, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int which) {
				Toast.makeText(MainActivity.this, "Choose: " + which, Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void onClickWeb() {
		Intent intent = new Intent(this.getApplicationContext(), WebActivity.class);
		MainActivity.this.startActivity(intent);
		finish();
	}

	public void onClickPopup() {
		popupMenu.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main_menu, menu);

		MenuItem menuItem = menu.findItem(R.id.menu_delete);
		ShareActionProvider mShareActionProvider = (ShareActionProvider) menuItem.getActionProvider();
		mShareActionProvider.setShareIntent(getDefaultIntent());

		return true;
	}


	private Intent getDefaultIntent() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/*");
		return intent;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
//			case R.id.main_delete:
//				Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
//				return true;
//
//			case R.id.main_help:
//				Toast.makeText(this, "Help", Toast.LENGTH_SHORT).show();
//				return true;

			case ADDED_MENU:
				Toast.makeText(this, "Help", Toast.LENGTH_SHORT).show();
				return true;

		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		menu.add(0, ADDED_MENU, 0, "Added");

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.contex_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.context_menu_delete:
				Toast.makeText(this, "Delete Context", Toast.LENGTH_SHORT).show();
				return true;

			case R.id.context_menu_help:
				Toast.makeText(this, "Help Context", Toast.LENGTH_SHORT).show();
				return true;

		}
		return super.onContextItemSelected(item);
	}

	@Override
	public boolean onMenuItemClick(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
			case R.id.popup_menu_delete:
				return true;

			case R.id.popup_menu_help:
				return true;

			default:
				return false;
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putSerializable("my", new MyObject());
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		MyObject myObject = (MyObject) savedInstanceState.getSerializable("my");
		super.onRestoreInstanceState(savedInstanceState);
	}

	class MyObject implements Serializable {
		public int a;
	}
}
