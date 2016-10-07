package com.sogilis.sogimailer.svc;

import com.sogilis.sogimailer.dude.ProfileDude;
import com.sogilis.sogimailer.mail.Mailer;
import com.sogilis.sogimailer.mail.Profile;

import java.util.Date;

import javax.inject.Inject;

public class ProfileMailRequest implements MailRequest, Mailer.Listener {

	@Inject ProfileDude profileDude;
	@Inject Mailer mailer;

	private Date id;
	private Listener mListener;

	private String mProfile;
	private String mRecipients;
	private String mSubject;
	private String mBody;

	public ProfileMailRequest(Date id, Listener listener) {
		this.id = id;
		this.mListener = listener;
	}

	public void setProfileName(String profile) {
		this.mProfile = profile;
	}

	public void setMessage(String recipients, String subject, String body) {
		this.mRecipients = recipients;
		this.mSubject = subject;
		this.mBody = body;
	}

	@Override
	public void send() {
		if (!checkKeys(mSubject, mBody, mRecipients)) {
			mListener.onRequestFailure(id, "One or many empty parameter provided");
		}

		profileDude.findByName(new ProfileDude.SingleListener() {
			@Override
			public void onProfileUpdate(Profile profile) {
				mailer.updateProfile(profile);
				mailer.sendSimpleMail(ProfileMailRequest.this, mRecipients, mSubject, mBody);
			}

			@Override
			public void notFound() {
				mListener.onRequestFailure(id, "Profile not found for name : " + mProfile);
			}

			@Override
			public void tooMany() {
				mListener.onRequestFailure(id, "Several profiles found for name : " + mProfile);
			}
		}, mProfile);

	}

	@Override
	public void onMailSuccess() {
		mListener.onRequestSuccess(id);
	}

	@Override
	public void onMailFailure(String message) {
		mListener.onRequestFailure(id, message);
	}

	private static boolean checkKeys(String subject, String body, String recipients) {
		if (subject == null) return false;
		if (body == null) return false;
		if (recipients == null) return false;

		if (subject.isEmpty()) return false;
		if (recipients.isEmpty()) return false;
		return true;
	}
}
