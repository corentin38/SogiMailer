package com.sogilis.sogimailer.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sogilis.sogimailer.R;

public class HomeFragment extends Fragment {

	private static final String TAG = "SOGIMAILER_HOMEFRAG";
	public static final String FRAGMENT_KEY = "com.sogilis.sogimailer.ui.HomeFragment";

	public static HomeFragment newInstance() {
		return new HomeFragment();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView");
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		return view;
	}
}
