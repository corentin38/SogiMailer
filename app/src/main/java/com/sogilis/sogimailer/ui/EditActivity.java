package com.sogilis.sogimailer.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

import com.sogilis.sogimailer.R;
import com.sogilis.sogimailer.dude.ProfileDude;
import com.sogilis.sogimailer.mail.Profile;

import javax.inject.Inject;

public class EditActivity extends BaseActivity {

	private static final String TAG = "SOGIMAILER--EDIT";

	public static final String PROFILE_KEY = "EDIT_PROFILE_KEY";

	@Inject ProfileDude profileDude;

	private EditFragment mEdit;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		setUpToolbar();

		Profile profile = getIntent().getParcelableExtra(PROFILE_KEY);
		mEdit = EditFragment.newInstance(profile);

		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction().replace(R.id.fragment_holder, mEdit).commit();
	}

	public void cancelEdit(View view) {
		finish();
	}

	public void saveEdit(View view) {
		profileDude.saveBasic(
				mEdit.getSenderEntry(),
				mEdit.getHostEntry(),
				mEdit.getPasswordEntry());
		finish();
	}

}
