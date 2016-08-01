package com.sogilis.sogimailer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "SOGIMAILER_ACTIVITY";

	private static final String SOGIMAILER_ACTION = "com.sogilis.sogimailer.ACTION_SEND";

	private static final String RESULTMSG_KEY = "MAILER_RESULTMSG";
	private static final String RETCODE_KEY = "MAILER_RETCODE";

	private static final String OPT_RECIPIENTS = "MAILER_OPT_RECIPIENTS";
	private static final String OPT_SUBJECT = "MAILER_OPT_SUBJECT";
	private static final String OPT_BODY = "MAILER_OPT_BODY";
	private static final String OPT_PASSWORD = "MAILER_OPT_PASSWORD";

    private BroadcastReceiver testReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int resultCode = intent.getIntExtra(RETCODE_KEY, RESULT_CANCELED);
	        Log.d(TAG, "onReceive " + resultCode + " is it eq to ? " + RESULT_OK);
	        String resultValue = intent.getStringExtra(RESULTMSG_KEY);

            if (resultCode == RESULT_OK) {
	            Log.d(TAG, "onReceive  SUCCESS in BCR, result message : " + resultValue);
	            Toast.makeText(MainActivity.this, "Response!!!! <" + resultValue + ">", Toast.LENGTH_SHORT).show();
            } else {
	            Log.d(TAG, "onReceive in BCR");
	            Log.d(TAG, "onReceive FAILURE in BCR, result message : " + resultValue);
	            Toast.makeText(MainActivity.this, "FAIL!!!! <" + resultValue + ">", Toast.LENGTH_SHORT).show();
            }
        }
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d(TAG, "onCreate");

		SharedPreferences settings = getSharedPreferences(
			"sogimailerPrefs",
			Context.MODE_PRIVATE);

		String recip = settings.getString("recipients", "");
		String subject = settings.getString("subject", "");
		String body = settings.getString("body", "");
		String password = settings.getString("password", "");

		EditText recipientsET  = (EditText) findViewById(R.id.recipients);
		recipientsET.setText(recip);

		EditText subjectET  = (EditText) findViewById(R.id.subject);
		subjectET.setText(subject);

		EditText bodyET  = (EditText) findViewById(R.id.body);
		bodyET.setText(body);

		EditText passwordET  = (EditText) findViewById(R.id.password);
		passwordET.setText(password);
	}

	@Override
	protected void onResume() {
        IntentFilter filter = new IntentFilter(SOGIMAILER_ACTION);
        registerReceiver(testReceiver, filter);
		super.onResume();
	}

	@Override
	protected void onPause() {
		unregisterReceiver(testReceiver);
		super.onPause();
	}

	public void click(View v)
	{
		Log.d(TAG, "Launching intent for service SogiMailer");
		EditText recipientsET  = (EditText) findViewById(R.id.recipients);
		EditText subjectET  = (EditText) findViewById(R.id.subject);
		EditText bodyET  = (EditText) findViewById(R.id.body);
		EditText passwordET  = (EditText) findViewById(R.id.password);

		String recipients = recipientsET.getText().toString();
		String subject = subjectET.getText().toString();
		String body = bodyET.getText().toString();
		String password = passwordET.getText().toString();

		Intent itt = new Intent(SOGIMAILER_ACTION);
		itt.setPackage("com.sogilis.sogimailer");

		itt.putExtra(OPT_RECIPIENTS, recipients);
		itt.putExtra(OPT_SUBJECT, subject);
		itt.putExtra(OPT_BODY, body);
		itt.putExtra(OPT_PASSWORD, password);

		startService(itt);

		SharedPreferences settings = getSharedPreferences(
				"sogimailerPrefs",
				Context.MODE_PRIVATE);

		SharedPreferences.Editor ed = settings.edit();

		ed.putString("recipients", recipients);
		ed.putString("subject", subject);
		ed.putString("body", body);
		ed.putString("password", password);

		ed.commit();
	}

}
