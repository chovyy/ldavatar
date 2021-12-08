package de.chovy.ldavatar.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.NoHandlerFoundException;

import de.chovy.ldavatar.avatar.AvatarService;
import de.chovy.ldavatar.ldap.LdapAvatarService;
import de.chovy.ldavatar.ldap.LdapUser;
import de.chovy.ldavatar.ldap.LdapUserRepository;
import de.chovy.ldavatar.placeholders.StaticImage;

class AvatarRestControllerTest {
	
	private static final String REMOVE_SUFFIX = "POST";
	
	public static final byte[] USERNAME_BYTES = "username".getBytes();
	public static final byte[] EMAIL_BYTES = "email".getBytes();
	
	@Mock
	private LdapUser user, emailUser, admin;
	@Mock
	private LdapUserRepository repo;

	private AvatarService avatarService;
	private AvatarRestController rest;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		when(user.getAvatar()).thenReturn(Optional.of(USERNAME_BYTES));
		when(user.isAdmin()).thenReturn(false);
		when(admin.getAvatar()).thenReturn(Optional.empty());
		when(admin.isAdmin()).thenReturn(true);
		when(emailUser.getAvatar()).thenReturn(Optional.of(EMAIL_BYTES));
		when(emailUser.isAdmin()).thenReturn(false);
		when(repo.findByUsername("user")).thenReturn(Optional.of(user));
		when(repo.findByUsername("nouser")).thenReturn(Optional.empty());
		when(repo.findByUsername("admin")).thenReturn(Optional.of(admin));
		when(repo.findByEmail("email@test.de")).thenReturn(Optional.of(emailUser));
		when(repo.findByEmail("no_email@test.de")).thenReturn(Optional.empty());
		
		avatarService = new LdapAvatarService(repo);
		rest = new AvatarRestController(REMOVE_SUFFIX, avatarService);
	}
	
	@Test
	void no_userParam() throws NoHandlerFoundException {
		ResponseEntity<byte[]> result = rest.getAvatar(Optional.empty(), Optional.empty());
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertTrue(StaticImage.AWESOME_USER.is(result.getBody()));
	}
	
	@Test
	void username_userFound() throws NoHandlerFoundException {
		ResponseEntity<byte[]> result = rest.getAvatar(Optional.of("USERPOST"), Optional.empty());
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(USERNAME_BYTES, result.getBody());
	}
	
	@Test
	void username_noUserFound() throws NoHandlerFoundException {
		ResponseEntity<byte[]> result = rest.getAvatar(Optional.of("noUserpost"), Optional.empty());
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertTrue(StaticImage.AWESOME_USER.is(result.getBody()));
	}
	
	@Test
	void username_adminFoundNoAvatar() throws NoHandlerFoundException {
		ResponseEntity<byte[]> result = rest.getAvatar(Optional.of("ADMIN"), Optional.empty());
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertTrue(StaticImage.AWESOME_ADMIN.is(result.getBody()));
	}
	
	@Test
	void email_userFound() throws NoHandlerFoundException {
		ResponseEntity<byte[]> result = rest.getAvatar(Optional.of("Email@test.dePOST"), Optional.empty());
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(EMAIL_BYTES, result.getBody());
	}
	
	@Test
	void email_noUserFound() throws NoHandlerFoundException {
		ResponseEntity<byte[]> result = rest.getAvatar(Optional.of("No_Email@test.de"), Optional.empty());
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertTrue(StaticImage.AWESOME_USER.is(result.getBody()));
	}
	
	@Test
	void noUserFound_awesomePlaceholder() throws NoHandlerFoundException {
		ResponseEntity<byte[]> result = rest.getAvatar(Optional.of("noUserpost"), Optional.of("awesome"));
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertTrue(StaticImage.AWESOME_USER.is(result.getBody()));
	}
	
	@Test
	void noUserFound_blankPlaceholder() throws NoHandlerFoundException {
		ResponseEntity<byte[]> result = rest.getAvatar(Optional.of("noUserpost"), Optional.of("blank"));
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertTrue(StaticImage.BLANK.is(result.getBody()));
	}
	
	@Test
	void noUserFound_outlookPlaceholder() throws NoHandlerFoundException {
		ResponseEntity<byte[]> result = rest.getAvatar(Optional.of("noUserpost"), Optional.of("outlook"));
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertTrue(StaticImage.OUTLOOK.is(result.getBody()));
	}
	
	@Test
	void noUserFound_noPlaceholder() throws NoHandlerFoundException {
		assertThrows(RuntimeException.class, () -> rest.getAvatar(Optional.of("noUserpost"), Optional.of("none")));
	}
	
	@Test
	void illegalPlaceholderParameter() throws NoHandlerFoundException {
		assertThrows(IllegalArgumentException.class, () -> rest.getAvatar(Optional.of("noUserpost"), Optional.of("xxx")));
	}
}
