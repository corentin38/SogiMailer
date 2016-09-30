package com.sogilis.sogimailer.ui;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;

import com.sogilis.sogimailer.R;
import com.sogilis.sogimailer.dude.ProfileDude;
import com.sogilis.sogimailer.mail.Profile;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class MainActivity extends BaseActivity implements HomeFragment.Listener {

	private static final String TAG = "SOGIMAILER_ACTIVITY";

	private static final String SOGIMAILER_ACTION = "com.sogilis.sogimailer.ACTION_SEND";

	@Inject BroadcastReceiver testReceiver;
	@Inject
	ProfileDude profileDude;

	private DrawerLayout mDrawer;
	private NavigationView mNavigationView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpToolbar(R.string.app_name, true, true);
		Log.d(TAG, "onCreate");

		mDrawer = (DrawerLayout) findViewById(R.id.drawer);
		mNavigationView = (NavigationView) findViewById(R.id.nav_view);
		initDrawer();

		// Setting ViewPager for each Tabs
		ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
		setupViewPager(viewPager);

		// Set Tabs inside Toolbar
		TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
		tabs.setupWithViewPager(viewPager);
	}

	// Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(HomeFragment.newInstance(), "Profiles");
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

    private void initDrawer() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawer.closeDrawers();

                switch (menuItem.getItemId()) {
                    case R.id.main_testmail:
	                    Log.d(TAG, "starting test");
	                    TestMailDialog dlg = TestMailDialog.newInstance();
	                    dlg.show(getSupportFragmentManager(), TestMailDialog.TESTMAIL_DIALOG_KEY);
	                    return true;
                    default:
                        Log.v(TAG, "Unknown menu item");
                }

                return true;
            }
        });
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				mDrawer.openDrawer(GravityCompat.START);
				return true;
		}

		return super.onOptionsItemSelected(item);
	}

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
	        mDrawer.closeDrawers();
            return;
        }

        super.onBackPressed();
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

	public void onEditButtonClicked(Profile profile) {
		Intent itt = new Intent(this, EditActivity.class);
		itt.putExtra(EditActivity.PROFILE_KEY, profile);
		startActivity(itt);
	}
}
