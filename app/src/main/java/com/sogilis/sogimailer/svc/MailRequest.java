package com.sogilis.sogimailer.svc;

import android.app.Application;

import java.util.Date;

public interface MailRequest {

	interface Listener {
		void onRequestSuccess(Date id);
		void onRequestFailure(Date id, String error);
		Application getApplication();
	}

	void send();
	void setMessage(String recipients, String subject, String body);

}
