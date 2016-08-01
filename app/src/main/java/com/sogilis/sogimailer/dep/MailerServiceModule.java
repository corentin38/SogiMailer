package com.sogilis.sogimailer.dep;

import com.sogilis.sogimailer.mail.Default;
import com.sogilis.sogimailer.mail.Mailer;
import com.sogilis.sogimailer.svc.MailerService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module (
		injects = MailerService.class
)
public class MailerServiceModule {

	@Provides
	@Singleton
	public Mailer providesMailer() {
		return new Mailer(new Default());
	}

}
