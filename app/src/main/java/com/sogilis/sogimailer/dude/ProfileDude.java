package com.sogilis.sogimailer.dude;

import com.sogilis.sogimailer.mail.Profile;

import java.util.List;

public interface ProfileDude {

	// Crud
	void save(Profile profile);

	// cRud
	interface SingleListener {
		void onProfileUpdate(Profile profile);
		void notFound();
		void tooMany();
	}
	void findByName(SingleListener listener, String name);
	void findById(SingleListener listener, long id);

	interface MultipleListener {
		void onProfilesUpdate(List<Profile> profiles);
	}
	void getAll(MultipleListener listener);

	// crUd
	void update(Profile profile);

	// cruD
	void delete(long id);

}
