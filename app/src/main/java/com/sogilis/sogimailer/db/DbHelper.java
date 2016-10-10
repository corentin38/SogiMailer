package com.sogilis.sogimailer.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

	private static final String TEXT_TYPE = " TEXT";
	private static final String INTEGER_TYPE = " INTEGER";
	private static final String COMMA_SEP = ",";

	private static final String SQL_CREATE_PROFILES =
			"CREATE TABLE " + Contract.Profile.TABLE_NAME + " (" +
					Contract.Profile.COLUMN_NAME_NAME                         + TEXT_TYPE + COMMA_SEP +
					Contract.Profile.COLUMN_NAME_SENDER                       + TEXT_TYPE + COMMA_SEP +
					Contract.Profile.COLUMN_NAME_SENDER_PASSWORD              + TEXT_TYPE + COMMA_SEP +
					Contract.Profile.COLUMN_NAME_TRANSPORT_PROTOCOL           + TEXT_TYPE + COMMA_SEP +
					Contract.Profile.COLUMN_NAME_HOST                         + TEXT_TYPE + COMMA_SEP +
					Contract.Profile.COLUMN_NAME_SMTP_QUIT_WAIT               + TEXT_TYPE + COMMA_SEP +
					Contract.Profile.COLUMN_NAME_SMTP_AUTH                    + TEXT_TYPE + COMMA_SEP +
					Contract.Profile.COLUMN_NAME_SMTP_PORT                    + TEXT_TYPE + COMMA_SEP +
					Contract.Profile.COLUMN_NAME_SMTP_SOCKET_FACTORY_PORT     + TEXT_TYPE + COMMA_SEP +
					Contract.Profile.COLUMN_NAME_SMTP_SOCKET_FACTORY_CLASS    + TEXT_TYPE + COMMA_SEP +
					Contract.Profile.COLUMN_NAME_SMTP_SOCKET_FACTORY_FALLBACK + TEXT_TYPE+ ")";

	private static final String SQL_DELETE_PROFILES =
			"DROP TABLE IF EXISTS " + Contract.Profile.TABLE_NAME;

	private static final String SQL_CREATE_DEFAULT_PROFILE =
			"CREATE TABLE " + Contract.DefaultProfile.TABLE_NAME + " (" +
					Contract.DefaultProfile.COLUMNE_NAME_DEFAULT_PROFILE_ID + INTEGER_TYPE + ")";

	private static final String SQL_DELETE_DEFAULT_PROFILE =
			"DROP TABLE IF EXISTS " + Contract.DefaultProfile.TABLE_NAME;

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "SogiMailer.db";

	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_PROFILES);
		db.execSQL(SQL_CREATE_DEFAULT_PROFILE);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// We should probably keep the user data ...
		db.execSQL(SQL_DELETE_PROFILES);
		db.execSQL(SQL_DELETE_DEFAULT_PROFILE);
		onCreate(db);
	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}

}
