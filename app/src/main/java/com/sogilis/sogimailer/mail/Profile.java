package com.sogilis.sogimailer.mail;

public interface Profile {

	String sender();
	String senderPassword();
	String transportProtocol();
	String host();
	String smtpQuitwait();
	String smtpAuth();
	String smtpPort();
	String smtpSocketFactoryPort();
	String smtpSocketFactoryClass();
	String smtpSocketFactoryFallback();

}
