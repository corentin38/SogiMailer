package com.sogilis.sogimailer.dude;

import android.os.AsyncTask;
import android.util.Log;

import com.sogilis.sogimailer.db.ProfileHelper;
import com.sogilis.sogimailer.mail.Default;
import com.sogilis.sogimailer.mail.Profile;

import java.util.List;

public class ProfileDudeImpl implements ProfileDude {

	private static final String TAG = "SOGIMAILER_PROFILEDUDE";

	@Override
	public void delete(long id) {
		new AsyncTask<Long, Void, Void>() {

			@Override
			protected Void doInBackground(Long... ids) {
				ProfileHelper.delete(ids[0]);
				return null;
			}

			@Override
			protected void onPostExecute(Void v) {
			}

		}.execute(id);
	}

	@Override
	public void save(Profile profile) {
		new AsyncTask<Profile, Void, Void>() {

			@Override
			protected Void doInBackground(Profile... profiles) {
				ProfileHelper.save(profiles[0]);
				return null;
			}

			@Override
			protected void onPostExecute(Void v) {
			}

		}.execute(profile);
	}


	@Override
	public void findByName(ProfileDude.SingleListener listener, String name) {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public void findById(ProfileDude.SingleListener listener, long id) {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public void getAll(final ProfileDude.MultipleListener listener) {
		new AsyncTask<Void, Void, List<Profile>>() {

			@Override
			protected List<Profile> doInBackground(Void... v) {
				return ProfileHelper.all();
			}

			@Override
			protected void onPostExecute(List<Profile> profiles) {
				if (profiles.size() == 0) {
					populate(listener);
					return;
				}
				listener.onProfilesUpdate(profiles);
			}

		}.execute();
	}

	@Override
	public void update(Profile profile) {
		new AsyncTask<Profile, Void, Void>() {

			@Override
			protected Void doInBackground(Profile... profiles) {
				ProfileHelper.update(profiles[0]);
				return null;
			}

			@Override
			protected void onPostExecute(Void v) {
			}

		}.execute(profile);
	}

	private void populate(final ProfileDude.MultipleListener listener) {
		save(new Default());
		try {
			Thread.sleep(100);
		} catch(InterruptedException e) {
			Log.d(TAG, "populate: Our dirty technique failed !!");
		}
		getAll(listener);
	}

}
