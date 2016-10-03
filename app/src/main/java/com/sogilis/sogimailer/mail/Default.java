package com.sogilis.sogimailer.mail;

import android.os.Parcel;
import android.os.Parcelable;

public class Default implements Profile {

	public static final String DEFAULT_PROFILE_NAME = "default";

	private String password;

	public Default(String password) {
		this.password = password;
	}

	public static final Parcelable.Creator<Default> CREATOR = new Parcelable.Creator<Default>() {
		@Override
		public Default createFromParcel(Parcel source) {
			return new Default(source);
		}

		@Override
		public Default[] newArray(int size) {
			return new Default[size];
		}
	};

	public Default(Parcel in) {
		this.password = in.readString();
	}

	public Default() {
		this.password = "THIS WON'T WORK !!!";
	}

	@Override
	public long id() {
		return -1;
	}

	@Override
	public String name() {
		return DEFAULT_PROFILE_NAME;
	}

	@Override
	public String host() {
		return "smtp.gmail.com";
	}

	@Override
	public String sender() {
		return "auguste.bidon@gmail.com";
	}

	@Override
	public String senderPassword() {
		return password;
	}

	@Override
	public String transportProtocol() {
		return "smtp";
	}

	@Override
	public String smtpQuitwait() {
		return "false";
	}

	@Override
	public String smtpAuth() {
		return "true";
	}

	@Override
	public String smtpPort() {
		return "465";
	}

	@Override
	public String smtpSocketFactoryPort() {
		return "465";
	}

	@Override
	public String smtpSocketFactoryClass() {
		return "javax.net.ssl.SSLSocketFactory";
	}

	@Override
	public String smtpSocketFactoryFallback() {
		return "false";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.password);
	}
}
