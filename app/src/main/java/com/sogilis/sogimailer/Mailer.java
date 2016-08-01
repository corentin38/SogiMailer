package com.sogilis.sogimailer;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mailer extends Authenticator {

	private static final String TAG = "SOGIMAIL_MAILER";

	private Context context;
	private Listener listener;

	private String mailhost;
	private String user;
	private String password;
	private Properties props;
	private Multipart multipart;
	private String attachmentPath;
	private String attachmentFilename;

	static {
		Security.addProvider(new JSSEProvider());
	}

	public interface Listener {
		public void onMailSuccess();
		public void onMailFailure();
	}

	/**
	 * Default constructor
	 * Constants to be found in email.xml resource file
	 */
	public Mailer() {
		//this.context = context;

/*		this.user = context.getString(R.string.account_login);
		this.password = context.getString(R.string.account_password);

		this.mailhost = context.getString(R.string.smtp_host);*/
		this.multipart = new MimeMultipart();

		this.props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		//props.setProperty("mail.host", mailhost);
		props.setProperty("mail.smtp.quitwait", "false");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(user, password);
	}

	public void addAttachment(String filename, String content) throws IOException {
		this.attachmentFilename = filename;
		this.attachmentPath = createTmpFile(filename, content);
	}

	private String createTmpFile(String filename, String content) throws IOException {
		if (context == null) {
			throw new IOException("Null context provided, unable to call getExternalCacheDir()");
		}

		File tmpDir = context.getExternalCacheDir();
		if (tmpDir == null) {
			throw new IOException("getExternalCacheDir() returned null. External storage seems unavailable");
		}

		File tmpFile = new File(tmpDir, filename);
		FileWriter fw = new FileWriter(tmpFile, false);
		fw.append(content);
		fw.close();

		Log.d(TAG, "HL7 tmp file written at: " + tmpFile.getAbsolutePath());

		return tmpFile.getAbsolutePath();
	}

	private void deleteTmpFile() {
		if (attachmentPath == null) {
			return;
		}

		File tmpFile = new File(attachmentPath);
		if (tmpFile.exists()) {
			tmpFile.delete();
		}
	}

	public synchronized void send(String subject, String body, String recipients) {
		MimeMessage message = null;

		// Setter mailhost dans props !!!!

		try {
			Session session = Session.getInstance(props, this);
			message = new MimeMessage(session);

			// Assuming user login and email address are the same
			message.setFrom(new InternetAddress(user));
			if (recipients.indexOf(',') > 0) {
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
			} else {
				message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
			}

			message.setSubject(subject);
			message.setSentDate(new Date());

			BodyPart mbp = new MimeBodyPart();
			mbp.setText(body);
			multipart.addBodyPart(mbp);

			if (attachmentPath != null) {
				BodyPart attachmentPart = new MimeBodyPart();
				DataSource source = new FileDataSource(attachmentPath);
				attachmentPart.setDataHandler(new DataHandler(source));
				attachmentPart.setFileName(attachmentFilename);
				multipart.addBodyPart(attachmentPart);
			}

			message.setContent(multipart);
		} catch (Exception e) {
			Log.w(TAG, "Unable to form the mail content from given parameters. Cause: " + e.getMessage());
			listener.onMailFailure();
			return;
		}

		new AsyncSender().execute(message);
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setMailhost(String mailhost) {
		this.mailhost = mailhost;
	}

	private class AsyncSender extends AsyncTask<MimeMessage, Void, Boolean> {

		@Override
		protected Boolean doInBackground(MimeMessage... messages) {
			try {
				Transport.send(messages[0]);
				deleteTmpFile();
				return true;
			} catch (Exception e) {
				Log.w(TAG, "Unable to send email automatically. Cause: " + e.getMessage());
				return false;
			}
		}

		@Override
		protected void onPostExecute(Boolean success) {
			if (success) {
				listener.onMailSuccess();
			} else {
				listener.onMailFailure();
			}
		}

	}
}
