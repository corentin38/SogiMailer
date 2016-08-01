package com.sogilis.sogimailer.mail;

public class Default implements Profile {

	private String password;

	public Default(String password) {
		this.password = password;
	}

	public Default() {
		this.password = "THIS WON'T WORK !!!";
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
}
