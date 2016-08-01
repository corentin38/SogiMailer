package com.sogilis.sogimailer;

import com.sogilis.sogimailer.svc.MailerService;

import org.junit.Test;

import static org.junit.Assert.*;

public class MiscTest {

	@Test
	public void checkKeysNull() {
		String nulv = null;
		String okay = "ok";
		
		assertEquals(false, MailerService.checkKeys(nulv, okay, okay, okay));
		assertEquals(false, MailerService.checkKeys(okay, nulv, okay, okay));
		assertEquals(false, MailerService.checkKeys(okay, okay, nulv, okay));
		assertEquals(false, MailerService.checkKeys(okay, okay, okay, nulv));
	}

	@Test
	public void checkKeysEmpty() {
		String empt = "";
		String okay = "ok";

		assertEquals(false, MailerService.checkKeys(empt, okay, okay, okay));
		// Empy body is allowed
		assertEquals(false, MailerService.checkKeys(okay, okay, empt, okay));
		assertEquals(false, MailerService.checkKeys(okay, okay, okay, empt));
	}


}
