package com.sogilis.sogimailer.dude;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.sogilis.sogimailer.SogiMailerApplication;
import com.sogilis.sogimailer.mail.Basic;
import com.sogilis.sogimailer.mail.NoSuchProfileException;
import com.sogilis.sogimailer.mail.Profile;

public class ProfileDude {
	private static final String TAG = "SOGIMAILER_PROFILEDUDE";


	public Profile getBasic() throws NoSuchProfileException {
		SharedPreferences settings = SogiMailerApplication.ctx.getSharedPreferences(
			Constants.SHARED_PREFS_NAME,
			Context.MODE_PRIVATE);

		String sender = settings.getString(Constants.SENDER_KEY, "");
		String host = settings.getString(Constants.HOST_KEY, "");
		String password = settings.getString(Constants.PASSWORD_KEY, "");

		if (sender.isEmpty() || host.isEmpty() || password.isEmpty()) {
			Log.d(TAG, "Unable to recover sender or host or password from shared prefs");
			throw new NoSuchProfileException("No basic profile information, please configure one");
		}

		Profile basic = new Basic(host, sender, password);
		return basic;
	}

	public void saveBasic(String sender, String host, String password) {
		SharedPreferences settings = SogiMailerApplication.ctx.getSharedPreferences(
				Constants.SHARED_PREFS_NAME,
				Context.MODE_PRIVATE);

		SharedPreferences.Editor ed = settings.edit();

		ed.putString(Constants.SENDER_KEY, sender);
		ed.putString(Constants.HOST_KEY, host);
		ed.putString(Constants.PASSWORD_KEY, password);

		ed.commit();
	}

}
