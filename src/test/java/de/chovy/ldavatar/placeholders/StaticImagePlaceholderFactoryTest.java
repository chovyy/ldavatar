package de.chovy.ldavatar.placeholders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.chovy.ldavatar.avatar.User;

class StaticImagePlaceholderFactoryTest {
	
	private StaticImagePlaceholderFactory service;
	
	@Mock
	private User user;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		service = new StaticImagePlaceholderFactory(StaticImage.AWESOME_USER, StaticImage.AWESOME_ADMIN);
	}

	@Test
	void no_user() {
		byte[] image = service.getPlaceholderAvatar();
		assertNotNull(image);
		assertTrue(StaticImage.AWESOME_USER.is(image));
	}
	
	@Test
	void empty_user() {
		byte[] image = service.getPlaceholderAvatar(Optional.empty());
		assertNotNull(image);
		assertTrue(StaticImage.AWESOME_USER.is(image));
	}
	
	@Test
	void normal_user() {
		when(user.isAdmin()).thenReturn(false);
		byte[] image = service.getPlaceholderAvatar(Optional.of(user));
		assertNotNull(image);
		assertTrue(StaticImage.AWESOME_USER.is(image));
	}
	
	@Test
	void admin_user() {
		when(user.isAdmin()).thenReturn(true);
		byte[] image = service.getPlaceholderAvatar(Optional.of(user));
		assertNotNull(image);
		assertTrue(StaticImage.AWESOME_ADMIN.is(image));
	}
}
