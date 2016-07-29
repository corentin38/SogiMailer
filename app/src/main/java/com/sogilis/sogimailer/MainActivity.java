package com.sogilis.sogimailer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "SOGI---MAILER";

	private static final String RESULT_KEY = "MAILER_RESULT";
	private static final String MESSAGE_KEY = "MAILER_MESSAGE";
	private static final String RETCODE_KEY = "MAILER_RETCODE";
	private static final String SOGIMAILER_ACTION = "com.sogilis.sogimailer.ACTION_SEND";

	private String res;

    private BroadcastReceiver testReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int resultCode = intent.getIntExtra(RETCODE_KEY, RESULT_CANCELED);
	        Log.d(TAG, "onReceive " + resultCode + " is it eq to ? " + RESULT_OK);
            if (resultCode == RESULT_OK) {
                String resultValue = intent.getStringExtra(RESULT_KEY);
	            res = resultValue;
	            Log.d(TAG, "onReceive in BCR");
	            Toast.makeText(MainActivity.this, "Response!!!! <" + res + ">", Toast.LENGTH_SHORT).show();
            }
        }
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d(TAG, "onCreate");
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
		EditText value2  = (EditText) findViewById(R.id.edit);
		Intent itt = new Intent(SOGIMAILER_ACTION);
		itt.setPackage("com.sogilis.sogimailer");
		itt.putExtra(MESSAGE_KEY, value2.getText().toString());

		startService(itt);

	}

}
