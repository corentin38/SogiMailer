package com.sogilis.sogimailer;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.sogilis.sogimailer.dude.ProfileDude;
import com.sogilis.sogimailer.dude.ProfileDudeImpl;
import com.sogilis.sogimailer.mail.Mailer;
import com.sogilis.sogimailer.mail.ProfileFactory;
import com.sogilis.sogimailer.svc.MailerService;
import com.sogilis.sogimailer.svc.ProfileMailRequest;
import com.sogilis.sogimailer.ui.AddActivity;
import com.sogilis.sogimailer.ui.EditActivity;
import com.sogilis.sogimailer.ui.HomeFragment;
import com.sogilis.sogimailer.ui.MainActivity;
import com.sogilis.sogimailer.ui.TestMailDialog;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module (
		injects = {
				MainActivity.class,
				ProfileMailRequest.class,
				EditActivity.class,
				HomeFragment.class,
				AddActivity.class,
				TestMailDialog.class
		}
)
public class SogiMailerModule {

	private static final String RESULTMSG_KEY = "MAILER_RESULTMSG";
	private static final String RETCODE_KEY = "MAILER_RETCODE";

	@Provides BroadcastReceiver providesBroadcastReceiver() {
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

	@Provides @Singleton
	ProfileDude providesProfileDude() {
		return new ProfileDudeImpl();
	}

	@Provides @Singleton Mailer providesMailer() {
		return new Mailer(ProfileFactory.simplest());
	}
}
