package com.sogilis.sogimailer;

import android.util.Log;

import com.sogilis.sogimailer.profile.Profile;

import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailer extends Authenticator {

	private static final String TAG = "SOGIMAILER_MAILER";

	private Properties props;
	private Profile profile;

	static {
		Security.addProvider(new JSSEProvider());
	}

	public interface Listener {
		void onMailSuccess();
		void onMailFailure(String message);
	}

	/**
	 * Default constructor
	 * Constants to be found in email.xml resource file
	 */
	public Mailer(Profile profile) {
		Log.d(TAG, "Building new mailer");
		this.profile = profile;
		getPropertiesFromProfile(profile);
	}

	private void getPropertiesFromProfile(Profile profile) {
		this.props = new Properties();
		props.setProperty("mail.transport.protocol", profile.transportProtocol());
		props.setProperty("mail.host", profile.host());
		props.setProperty("mail.smtp.quitwait", profile.smtpQuitwait());
		props.put("mail.smtp.auth", profile.smtpAuth());
		props.put("mail.smtp.port", profile.smtpPort());
		props.put("mail.smtp.socketFactory.port", profile.smtpSocketFactoryPort());
		props.put("mail.smtp.socketFactory.class", profile.smtpSocketFactoryClass());
		props.put("mail.smtp.socketFactory.fallback", profile.smtpSocketFactoryFallback());
	}

	public void updateProfile(Profile profile) {
		this.profile = profile;
		getPropertiesFromProfile(profile);
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(profile.sender(), profile.senderPassword());
	}

	public boolean sendSimpleMail(Listener listener, String recipients, String subject, String body) {
		MimeMessage message;
		Log.d(TAG, "Sending simple mail to " + recipients);

		try {
			Session session = Session.getInstance(props, this);
			message = new MimeMessage(session);

			// Assuming user login and email address are the same
			message.setFrom(new InternetAddress(profile.sender()));
			if (recipients.indexOf(',') > 0) {
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
			} else {
				message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
			}

			message.setSubject(subject);
			message.setSentDate(new Date());
			message.setContent(body, "text/plain");

		} catch (Exception e) {
			String err = "Unable to form the mail content from given parameters. Cause: " + e.getMessage();

			Log.w(TAG, err);
			e.printStackTrace();
			notifyListeners(listener, false, err);
			return false;
		}

		try {
			Transport.send(message);
		} catch (Exception e) {
			String err = "Unable to send email automatically. Cause: " + e.getMessage();

			Log.w(TAG, err);
			e.printStackTrace();
			notifyListeners(listener, false, err);
			return false;
		}

		notifyListeners(listener, true, null);
		return true;
	}

	private void notifyListeners(Listener listener, boolean ret, String message) {
		if (listener == null) {
			return;
		}

		if (ret) {
			Log.d(TAG, "calling onMailSuccess");
			listener.onMailSuccess();
		} else {
			Log.d(TAG, "calling onMailFailurer with " + message);
			listener.onMailFailure(message);
		}
	}

}
