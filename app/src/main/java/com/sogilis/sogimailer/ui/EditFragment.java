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

	private static final String PROFILEID_BUNDLE_KEY = "profile_id_bundle_key";
	private static final String PROFILE_BUNDLE_KEY = "profile_bundle_key";

	private EditText hostET;
	private EditText senderET;
	private EditText passwordET;

	public static EditFragment newInstance(int profileId, Profile profile) {
		EditFragment frag = new EditFragment();

		Bundle bun = new Bundle();
		bun.putInt(PROFILEID_BUNDLE_KEY, profileId);
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

		if (savedInstanceState == null) initEditFields();
		return view;
	}

	private void initEditFields() {
		Bundle bun = getArguments();
		Profile profile = bun.getParcelable(PROFILE_BUNDLE_KEY);

		if (profile != null) {
			hostET.setText(profile.host());
			senderET.setText(profile.sender());
			passwordET.setText(profile.senderPassword());
		}
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
