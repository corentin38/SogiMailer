package com.sogilis.sogimailer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class SogiMailerService extends Service {

	private static final String TAG = "MAILER";

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
	public IBinder onBind(Intent intent) {

		return new IHelloService.Stub() {

			public void hello(String holy) {
				Log.d(TAG, "hello in IHelloService impl");
				Toast.makeText(getApplicationContext(), "Hello ! You told me : <" + holy + ">", Toast.LENGTH_SHORT).show();
			}

		};

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
	}
}
