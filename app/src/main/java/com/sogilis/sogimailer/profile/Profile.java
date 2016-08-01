package com.sogilis.sogimailer.profile;

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
