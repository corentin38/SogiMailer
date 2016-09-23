package com.sogilis.sogimailer.ui;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.sogilis.sogimailer.R;
import com.sogilis.sogimailer.SogiMailerApplication;
import com.sogilis.sogimailer.dude.ProfileDude;
import com.sogilis.sogimailer.mail.Mailer;
import com.sogilis.sogimailer.mail.Profile;

import java.util.ArrayList;
import java.util.List;

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

		List<Profile> profileList = profileDude.all();
		Profile[] profiles = profileList.toArray(new Profile[profileList.size()]);

        // Setting ViewPager for each Tabs
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager, profiles);

        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

	}

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager, Profile[] profiles) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(HomeFragment.newInstance(profiles), "Profiles");
        adapter.addFragment(new DocumentationFragment(), "Documentation");
        viewPager.setAdapter(adapter);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.main_testmail:
				Log.d(TAG, "starting test");
				TestMailDialog dlg = TestMailDialog.newInstance();
				dlg.show(getSupportFragmentManager(), TestMailDialog.TESTMAIL_DIALOG_KEY);
				return true;
		}

		return super.onOptionsItemSelected(item);
	}

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
