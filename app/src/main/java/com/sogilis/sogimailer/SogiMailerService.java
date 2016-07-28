package com.sogilis.sogimailer;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

public class SogiMailerService extends IntentService {

	private static final String TAG = "MAILER";

    public SogiMailerService() {
        // Used to name the worker thread, important only for debugging.
        super("test-service");
    }

    @Override
    public void onCreate() {
        super.onCreate();
	    Log.d(TAG, "onCreate");
    }

	/**
	 * When binding to the service, we return an interface to our messenger
	 * for sending messages to the service.
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "hello in IHelloService impl");
		String holy = intent.getStringExtra("TOTO");
		ResultReceiver rr = intent.getParcelableExtra("TUTU");
		if (rr != null) {
			Bundle b = new Bundle();
			b.putString("TITI", "oula oula ! " + holy);
			rr.send(-1, b);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
	}
}
