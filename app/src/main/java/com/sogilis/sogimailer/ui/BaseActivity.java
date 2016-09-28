package com.sogilis.sogimailer.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.sogilis.sogimailer.R;
import com.sogilis.sogimailer.SogiMailerApplication;

public class BaseActivity extends AppCompatActivity {

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((SogiMailerApplication) getApplication()).getObjectGraph().inject(this);
	}

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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
