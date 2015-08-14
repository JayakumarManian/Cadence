package com.csscorp.jayakumarmanian.cadence;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public class LoginScreen extends Activity {

	Intent i = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.login_activity);
	}

	public void login_sigin(View v) {
		switch (v.getId()) {
			case R.id.log_in:
				i = new Intent(this, Login.class);
				startActivityForResult(i, 500);
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);
				break;
			case R.id.sign_in:
				i = new Intent(this, Register.class);
				startActivityForResult(i, 500);
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);
				;
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
}