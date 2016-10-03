package com.sogilis.sogimailer.db;

import android.provider.BaseColumns;

public class Contract {

	public Contract() {}

	public static abstract class Profile implements BaseColumns {
		public static final String TABLE_NAME                               = "profiles";
		public static final String COLUMN_NAME_NAME                         = "name";
		public static final String COLUMN_NAME_SENDER                       = "sender";
		public static final String COLUMN_NAME_SENDER_PASSWORD              = "sender_password";
		public static final String COLUMN_NAME_TRANSPORT_PROTOCOL           = "transport_protocol";
		public static final String COLUMN_NAME_HOST                         = "host";
		public static final String COLUMN_NAME_SMTP_QUIT_WAIT               = "smtp_quit_wait";
		public static final String COLUMN_NAME_SMTP_AUTH                    = "smtp_auth";
		public static final String COLUMN_NAME_SMTP_PORT                    = "smtp_port";
		public static final String COLUMN_NAME_SMTP_SOCKET_FACTORY_PORT     = "smtp_socket_factory_port";
		public static final String COLUMN_NAME_SMTP_SOCKET_FACTORY_CLASS    = "smtp_socket_factory_class";
		public static final String COLUMN_NAME_SMTP_SOCKET_FACTORY_FALLBACK = "smtp_socket_factory_fallback";
		public static final String COLUMN_NAME_IS_DEFAULT                   = "is_default";
	}

}
