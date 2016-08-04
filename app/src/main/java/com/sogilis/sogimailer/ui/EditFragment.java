package com.sogilis.sogimailer.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sogilis.sogimailer.R;
import com.sogilis.sogimailer.mail.Profile;

public class EditFragment extends Fragment {

	private static final String TAG = "SOGIMAILER_EDITFRAG";
	public static final String FRAGMENT_KEY = "com.sogilis.sogimailer.ui.EditFragment";

	private static final String PROFILEID_BUNDLE_KEY = "profile_id_bundle_key";
	private static final String PROFILE_BUNDLE_KEY = "profile_bundle_key";

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
		//initProfileBox(view);
		return view;
	}


}
