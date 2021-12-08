package de.chovy.ldavatar.placeholders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.chovy.ldavatar.avatar.User;

class PlaceholdersTest {
	
	@Mock
	private User user, admin;
	
	private Optional<User> userOpt, adminOpt;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		when(user.isAdmin()).thenReturn(false);
		when(admin.isAdmin()).thenReturn(true);
		userOpt = Optional.of(user);
		adminOpt = Optional.of(admin);
	}

	@Test
	void awesome_user() {
		assertTrue(StaticImage.AWESOME_USER.is(Placeholders.AWESOME.getFactory().getPlaceholderAvatar(userOpt)));
	}
	
	@Test
	void awesome_admin() {
		assertTrue(StaticImage.AWESOME_ADMIN.is(Placeholders.AWESOME.getFactory().getPlaceholderAvatar(adminOpt)));
	}
	
	@Test
	void blank_user() {
		assertTrue(StaticImage.BLANK.is(Placeholders.BLANK.getFactory().getPlaceholderAvatar(userOpt)));
	}
	
	@Test
	void blank_admin() {
		assertTrue(StaticImage.BLANK.is(Placeholders.BLANK.getFactory().getPlaceholderAvatar(adminOpt)));
	}
	
	@Test
	void outlook_user() {
		assertTrue(StaticImage.OUTLOOK.is(Placeholders.OUTLOOK.getFactory().getPlaceholderAvatar(userOpt)));
	}
	
	@Test
	void outlook_admin() {
		assertTrue(StaticImage.OUTLOOK.is(Placeholders.OUTLOOK.getFactory().getPlaceholderAvatar(adminOpt)));
	}
	
	@Test
	void none_user() {
		assertThrows(NoAvatarFoundException.class, () -> Placeholders.NONE.getFactory().getPlaceholderAvatar(userOpt));
	}
	
	@Test
	void none_admin() {
		assertThrows(NoAvatarFoundException.class, () -> Placeholders.NONE.getFactory().getPlaceholderAvatar(userOpt));
	}
	
	@Test
	void none_nothing() {
		assertThrows(NoAvatarFoundException.class, () -> Placeholders.NONE.getFactory().getPlaceholderAvatar());
	}
	
	@Test
	void joined_names() {
		assertEquals("awesome, outlook, blank, none", Placeholders.joinNames());
	}
}
