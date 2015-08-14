package com.csscorp.jayakumarmanian.cadence;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



public class Login extends Activity {
	Intent i = null;
	EditText tv1, tv4;
	boolean flag = false;
	DatabaseHandler dbh = new DatabaseHandler(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		tv1 = (EditText) findViewById(R.id.phone2);
		tv4 = (EditText) findViewById(R.id.password2);
		//db = openOrCreateDatabase("user", MODE_PRIVATE, null);
	}

	public void action(View v) {
		SQLiteDatabase db = dbh.getReadableDatabase();
		switch (v.getId()) {
		case R.id.signin2:
			i = new Intent(this, Register.class);
			startActivityForResult(i, 500);
			overridePendingTransition(R.anim.slide_in_top,
					R.anim.slide_out_bottom);
			finish();
			break;
		case R.id.start:
			String mobile_no = tv1.getText().toString();
			String password = tv4.getText().toString();
			if (mobile_no == null || mobile_no == "" || mobile_no.length() < 10) {
				show("Please Enter Correct mobile number.");
			} else if (password == null || password == ""
					|| password.length() < 6) {
				show("Please Enter Correct Password.");
			} else {
				Cursor c = db
						.rawQuery("select * from login where mobile_no='"
								+ mobile_no + "' and password='" + password
								+ "'", null);
				c.moveToFirst();
				if (c.getCount() > 0) {
					i = new Intent(this, MainActivity.class);
					i.putExtra("MobileNo",mobile_no);
					startActivityForResult(i, 500);
					overridePendingTransition(R.anim.slide_in_right,
							R.anim.slide_out_left);
					db.close();
					finish();
				} else
					show("Wrong Password or Mobile number.");

			}
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	public void show(String str) {
		Toast.makeText(this, str, Toast.LENGTH_LONG).show();
	}

}
