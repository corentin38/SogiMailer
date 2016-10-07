package com.sogilis.sogimailer.svc;

import java.util.Date;

public interface MailRequest {

	interface Listener {
		void onRequestSuccess(Date id);
		void onRequestFailure(Date id, String error);
	}

	void send();

}
