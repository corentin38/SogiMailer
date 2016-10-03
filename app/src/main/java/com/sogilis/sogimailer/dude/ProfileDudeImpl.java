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
	public void delete(final long id) {
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... v) {
				ProfileHelper.delete(id);
				return null;
			}
		}.execute();
	}

	@Override
	public void save(final Profile profile) {
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... v) {
				ProfileHelper.save(profile);
				return null;
			}
		}.execute();
	}

	@Override
	public void findByName(final ProfileDude.SingleListener listener, final String name) {
		new AsyncTask<Void, Void, Profile>() {

			@Override
			protected Profile doInBackground(Void... v) {
				return ProfileHelper.findByName(name);
			}

			@Override
			protected void onPostExecute(Profile profile) {
				listener.onProfileUpdate(profile);
			}

		}.execute();
	}

	@Override
	public void findById(final ProfileDude.SingleListener listener, final long id) {
		new AsyncTask<Void, Void, Profile>() {

			@Override
			protected Profile doInBackground(Void... v) {
				return ProfileHelper.findById(id);
			}

			@Override
			protected void onPostExecute(Profile profile) {
				listener.onProfileUpdate(profile);
			}

		}.execute();
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
	public void update(final Profile profile) {
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... v) {
				ProfileHelper.update(profile);
				return null;
			}
		}.execute();
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
