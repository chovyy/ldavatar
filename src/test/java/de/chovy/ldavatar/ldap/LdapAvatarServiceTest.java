package de.chovy.ldavatar.ldap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.chovy.ldavatar.avatar.PlaceholderFactory;
import de.chovy.ldavatar.placeholders.Placeholders;
import de.chovy.ldavatar.placeholders.StaticImage;

class LdapAvatarServiceTest {
	
	public static final byte[] TEST_BYTES = "test".getBytes();
	
	private LdapAvatarService service;
	
	@Mock
	private LdapUserRepository repo;
	
	private PlaceholderFactory pf;
	
	@Mock
	private LdapUser user;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		pf = Placeholders.AWESOME.getFactory();
		service = new LdapAvatarService(repo, null);
	}

	@Test
	void by_username_found_with_avatar() {
		when(repo.findByUsername("test")).thenReturn(Optional.of(user));
		when(user.getAvatar()).thenReturn(Optional.of(TEST_BYTES));
		
		byte[] result = service.getAvatarByUsername("test", pf);
		assertNotNull(result);
		assertEquals(TEST_BYTES, result);
	}
	
	@Test
	void by_username_found_without_avatar() {
		when(repo.findByUsername("test")).thenReturn(Optional.of(user));
		when(user.getAvatar()).thenReturn(Optional.empty());
		
		byte[] result = service.getAvatarByUsername("test", pf);
		assertNotNull(result);
		assertTrue(StaticImage.AWESOME_USER.is(result));
	}
	
	@Test
	void admin_found_without_avatar() {
		when(repo.findByUsername("admin")).thenReturn(Optional.of(user));
		when(user.getAvatar()).thenReturn(Optional.empty());
		when(user.isAdmin()).thenReturn(true);
		
		byte[] result = service.getAvatarByUsername("admin", pf);
		assertNotNull(result);
		assertTrue(StaticImage.AWESOME_ADMIN.is(result));
	}
	
	@Test
	void by_username_not_found() {
		when(repo.findByUsername("test")).thenReturn(Optional.empty());
		
		byte[] result = service.getAvatarByUsername("test", pf);
		assertNotNull(result);
		assertTrue(StaticImage.AWESOME_USER.is(result));
	}
	
	@Test
	void by_email_found_with_avatar() {
		when(repo.findByEmail("test")).thenReturn(Optional.of(user));
		when(user.getAvatar()).thenReturn(Optional.of(TEST_BYTES));
		
		byte[] result = service.getAvatarByEmail("test", pf);
		assertNotNull(result);
		assertEquals(TEST_BYTES, result);
	}
	
	@Test
	void by_email_found_without_avatar() {
		when(repo.findByEmail("test")).thenReturn(Optional.of(user));
		when(user.getAvatar()).thenReturn(Optional.empty());
		
		byte[] result = service.getAvatarByEmail("test", pf);
		assertNotNull(result);
		assertTrue(StaticImage.AWESOME_USER.is(result));
	}
	
	@Test
	void by_email_not_found() {
		when(repo.findByEmail("test")).thenReturn(Optional.empty());
		
		byte[] result = service.getAvatarByEmail("test", pf);
		assertNotNull(result);
		assertTrue(StaticImage.AWESOME_USER.is(result));
	}
}
