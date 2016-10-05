package com.sogilis.sogimailer.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.sogilis.sogimailer.R;
import com.sogilis.sogimailer.mail.Profile;

public class EditFragment extends Fragment {

	private static final String TAG = "SOGIMAILER_EDITFRAG";
	public static final String FRAGMENT_KEY = "com.sogilis.sogimailer.ui.EditFragment";

	private static final String PROFILE_BUNDLE_KEY = "profile_bundle_key";

	private EditText hostET;
	private EditText senderET;
	private EditText passwordET;

	private Profile mProfile;

	public static EditFragment newInstance(Profile profile) {
		EditFragment frag = new EditFragment();

		Bundle bun = new Bundle();
		bun.putParcelable(PROFILE_BUNDLE_KEY, profile);

		frag.setArguments(bun);
		return frag;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView");

		View view = inflater.inflate(R.layout.fragment_edit, container, false);
		hostET = (EditText) view.findViewById(R.id.edit_host);
		senderET = (EditText) view.findViewById(R.id.edit_user);
		passwordET = (EditText) view.findViewById(R.id.edit_password);

		if (savedInstanceState == null) {
			mProfile = getArguments().getParcelable(PROFILE_BUNDLE_KEY);
		} else {
			mProfile = savedInstanceState.getParcelable(PROFILE_BUNDLE_KEY);
		}

		initEditFields(mProfile);

		return view;
	}

	private void initEditFields(Profile profile) {
		if (profile == null) return;

		hostET.setText(profile.host());
		senderET.setText(profile.sender());
		passwordET.setText(profile.senderPassword());

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable(PROFILE_BUNDLE_KEY, mProfile);
	}

	public long getProfileId() {
		return mProfile.id();
	}

	public String getHostEntry() {
		return hostET.getText().toString();
	}

	public String getSenderEntry() {
		return senderET.getText().toString();
	}

	public String getPasswordEntry() {
		return passwordET.getText().toString();
	}

}
