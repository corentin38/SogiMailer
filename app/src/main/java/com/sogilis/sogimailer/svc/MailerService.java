package com.sogilis.sogimailer.svc;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.sogilis.sogimailer.SogiMailerApplication;
import com.sogilis.sogimailer.dude.ProfileDude;
import com.sogilis.sogimailer.mail.Mailer;
import com.sogilis.sogimailer.mail.Profile;

import java.util.List;

import javax.inject.Inject;

public class MailerService extends IntentService
		implements Mailer.Listener, ProfileDude.MultipleListener {
	private static final String TAG = "SOGIMAILER_SERVICE";

	static final String ACTION = "com.sogilis.sogimailer.ACTION_SEND";

	private static final String OPT_SUBJECT = "MAILER_OPT_SUBJECT";
	private static final String OPT_PASSWORD = "MAILER_OPT_PASSWORD";
	private static final String OPT_BODY = "MAILER_OPT_BODY";
	private static final String OPT_RECIPIENTS = "MAILER_OPT_RECIPIENTS";

	private static final String RESULTMSG_KEY = "MAILER_RESULTMSG";
	private static final String RETCODE_KEY = "MAILER_RETCODE";

	@Inject Mailer mailer;
	@Inject
	ProfileDude profileDude;

	// XXX: Params are stored as attribute : WE SHOULD USE LOCK TO PREVENT ANOTHER REQUEST TO ...
	private String subject;
	private String body;
	private String recipients;
	private String password;

	public MailerService() {
		super("test-service");
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate");

		((SogiMailerApplication) getApplication()).getObjectGraph().inject(this);
	}

	/**
	 * When binding to the service, we return an interface to our messenger
	 * for sending messages to the service.
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "Handle new intent");

		Log.d(TAG, "sub, body, reci, pawd : " + subject + " " + body + " " + recipients + " " + password);
		subject = intent.getStringExtra(OPT_SUBJECT);
		body = intent.getStringExtra(OPT_BODY);
		recipients = intent.getStringExtra(OPT_RECIPIENTS);
		password = intent.getStringExtra(OPT_PASSWORD);

		if (!checkKeys(subject, body, recipients)) {
			onMailFailure("Bad argument");
			return;
		}

		profileDude.getAll(this);

	}

	@Override
	public void onProfilesUpdate(List<Profile> profiles) {
		if (profiles.size() != 1) {
			onMailFailure("Unable to retrieve mailer profile");
			return;
		}

		mailer.updateProfile(profiles.get(0));
		Log.d(TAG, "Sending mail to mailer !");
		mailer.sendSimpleMail(this, recipients, subject, body);
	}

	@Override
	public void onMailFailure(String err) {
		broadcastResult(-12, err);
	}

	@Override
	public void onMailSuccess() {
		broadcastResult(Activity.RESULT_OK, "Message sent successfully");
	}

	private void broadcastResult(int returnCode, String message) {
		Intent itt = new Intent(ACTION);
		itt.putExtra(RESULTMSG_KEY, message);
		itt.putExtra(RETCODE_KEY, returnCode);

		sendBroadcast(itt);
	}

	public static boolean checkKeys(String subject, String body, String recipients) {
		if (subject == null) return false;
		if (body == null) return false;
		if (recipients == null) return false;

		if (subject.isEmpty()) return false;
		if (recipients.isEmpty()) return false;
		return true;
	}
}
