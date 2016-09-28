package com.sogilis.sogimailer.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.sogilis.sogimailer.R;

public class BaseActivity extends AppCompatActivity {

	public void setUpToolbar(int titleId, boolean displayHomeAsUp, boolean main) {
		Toolbar toolbar = (Toolbar) findViewById(R.id.global_toolbar);
		setSupportActionBar(toolbar);

		ActionBar actionBar = getSupportActionBar();
		if (actionBar == null) {
			return;
		}

		actionBar.setDisplayHomeAsUpEnabled(displayHomeAsUp);
		actionBar.setDisplayShowTitleEnabled(false);

		if (main) {
			actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
		}

		TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
		mTitle.setText(getString(titleId));
	}

	public void setUpToolbar() {
		setUpToolbar(R.string.app_name, true, false);
	}

}
