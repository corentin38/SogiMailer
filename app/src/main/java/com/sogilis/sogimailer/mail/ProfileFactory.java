package com.sogilis.sogimailer.mail;

public class ProfileFactory {

	public static Profile simplest() {
		return new ExtendedProfile(
				"smtp.gmail.com",                   // host
				-1,                                 // SQLite id
				"auguste.bidon@gmail.com",          // sender
				"THIS WON'T WORK",                  // senderPassword
				"true",                             // smtpAuth
				"465",                              // smtpPort
				"false",                            // smtpQuitWait
				"javax.net.ssl.SSLSocketFactory",   // smtpFactoryClass
				"false",                            // smtpFallback
				"465",                              // smtpSocketFactoryPort
				"smtp",                             // transportProtocol
				"default"                           // profile name
		);
	}

	public static Profile gmail(String profileName, String host, String senderPassword, String sender) {
		return new ExtendedProfile(
				host,                               // host
				-1,                                 // SQLite id
				sender,                             // sender
				senderPassword,                     // senderPassword
				"true",                             // smtpAuth
				"465",                              // smtpPort
				"false",                            // smtpQuitWait
				"javax.net.ssl.SSLSocketFactory",   // smtpFactoryClass
				"false",                            // smtpFallback
				"465",                              // smtpSocketFactoryPort
				"smtp",                             // transportProtocol
				profileName                         // profile name
		);
	}

}
