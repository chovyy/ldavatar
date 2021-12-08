package de.chovy.ldavatar.placeholders;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


class StaticImageTest {

	@Test
	void dummy() {
		byte[] image = StaticImage.DUMMY.get();
		assertTrue(StaticImage.DUMMY.is(image));
	}
	
	@Test
	void awesome_user() {
		byte[] image = StaticImage.AWESOME_USER.get();
		assertTrue(StaticImage.AWESOME_USER.is(image));
	}
	
	@Test
	void awesome_admin() {
		byte[] image = StaticImage.AWESOME_ADMIN.get();
		assertTrue(StaticImage.AWESOME_ADMIN.is(image));
	}
	
	@Test
	void blank() {
		byte[] image = StaticImage.BLANK.get();
		assertTrue(StaticImage.BLANK.is(image));
	}
	
	@Test
	void outlook() {
		byte[] image = StaticImage.OUTLOOK.get();
		assertTrue(StaticImage.OUTLOOK.is(image));
	}
}
