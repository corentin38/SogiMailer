package com.sogilis.sogimailer.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.sogilis.sogimailer.R;
import com.sogilis.sogimailer.dude.ProfileDude;
import com.sogilis.sogimailer.mail.Profile;
import com.sogilis.sogimailer.mail.ProfileFactory;

import javax.inject.Inject;

public class AddActivity extends BaseActivity implements ProfileDude.SaveListener {

	private static final String TAG = "SOGIMAILER_ADD";

	private EditText mNameET;
	private EditText mHostET;
	private EditText mSenderET;
	private EditText mSenderPasswordET;
	private CheckBox mDefaultCheckbox;

	@Inject ProfileDude profileDude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		setUpToolbar(R.string.app_name, true, false);

		findViews();
	}

	private void findViews() {
		mNameET = (EditText) findViewById(R.id.add_name);
		mHostET = (EditText) findViewById(R.id.add_host);
		mSenderET = (EditText) findViewById(R.id.add_sender);
		mSenderPasswordET = (EditText) findViewById(R.id.add_sender_password);
		mDefaultCheckbox = (CheckBox) findViewById(R.id.add_default_checkbox);
	}

	public void addProfile(View view) {
		String name = mNameET.getText().toString();
		String host = mHostET.getText().toString();
		String sender = mSenderET.getText().toString();
		String senderPassword = mSenderPasswordET.getText().toString();

		Profile newProfile = ProfileFactory.gmail(name, host, senderPassword, sender);

		Log.d(TAG, "addProfile: name=[" + name + "], host=[" + host + "], sender=[" + sender + "], password=[" + senderPassword + "]");
		profileDude.save(this, newProfile);
		finish();
	}

	public void cancelAdd(View view) {
		finish();
	}

	@Override
	public void onProfileSaved(long id) {
		boolean setAsDefault = mDefaultCheckbox.isChecked();
		if (setAsDefault) profileDude.setDefaultProfile(id);
	}

	@Override
	public void onProfileSaveFailure() {
		Toast.makeText(this, "Unable to save new profile", Toast.LENGTH_LONG).show();
	}
}
