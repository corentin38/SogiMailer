package com.sogilis.sogimailer.svc;

import java.util.Date;

public class DefaultMailRequest implements MailRequest {

	private ProfileMailRequest mRequest;

	public DefaultMailRequest(Date id, MailRequest.Listener listener) {
		mRequest = new ProfileMailRequest(id, listener);
		mRequest.setProfileName("default");
	}

	@Override
	public void setMessage(String recipients, String subject, String body) {
		mRequest.setMessage(recipients, subject, body);
	}

	@Override
	public void send() {
		mRequest.send();
	}

}
