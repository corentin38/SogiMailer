package com.sogilis.sogimailer.mail;

import android.os.Parcel;
import android.os.Parcelable;

public class Basic implements Profile {

	private long mId;
	private String mName;
	private String mSender;
	private String mHost;
	private String mPassword;

	public static final Parcelable.Creator<Basic> CREATOR = new Parcelable.Creator<Basic>() {
		@Override
		public Basic createFromParcel(Parcel source) {
			return new Basic(source);
		}

		@Override
		public Basic[] newArray(int size) {
			return new Basic[size];
		}
	};

	public Basic(long mId, String mName, String mHost, String mPassword, String mSender) {
		this.mId = mId;
		this.mName = mName;
		this.mHost = mHost;
		this.mPassword = mPassword;
		this.mSender = mSender;
	}

	public Basic(String mName, String mHost, String mPassword, String mSender) {
		this.mId = -1;
		this.mName = mName;
		this.mHost = mHost;
		this.mPassword = mPassword;
		this.mSender = mSender;
	}

	public Basic(Parcel in) {
		this.mId = in.readLong();
		this.mName = in.readString();
		this.mSender = in.readString();
		this.mHost = in.readString();
		this.mPassword = in.readString();
	}

	@Override
	public long id() {
		return mId;
	}

	@Override
	public String name() {
		return mName;
	}

	@Override
	public String host() {
		return mHost;
	}

	@Override
	public String sender() {
		return mSender;
	}

	@Override
	public String senderPassword() {
		return mPassword;
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
		dest.writeLong(this.mId);
		dest.writeString(this.mName);
		dest.writeString(this.mSender);
		dest.writeString(this.mHost);
		dest.writeString(this.mPassword);
	}
}
