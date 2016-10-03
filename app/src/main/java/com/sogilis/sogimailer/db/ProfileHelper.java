package com.sogilis.sogimailer.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sogilis.sogimailer.SogiMailerApplication;
import com.sogilis.sogimailer.mail.ExtendedProfile;
import com.sogilis.sogimailer.mail.Profile;

import java.util.ArrayList;
import java.util.List;

public class ProfileHelper {

	private static final String TAG = "PROFILE_DB";

	private static SQLiteDatabase db() {
		DbHelper helper = new DbHelper(SogiMailerApplication.ctx);
		return helper.getWritableDatabase();
	}

	public static void save(Profile profile) {
		ContentValues values = new ContentValues();
		values.put(Contract.Profile.COLUMN_NAME_SENDER, profile.sender());
		values.put(Contract.Profile.COLUMN_NAME_SENDER_PASSWORD, profile.senderPassword());
		values.put(Contract.Profile.COLUMN_NAME_TRANSPORT_PROTOCOL, profile.transportProtocol());
		values.put(Contract.Profile.COLUMN_NAME_HOST, profile.host());
		values.put(Contract.Profile.COLUMN_NAME_SMTP_QUIT_WAIT, profile.smtpQuitwait());
		values.put(Contract.Profile.COLUMN_NAME_SMTP_AUTH, profile.smtpAuth());
		values.put(Contract.Profile.COLUMN_NAME_SMTP_PORT, profile.smtpPort());
		values.put(Contract.Profile.COLUMN_NAME_SMTP_SOCKET_FACTORY_PORT, profile.smtpSocketFactoryPort());
		values.put(Contract.Profile.COLUMN_NAME_SMTP_SOCKET_FACTORY_CLASS, profile.smtpSocketFactoryClass());
		values.put(Contract.Profile.COLUMN_NAME_SMTP_SOCKET_FACTORY_FALLBACK, profile.smtpSocketFactoryFallback());

		db().insert(Contract.Profile.TABLE_NAME, null, values);
	}

	public static List<Profile> all() {
		String[] projection = {
				Contract.Profile._ID,
				Contract.Profile.COLUMN_NAME_NAME,
				Contract.Profile.COLUMN_NAME_SENDER,
				Contract.Profile.COLUMN_NAME_SENDER_PASSWORD,
				Contract.Profile.COLUMN_NAME_TRANSPORT_PROTOCOL,
				Contract.Profile.COLUMN_NAME_HOST,
				Contract.Profile.COLUMN_NAME_SMTP_QUIT_WAIT,
				Contract.Profile.COLUMN_NAME_SMTP_AUTH,
				Contract.Profile.COLUMN_NAME_SMTP_PORT,
				Contract.Profile.COLUMN_NAME_SMTP_SOCKET_FACTORY_PORT,
				Contract.Profile.COLUMN_NAME_SMTP_SOCKET_FACTORY_CLASS,
				Contract.Profile.COLUMN_NAME_SMTP_SOCKET_FACTORY_FALLBACK,
		};

		Cursor c = db().query(
				Contract.Profile.TABLE_NAME,
				projection,
				null, null,
				null, null,
				null);

		List<Profile> profiles = new ArrayList<>();

		if (!c.moveToFirst()) {
			return profiles;
		}

		do {

			long sqlId                       = c.getLong(c.getColumnIndex(Contract.Profile._ID));
			String name                      = c.getString(c.getColumnIndex(Contract.Profile.COLUMN_NAME_NAME));
			String sender                    = c.getString(c.getColumnIndex(Contract.Profile.COLUMN_NAME_SENDER));
			String senderPassword            = c.getString(c.getColumnIndex(Contract.Profile.COLUMN_NAME_SENDER_PASSWORD));
			String transportProtocol         = c.getString(c.getColumnIndex(Contract.Profile.COLUMN_NAME_TRANSPORT_PROTOCOL));
			String host                      = c.getString(c.getColumnIndex(Contract.Profile.COLUMN_NAME_HOST));
			String smtpQuitWait              = c.getString(c.getColumnIndex(Contract.Profile.COLUMN_NAME_SMTP_QUIT_WAIT));
			String smtpAuth                  = c.getString(c.getColumnIndex(Contract.Profile.COLUMN_NAME_SMTP_AUTH));
			String smtpPort                  = c.getString(c.getColumnIndex(Contract.Profile.COLUMN_NAME_SMTP_PORT));
			String smtpSocketFactoryPort     = c.getString(c.getColumnIndex(Contract.Profile.COLUMN_NAME_SMTP_SOCKET_FACTORY_PORT));
			String smtpSocketFactoryClass    = c.getString(c.getColumnIndex(Contract.Profile.COLUMN_NAME_SMTP_SOCKET_FACTORY_CLASS));
			String smtpSocketFactoryFallback = c.getString(c.getColumnIndex(Contract.Profile.COLUMN_NAME_SMTP_SOCKET_FACTORY_FALLBACK));

			profiles.add(new ExtendedProfile(host, sqlId, sender, senderPassword, smtpAuth,
					smtpPort, smtpQuitWait, smtpSocketFactoryClass, smtpSocketFactoryFallback,
					smtpSocketFactoryPort, transportProtocol, name));

		} while (c.moveToNext());

		return profiles;
	}

