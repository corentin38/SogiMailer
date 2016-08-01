package com.sogilis.sogimailer.dep;

import dagger.Module;

@Module (
		includes = {
				MailerServiceModule.class,
				MainActivityModule.class
		}
)
public class SogiMailerModule {}
