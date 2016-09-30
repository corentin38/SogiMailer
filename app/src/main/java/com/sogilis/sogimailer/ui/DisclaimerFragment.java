package com.sogilis.sogimailer.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sogilis.sogimailer.R;

public class DisclaimerFragment extends Fragment {

	private static final String TAG = "SOGIMAILER_DISCFRAG";
	public static final String FRAGMENT_KEY = "com.sogilis.sogimailer.ui.DisclaimerFragment";

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView");
		return inflater.inflate(R.layout.fragment_disclaimer, container, false);
	}

}
