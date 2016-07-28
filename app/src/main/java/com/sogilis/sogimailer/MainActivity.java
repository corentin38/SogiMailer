package com.sogilis.sogimailer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "SOGI---MAILER";

	private String res;

    private BroadcastReceiver testReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int resultCode = intent.getIntExtra("resultCode", RESULT_CANCELED);
	        Log.d(TAG, "onReceive " + resultCode + " is it eq to ? " + RESULT_OK);
            if (resultCode == RESULT_OK) {
                String resultValue = intent.getStringExtra("TITI");
	            res = resultValue;
	            Log.d(TAG, "onReceive in BCR");
	            doit();
            }
        }
    };

	private void doit() {
		Log.d(TAG, "DO IT !!!!!!!");
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Log.d(TAG, "On ui Thread, I presume ?");
				Toast.makeText(MainActivity.this, "Response!!!! <" + res + ">", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d(TAG, "onCreate");
	}

	@Override
	protected void onResume() {
        IntentFilter filter = new IntentFilter("com.sogilis.sogimailer.ACTION_SEND");
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
		Intent itt = new Intent();
		itt.setClassName("com.sogilis.sogimailer", "com.sogilis.sogimailer.SogiMailerService");
		itt.putExtra("TOTO", value2.getText().toString());

		startService(itt);

	}

}
