package com.sogilis.sogimailer.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.sogilis.sogimailer.R;
import com.sogilis.sogimailer.SogiMailerApplication;
import com.sogilis.sogimailer.dude.Constants;

public class TestMailDialog extends DialogFragment {

	private static final String TAG = "SOGIMAILER_TESTEMAILDLG";

	public static final String TESTMAIL_DIALOG_KEY = "com.sogilis.sogimailer.ui.TestMailDialog";

	private static final String SOGIMAILER_ACTION = "com.sogilis.sogimailer.ACTION_SEND";

	private static final String OPT_RECIPIENTS = "MAILER_OPT_RECIPIENTS";
	private static final String OPT_SUBJECT = "MAILER_OPT_SUBJECT";
	private static final String OPT_BODY = "MAILER_OPT_BODY";
	private static final String OPT_PROFILE = "MAILER_OPT_PROFILE";

	private EditText recipientET;
	private EditText subjectET;
	private EditText bodyET;

	public static TestMailDialog newInstance() {
		return new TestMailDialog();
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Log.d(TAG, "onCreateDialog");

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();

		View view = inflater.inflate(R.layout.dialog_testmail, null);
		recipientET = (EditText) view.findViewById(R.id.testmail_recipient);
		subjectET = (EditText) view.findViewById(R.id.testmail_subject);
		bodyET = (EditText) view.findViewById(R.id.testmail_body);

		builder.setView(view)
			.setTitle("Test mail sender")
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					Log.d(TAG, "Cancel !");
				}
			})
			.setPositiveButton("Send", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Log.d(TAG, "We should send test mail !");
					sendTestMail();
				}
			});

		AlertDialog dlg = builder.create();
		dlg.setCanceledOnTouchOutside(true);

		if (savedInstanceState == null) suggestPreviousEntries();

		return dlg;
	}

	private void sendTestMail() {
		remindEntries();

		Intent itt = new Intent(SOGIMAILER_ACTION);
		itt.setPackage("com.sogilis.sogimailer");

		itt.putExtra(OPT_PROFILE, "default");
		itt.putExtra(OPT_RECIPIENTS, recipientET.getText().toString());
		itt.putExtra(OPT_SUBJECT, subjectET.getText().toString());
		itt.putExtra(OPT_BODY, bodyET.getText().toString());

		SogiMailerApplication.ctx.startService(itt);
	}

	private void suggestPreviousEntries() {
		SharedPreferences settings = SogiMailerApplication.ctx.getSharedPreferences(
			Constants.SHARED_PREFS_NAME,
			Context.MODE_PRIVATE);

		String recipient = settings.getString(Constants.TESTMAIL_RECIPIENT_PREVIOUS, SogiMailerApplication.ctx.getString(R.string.default_email_suggestion));
		String subject = settings.getString(Constants.TESTMAIL_SUBJECT_PREVIOUS, SogiMailerApplication.ctx.getString(R.string.default_subject_suggestion));
		String body = settings.getString(Constants.TESTMAIL_BODY_PREVIOUS, SogiMailerApplication.ctx.getString(R.string.default_body_suggestion));

		recipientET.setText(recipient);
		subjectET.setText(subject);
		bodyET.setText(body);
	}

	private void remindEntries() {
		SharedPreferences settings = SogiMailerApplication.ctx.getSharedPreferences(
				Constants.SHARED_PREFS_NAME,
				Context.MODE_PRIVATE);

		SharedPreferences.Editor ed = settings.edit();

		ed.putString(Constants.TESTMAIL_RECIPIENT_PREVIOUS, recipientET.getText().toString());
		ed.putString(Constants.TESTMAIL_SUBJECT_PREVIOUS, subjectET.getText().toString());
		ed.putString(Constants.TESTMAIL_BODY_PREVIOUS, bodyET.getText().toString());

		ed.commit();
	}

}
