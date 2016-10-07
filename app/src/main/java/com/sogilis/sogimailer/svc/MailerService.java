package com.sogilis.sogimailer.svc;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.sogilis.sogimailer.SogiMailerApplication;

import java.util.Date;

public class MailerService extends IntentService
		implements MailRequest.Listener {
	private static final String TAG = "SOGIMAILER_SERVICE";

	static final String ACTION = "com.sogilis.sogimailer.ACTION_SEND";

	private static final String OPT_PROFILE = "MAILER_OPT_PROFILE";
	private static final String OPT_SUBJECT = "MAILER_OPT_SUBJECT";
	private static final String OPT_PASSWORD = "MAILER_OPT_PASSWORD";
	private static final String OPT_BODY = "MAILER_OPT_BODY";
	private static final String OPT_RECIPIENTS = "MAILER_OPT_RECIPIENTS";

	private static final String RESULTMSG_KEY = "MAILER_RESULTMSG";
	private static final String RETCODE_KEY = "MAILER_RETCODE";

	public MailerService() {
		super("test-service");
	}

	/**
	 * When binding to the service, we return an interface to our messenger
	 * for sending messages to the service.
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "Handle new intent");

		Log.d(TAG, "Creating mail request, " +
				"profile=["   + intent.getStringExtra(OPT_PROFILE)    + "], " +
				"recipient=[" + intent.getStringExtra(OPT_RECIPIENTS) + "], " +
				"subject=["   + intent.getStringExtra(OPT_SUBJECT)    + "], " +
				"body=["      + intent.getStringExtra(OPT_BODY)       + "], "
		);

		ProfileMailRequest request = new ProfileMailRequest(new Date(), this);
		request.setProfileName(intent.getStringExtra(OPT_PROFILE));

		request.setMessage(
				intent.getStringExtra(OPT_RECIPIENTS),
				intent.getStringExtra(OPT_SUBJECT),
				intent.getStringExtra(OPT_BODY));

		request.send();
	}

	@Override
	public void onRequestFailure(Date id, String error) {
		broadcastResult(-100, "[Request " + id.toString() + "] " + error);
	}

	@Override
	public void onRequestSuccess(Date id) {
		broadcastResult(Activity.RESULT_OK, "Message sent successfully (Req " + id.toString() + ")");
	}

	private void broadcastResult(int returnCode, String message) {
		Intent itt = new Intent(ACTION);
		itt.putExtra(RESULTMSG_KEY, message);
		itt.putExtra(RETCODE_KEY, returnCode);

		sendBroadcast(itt);
	}

}
