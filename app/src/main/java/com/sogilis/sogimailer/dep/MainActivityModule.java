package com.sogilis.sogimailer.dep;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.sogilis.sogimailer.SogiMailerApplication;
import com.sogilis.sogimailer.ui.MainActivity;

import dagger.Module;
import dagger.Provides;

@Module (
		injects = MainActivity.class
)
public class MainActivityModule {

	private static final String RESULTMSG_KEY = "MAILER_RESULTMSG";
	private static final String RETCODE_KEY = "MAILER_RETCODE";

	@Provides
	public BroadcastReceiver providesBroadcastReceiver() {
		return new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				int resultCode = intent.getIntExtra(RETCODE_KEY, Activity.RESULT_CANCELED);
				String resultValue = intent.getStringExtra(RESULTMSG_KEY);

				if (resultCode == Activity.RESULT_OK) {
					Toast.makeText(SogiMailerApplication.ctx, "Mailer service <" + resultValue + ">", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(SogiMailerApplication.ctx, "ERROR <" + resultValue + ">", Toast.LENGTH_LONG).show();
				}
			}
		};
	}

}
