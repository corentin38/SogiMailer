package com.sogilis.sogimailer;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class SogiMailerService extends IntentService {

	private static final String TAG = "MAILER";

	static final String ACTION = "com.sogilis.sogimailer.ACTION_SEND";

	private static final String RESULT_KEY = "MAILER_RESULT";
	private static final String MESSAGE_KEY = "MAILER_MESSAGE";
	private static final String RETCODE_KEY = "MAILER_RETCODE";

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
		String holy = intent.getStringExtra(MESSAGE_KEY);

		Intent itt = new Intent(ACTION);
		itt.putExtra(RESULT_KEY, "OULA OULA ! <" + holy + ">");
		itt.putExtra(RETCODE_KEY, Activity.RESULT_OK);

		sendBroadcast(itt);
	}

}
