package com.sogilis.sogimailer.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sogilis.sogimailer.R;
import com.sogilis.sogimailer.mail.Profile;

public class HomeFragment extends Fragment {

	private static final String TAG = "SOGIMAILER_HOMEFRAG";
	public static final String FRAGMENT_KEY = "com.sogilis.sogimailer.ui.HomeFragment";

	private static final String SENDER_BUNDLE_KEY = "sender_bundle_key";
	private static final String HOST_BUNDLE_KEY = "host_bundle_key";
	private static final String PASSWORD_BUNDLE_KEY = "password_bundle_key";

	public static HomeFragment newInstance(Profile profile) {
		HomeFragment frag = new HomeFragment();

		Bundle bun = new Bundle();
		bun.putString(SENDER_BUNDLE_KEY, profile.sender());
		bun.putString(HOST_BUNDLE_KEY, profile.host());
		bun.putString(PASSWORD_BUNDLE_KEY, profile.senderPassword());

		frag.setArguments(bun);
		return frag;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView");
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		initProfileBox(view);
		return view;
	}

	private void initProfileBox(View view) {
		Bundle bun = getArguments();
/*		String sender = bun.getString(SENDER_BUNDLE_KEY, "");
		String host = bun.getString(HOST_BUNDLE_KEY, "");
		String password = bun.getString(PASSWORD_BUNDLE_KEY, "");*/
		String sender = "C'est moi !";
		String host = "Le sacré host !";
		String password = "tototototototo";

		TextView senderTV = (TextView) view.findViewById(R.id.home_default_user);
		TextView hostTV = (TextView) view.findViewById(R.id.home_default_host);
		TextView passwordTV = (TextView) view.findViewById(R.id.home_default_mdp);

		senderTV.setText(sender);
		hostTV.setText(host);
		passwordTV.setText(password);
	}

}
