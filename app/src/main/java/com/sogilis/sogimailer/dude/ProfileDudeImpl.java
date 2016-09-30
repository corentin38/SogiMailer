package com.sogilis.sogimailer.dude;

import android.os.AsyncTask;

import com.sogilis.sogimailer.db.ProfileHelper;
import com.sogilis.sogimailer.mail.Profile;

import java.util.List;

public class ProfileDudeImpl implements ProfileDude {

	private static final String TAG = "SOGIMAILER_PROFILEDUDE";

	@Override
	public void delete(long id) {
		throw new RuntimeException("Not yet implemented");
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

}
