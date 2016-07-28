package com.sogilis.sogimailer;

import android.content.Intent;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "SOGI---MAILER";

	private MailerResult r;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d(TAG, "onCreate");
		r = new MailerResult(new Handler());
		r.setReceiver(new Receiver() {
			@Override
			public void onReceiveResult(int resultCode, Bundle resultData) {
				Log.d(TAG, "onReceiveResult");
				Toast.makeText(MainActivity.this, "Message : <" + resultData.getString("TITI"), Toast.LENGTH_LONG).show();
			}
		});
	}

	public void click(View v)
	{
		Log.d(TAG, "Launching intent for service SogiMailer");
		EditText value2  = (EditText) findViewById(R.id.edit);
		Intent itt = new Intent();
		itt.setClassName("com.sogilis.sogimailer", "com.sogilis.sogimailer.SogiMailerService");
		itt.putExtra("TOTO", value2.getText().toString());
		itt.putExtra("TUTU", r);

		startService(itt);

	}

	// Defines our event interface for communication
	public interface Receiver {
		public void onReceiveResult(int resultCode, Bundle resultData);
	}

	private class MailerResult extends ResultReceiver {

		private Receiver receiver;

		public MailerResult(Handler h) {
			super(h);
		}

		// Setter for assigning the receiver
		public void setReceiver(Receiver receiver) {
			this.receiver = receiver;
		}

		// Delegate method which passes the result to the receiver if the receiver has been assigned
		@Override
		protected void onReceiveResult(int resultCode, Bundle resultData) {
			if (receiver != null) {
				receiver.onReceiveResult(resultCode, resultData);
			}
		}

	}
}
