package com.sogilis.sogimailer;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class SogiMailerService extends IntentService {

	private static final String TAG = "MAILER";
	static final String ACTION = "com.sogilis.sogimailer.ACTION_SEND";

    public SogiMailerService() {
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

		Intent itt = new Intent(ACTION);
		itt.putExtra("TITI", "OULA OULA ! <" + holy + ">");
		itt.putExtra("resultCode", -1);

		sendBroadcast(itt);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
	}
}
