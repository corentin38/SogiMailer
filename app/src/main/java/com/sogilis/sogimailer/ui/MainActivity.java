package com.sogilis.sogimailer.ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sogilis.sogimailer.R;
import com.sogilis.sogimailer.SogiMailerApplication;
import com.sogilis.sogimailer.dude.ProfileDude;
import com.sogilis.sogimailer.mail.Default;
import com.sogilis.sogimailer.mail.Mailer;
import com.sogilis.sogimailer.mail.NoSuchProfileException;
import com.sogilis.sogimailer.mail.Profile;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity {

	private static final String TAG = "SOGIMAILER_ACTIVITY";

	private static final String SOGIMAILER_ACTION = "com.sogilis.sogimailer.ACTION_SEND";

/*
	private static final String OPT_RECIPIENTS = "MAILER_OPT_RECIPIENTS";
	private static final String OPT_SUBJECT = "MAILER_OPT_SUBJECT";
	private static final String OPT_BODY = "MAILER_OPT_BODY";
	private static final String OPT_PASSWORD = "MAILER_OPT_PASSWORD";
*/

	@Inject
    public BroadcastReceiver testReceiver;

	@Inject
	public ProfileDude profileDude;

	@Inject
	public Mailer mailer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpToolbar(R.string.app_name, true);
		Log.d(TAG, "onCreate");

		((SogiMailerApplication) getApplication()).getObjectGraph().inject(this);

		if (savedInstanceState == null) {
			initFragments();
		}
	}

	@Override
	protected void onResume() {
        IntentFilter filter = new IntentFilter(SOGIMAILER_ACTION);
        registerReceiver(testReceiver, filter);
		super.onResume();
	}

	@Override
	protected void onPause() {
		unregisterReceiver(testReceiver);
		super.onPause();
	}

	public void setUpToolbar(int titleId, boolean displayHomeAsUp) {
		Toolbar toolbar = (Toolbar) findViewById(R.id.global_toolbar);
		setSupportActionBar(toolbar);

		ActionBar actionBar = getSupportActionBar();
		if (actionBar == null) {
			return;
		}

		actionBar.setDisplayHomeAsUpEnabled(displayHomeAsUp);
		actionBar.setDisplayShowTitleEnabled(false);
		TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
		mTitle.setText(getString(titleId));
	}

	private void initFragments() {
		Profile saved;
		try {
			saved = profileDude.getBasic();
		} catch (NoSuchProfileException e) {
			Log.d(TAG, "No saved profile, defaulting to shitty one !");
			saved = new Default();
		}

		Fragment homeFragment = HomeFragment.newInstance(saved);
		FragmentTransaction transaction = getFragmentManager().beginTransaction();

		transaction.replace(R.id.main_fragment_holder, homeFragment, HomeFragment.FRAGMENT_KEY);
		transaction.addToBackStack(null);

		transaction.commit();
	}


	public void testEmail(View view) {
		Log.d(TAG, "testEmail");
		TestMailDialog dlg = TestMailDialog.newInstance();
		dlg.show(getSupportFragmentManager(), TestMailDialog.TESTMAIL_DIALOG_KEY);
	}

	public void doc(View view) {
		Log.d(TAG, "doc");
	}

	public void edit(View view) {
		Log.d(TAG, "edit");
	}

	public void saveEdit(View view) {
		Log.d(TAG, "saveEdit");
	}

	public void cancelEdit(View view) {
		Log.d(TAG, "saveEdit");
	}


/*	public void send() {
		Log.d(TAG, "Launching intent for service SogiMailer");
		EditText recipientsET  = (EditText) findViewById(R.id.recipients);
		EditText subjectET  = (EditText) findViewById(R.id.subject);
		EditText bodyET  = (EditText) findViewById(R.id.body);
		EditText passwordET  = (EditText) findViewById(R.id.password);

		String recipients = recipientsET.getText().toString();
		String subject = subjectET.getText().toString();
		String body = bodyET.getText().toString();
		String password = passwordET.getText().toString();

		Intent itt = new Intent(SOGIMAILER_ACTION);
		itt.setPackage("com.sogilis.sogimailer");

		itt.putExtra(OPT_RECIPIENTS, recipients);
		itt.putExtra(OPT_SUBJECT, subject);
		itt.putExtra(OPT_BODY, body);
		itt.putExtra(OPT_PASSWORD, password);

		startService(itt);
	}*/

}
