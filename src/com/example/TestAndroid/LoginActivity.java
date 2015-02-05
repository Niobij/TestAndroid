package com.example.TestAndroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by ZOG on 12/15/2014.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

	private final String NAME = "name";
	private final String PASS = "1234";

	private EditText etName;
	private EditText etPass;
	private Button btnLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		etName = (EditText) findViewById(R.id.etName);
		etPass = (EditText) findViewById(R.id.etPass);
		btnLogin = (Button) findViewById(R.id.btnLogin);

		btnLogin.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btnLogin:
				onClickBtnLogin();
				break;
		}
	}

	private void onClickBtnLogin() {
		String name = etName.getText().toString();
		String pass = etPass.getText().toString();

		if (name.isEmpty() || pass.isEmpty()) {
			Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
			return;
		}

		if (!name.equals(NAME) || !pass.equals(PASS)) {
			Toast.makeText(this, "Error auth", Toast.LENGTH_SHORT).show();
			return;
		}

		Intent intent = new Intent(this, SecondActivity.class);
		startActivity(intent);
		finish();
	}

}