	public static void update(Profile profile) {
		ContentValues values = new ContentValues();
		values.put(Contract.Profile.COLUMN_NAME_NAME, profile.name());
		values.put(Contract.Profile.COLUMN_NAME_SENDER, profile.sender());
		values.put(Contract.Profile.COLUMN_NAME_SENDER_PASSWORD, profile.senderPassword());
		values.put(Contract.Profile.COLUMN_NAME_TRANSPORT_PROTOCOL, profile.transportProtocol());
		values.put(Contract.Profile.COLUMN_NAME_HOST, profile.host());
		values.put(Contract.Profile.COLUMN_NAME_SMTP_QUIT_WAIT, profile.smtpQuitwait());
		values.put(Contract.Profile.COLUMN_NAME_SMTP_AUTH, profile.smtpAuth());
		values.put(Contract.Profile.COLUMN_NAME_SMTP_PORT, profile.smtpPort());
		values.put(Contract.Profile.COLUMN_NAME_SMTP_SOCKET_FACTORY_PORT, profile.smtpSocketFactoryPort());
		values.put(Contract.Profile.COLUMN_NAME_SMTP_SOCKET_FACTORY_CLASS, profile.smtpSocketFactoryClass());
		values.put(Contract.Profile.COLUMN_NAME_SMTP_SOCKET_FACTORY_FALLBACK, profile.smtpSocketFactoryFallback());

		String selection = Contract.Profile._ID + " LIKE ?";
		String[] selectionArgs = { String.valueOf(profile.id()) };

		db().update(
				Contract.Profile.TABLE_NAME,
				values,
				selection,
				selectionArgs);
	}

	public static void delete(long profileId) {
		String selection = Contract.Profile._ID + " LIKE ?";
		String[] selectionArgs = { String.valueOf(profileId) };
		db().delete(Contract.Profile.TABLE_NAME, selection, selectionArgs);
	}

	public static Profile findById(long id) {
		String[] projection = {
				Contract.Profile._ID,
				Contract.Profile.COLUMN_NAME_NAME,
				Contract.Profile.COLUMN_NAME_SENDER,
				Contract.Profile.COLUMN_NAME_SENDER_PASSWORD,
				Contract.Profile.COLUMN_NAME_TRANSPORT_PROTOCOL,
				Contract.Profile.COLUMN_NAME_HOST,
				Contract.Profile.COLUMN_NAME_SMTP_QUIT_WAIT,
				Contract.Profile.COLUMN_NAME_SMTP_AUTH,
				Contract.Profile.COLUMN_NAME_SMTP_PORT,
				Contract.Profile.COLUMN_NAME_SMTP_SOCKET_FACTORY_PORT,
				Contract.Profile.COLUMN_NAME_SMTP_SOCKET_FACTORY_CLASS,
				Contract.Profile.COLUMN_NAME_SMTP_SOCKET_FACTORY_FALLBACK,
		};

		String selection = Contract.Profile._ID + " = ?";
		String[] selectionArgs = { String.valueOf(id) };

		Cursor c = db().query(
				Contract.Profile.TABLE_NAME,
				projection,
				selection, selectionArgs,
				null, null,
				null);

		if (!c.moveToFirst()) {
			return null;
		}

		long sqlId                       = c.getLong(c.getColumnIndex(Contract.Profile._ID));
		String name                      = c.getString(c.getColumnIndex(Contract.Profile.COLUMN_NAME_NAME));
		String sender                    = c.getString(c.getColumnIndex(Contract.Profile.COLUMN_NAME_SENDER));
		String senderPassword            = c.getString(c.getColumnIndex(Contract.Profile.COLUMN_NAME_SENDER_PASSWORD));
		String transportProtocol         = c.getString(c.getColumnIndex(Contract.Profile.COLUMN_NAME_TRANSPORT_PROTOCOL));
		String host                      = c.getString(c.getColumnIndex(Contract.Profile.COLUMN_NAME_HOST));
		String smtpQuitWait              = c.getString(c.getColumnIndex(Contract.Profile.COLUMN_NAME_SMTP_QUIT_WAIT));
		String smtpAuth                  = c.getString(c.getColumnIndex(Contract.Profile.COLUMN_NAME_SMTP_AUTH));
		String smtpPort                  = c.getString(c.getColumnIndex(Contract.Profile.COLUMN_NAME_SMTP_PORT));
		String smtpSocketFactoryPort     = c.getString(c.getColumnIndex(Contract.Profile.COLUMN_NAME_SMTP_SOCKET_FACTORY_PORT));
		String smtpSocketFactoryClass    = c.getString(c.getColumnIndex(Contract.Profile.COLUMN_NAME_SMTP_SOCKET_FACTORY_CLASS));
		String smtpSocketFactoryFallback = c.getString(c.getColumnIndex(Contract.Profile.COLUMN_NAME_SMTP_SOCKET_FACTORY_FALLBACK));

		Profile profile = new ExtendedProfile(host, sqlId, sender, senderPassword, smtpAuth,
				smtpPort, smtpQuitWait, smtpSocketFactoryClass, smtpSocketFactoryFallback,
				smtpSocketFactoryPort, transportProtocol, name);

		return profile;
	}

}
