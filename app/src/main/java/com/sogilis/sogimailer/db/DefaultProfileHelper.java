package com.sogilis.sogimailer.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sogilis.sogimailer.SogiMailerApplication;
import com.sogilis.sogimailer.mail.Profile;

public class DefaultProfileHelper {

	private static final String TAG = "DEFAULT_PROFILE_DB";

	private static final String[] FULL_DEFAULT_PROFILE_PROJECTION = {
			Contract.DefaultProfile._ID,
			Contract.DefaultProfile.COLUMNE_NAME_DEFAULT_PROFILE_ID
	};

	private static SQLiteDatabase db() {
		DbHelper helper = new DbHelper(SogiMailerApplication.ctx);
		return helper.getWritableDatabase();
	}

	public static long defaultProfileId() {
		Cursor c = db().query(
				Contract.DefaultProfile.TABLE_NAME,
				FULL_DEFAULT_PROFILE_PROJECTION,
				null, null,
				null, null,
				null);

		if (!c.moveToFirst()) {
			return -1;
		}

		if (c.getCount() > 1) {
			return -2;
		}

		return c.getLong(c.getColumnIndex(Contract.DefaultProfile.COLUMNE_NAME_DEFAULT_PROFILE_ID));
	}

	public static void setDefaultProfile(long id) {
		// Hardcore drop
		db().delete(
				Contract.DefaultProfile.TABLE_NAME,
				null, null
		);

		// Insert single
		ContentValues value = new ContentValues();
		value.put(Contract.DefaultProfile.COLUMNE_NAME_DEFAULT_PROFILE_ID, id);

		db().insert(
				Contract.DefaultProfile.TABLE_NAME,
				null,
				value
		);
	}

}
