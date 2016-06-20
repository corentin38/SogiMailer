package com.sogilis.sogimailer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "SOGI---MAILER";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "Receiving Intent !!");

		String blabla = getIntent().getStringExtra("SOMETHING");
		Log.d(TAG, "We are proud to announce that we process a message : " + blabla);

		Intent res = new Intent();
		res.putExtra("SOMETHING", "JE VOUS AIIIIII ... COMMMMMPRRRRRIS");

		Log.d(TAG, "Finish  activity with result OK");
		setResult(RESULT_OK, res);
		finish();
	}
}
