package com.sogilis.sogimailer;

import org.junit.Test;

import static org.junit.Assert.*;

public class MiscTest {

	@Test
	public void checkKeysNull() {
		String nulv = null;
		String okay = "ok";
		
		assertEquals(false, SogiMailerService.checkKeys(nulv, okay, okay, okay));
		assertEquals(false, SogiMailerService.checkKeys(okay, nulv, okay, okay));
		assertEquals(false, SogiMailerService.checkKeys(okay, okay, nulv, okay));
		assertEquals(false, SogiMailerService.checkKeys(okay, okay, okay, nulv));
	}

	@Test
	public void checkKeysEmpty() {
		String empt = "";
		String okay = "ok";

		assertEquals(false, SogiMailerService.checkKeys(empt, okay, okay, okay));
		// Empy body is allowed
		assertEquals(false, SogiMailerService.checkKeys(okay, okay, empt, okay));
		assertEquals(false, SogiMailerService.checkKeys(okay, okay, okay, empt));
	}


}
