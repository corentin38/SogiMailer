package com.sogilis.sogimailer.mail;

public class Basic implements Profile {

	private String mSender;
	private String mHost;
	private String mPassword;

	public Basic(String mHost, String mPassword, String mSender) {
		this.mHost = mHost;
		this.mPassword = mPassword;
		this.mSender = mSender;
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
}
