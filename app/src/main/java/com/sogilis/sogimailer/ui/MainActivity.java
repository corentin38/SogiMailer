package com.sogilis.sogimailer.ui;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sogilis.sogimailer.R;
import com.sogilis.sogimailer.SogiMailerApplication;
import com.sogilis.sogimailer.dude.ProfileDude;
import com.sogilis.sogimailer.mail.Default;
import com.sogilis.sogimailer.mail.Mailer;
import com.sogilis.sogimailer.mail.NoSuchProfileException;
import com.sogilis.sogimailer.mail.Profile;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity {

	private static final String TAG = "SOGIMAILER_ACTIVITY";

	private static final String SOGIMAILER_ACTION = "com.sogilis.sogimailer.ACTION_SEND";

	@Inject
    public BroadcastReceiver testReceiver;

	@Inject
	public ProfileDude profileDude;

	@Inject
	public Mailer mailer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpToolbar(R.string.app_name, true);
		Log.d(TAG, "onCreate");

		((SogiMailerApplication) getApplication()).getObjectGraph().inject(this);

		if (savedInstanceState == null) {
			goHome();
		}
	}

	@Override
	protected void onResume() {
        IntentFilter filter = new IntentFilter(SOGIMAILER_ACTION);
        registerReceiver(testReceiver, filter);
		super.onResume();
	}

	@Override
	protected void onPause() {
		unregisterReceiver(testReceiver);
		super.onPause();
	}

	public void setUpToolbar(int titleId, boolean displayHomeAsUp) {
		Toolbar toolbar = (Toolbar) findViewById(R.id.global_toolbar);
		setSupportActionBar(toolbar);

		ActionBar actionBar = getSupportActionBar();
		if (actionBar == null) {
			return;
		}

		actionBar.setDisplayHomeAsUpEnabled(displayHomeAsUp);
		actionBar.setDisplayShowTitleEnabled(false);
		TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
		mTitle.setText(getString(titleId));
	}

	public void testEmail(View view) {
		Log.d(TAG, "testEmail");
		TestMailDialog dlg = TestMailDialog.newInstance();
		dlg.show(getSupportFragmentManager(), TestMailDialog.TESTMAIL_DIALOG_KEY);
	}

	public void doc(View view) {
		Log.d(TAG, "doc");
	}

	public void edit(View view) {
		Log.d(TAG, "edit");
		goEdit();
	}

	public void saveEdit(View view) {
		Log.d(TAG, "saveEdit");
		goHome();
	}

	public void cancelEdit(View view) {
		Log.d(TAG, "saveEdit");
		goHome();
	}

	public void discardDisclaimer(View view) {
		Log.d(TAG, "discardDisclaimer");
		removeDisclaimer();
	}

	private void goHome() {
		Log.d(TAG, "goHome");
		FragmentManager fm = getFragmentManager();

		Fragment edit = fm.findFragmentByTag(EditFragment.FRAGMENT_KEY);
		Fragment home = fm.findFragmentByTag(HomeFragment.FRAGMENT_KEY);

		if (home == null) {
			try {
				Profile profile = profileDude.getBasic();
				home = HomeFragment.newInstance(profile);
			} catch (NoSuchProfileException e) {
				Log.d(TAG, "No profile defined while getting back home");
				home = HomeFragment.newInstance(new Default());
			}
		}

		removeDisclaimer();

		FragmentTransaction tx = fm.beginTransaction();
		if (edit != null) {
			tx.remove(edit);
		}
		tx.replace(R.id.main_fragment_holder, home, HomeFragment.FRAGMENT_KEY);
		tx.addToBackStack(null);
		tx.commit();
	}

	private void goEdit() {
		Log.d(TAG, "goEdit");
		FragmentManager fm = getFragmentManager();

		Fragment edit = fm.findFragmentByTag(EditFragment.FRAGMENT_KEY);
		Fragment home = fm.findFragmentByTag(HomeFragment.FRAGMENT_KEY);

		if (edit == null) {
			Profile profile = null;
			try {
				profile = profileDude.getBasic();
			} catch (NoSuchProfileException e) {
				Log.d(TAG, "No profile defined while getting back home");
				showDisclaimer();
			}
			edit = EditFragment.newInstance(0, profile);
		}

		FragmentTransaction tx = fm.beginTransaction();
		if (home != null) {
			tx.remove(home);
		}
		tx.replace(R.id.main_fragment_holder, edit, EditFragment.FRAGMENT_KEY);
		tx.addToBackStack(null);
		tx.commit();
	}

	private void showDisclaimer() {
		Log.d(TAG, "showDisclaimer");
		FragmentManager fm = getFragmentManager();
		Fragment disc = fm.findFragmentByTag(DisclaimerFragment.FRAGMENT_KEY);
		if (disc == null) {
			Log.d(TAG, "disclaimer is null");
			disc = new DisclaimerFragment();
		}

		FragmentTransaction tx = fm.beginTransaction();
		tx.replace(R.id.disclaimer_fragment_holder, disc, DisclaimerFragment.FRAGMENT_KEY);
		tx.addToBackStack(null);
		tx.commit();
	}

	private void removeDisclaimer() {
		Log.d(TAG, "removeDisclaimer");
		FragmentManager fm = getFragmentManager();
		Fragment disc = fm.findFragmentByTag(DisclaimerFragment.FRAGMENT_KEY);
		if (disc == null) {
			Log.d(TAG, "disclaimer is null !!");
			return;
		}

		Log.d(TAG, "disclaimer is not null");
		FragmentTransaction tx = fm.beginTransaction();
		tx.remove(disc);
		tx.addToBackStack(null);
		tx.commit();
	}

}
