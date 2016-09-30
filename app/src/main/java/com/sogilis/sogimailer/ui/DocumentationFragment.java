package com.sogilis.sogimailer.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.sogilis.sogimailer.R;

public class DocumentationFragment extends Fragment {

	private static final String TAG = "SOGIMAILER_DOCFRAG";
	public static final String FRAGMENT_KEY = "com.sogilis.sogimailer.ui.DocumentationFragment";

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView");
		View view = inflater.inflate(R.layout.fragment_documentation, container, false);

		initWebview(view);

		return view;
	}

	private void initWebview(View view) {
		WebView wview = (WebView) view.findViewById(R.id.doc_webview);
		wview.getSettings().setJavaScriptEnabled(true);
		wview.loadUrl("file:///android_asset/documentation.html");
	}
}
