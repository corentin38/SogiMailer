package com.sogilis.sogimailer.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sogilis.sogimailer.R;

public class EditActivity extends BaseActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		setUpToolbar();
	}

}
