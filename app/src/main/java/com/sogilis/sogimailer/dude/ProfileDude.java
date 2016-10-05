package com.sogilis.sogimailer.dude;

import com.sogilis.sogimailer.mail.Profile;

import java.util.List;

public interface ProfileDude {

	// Create
	void save(Profile profile);

	// Read
	interface SingleListener {
		void onProfileUpdate(Profile profile);
		void notFound();
		void tooMany();
	}
	void findByName(SingleListener listener, String name);
	void findById(SingleListener listener, long id);
	void getDefaultProfile(SingleListener listener);

	interface MultipleListener {
		void onProfilesUpdate(List<Profile> profiles);
	}
	void getAll(MultipleListener listener);

	// Update
	void update(Profile profile);
	void setDefaultProfile(long id);

	// Delete
	void delete(long id);

}
