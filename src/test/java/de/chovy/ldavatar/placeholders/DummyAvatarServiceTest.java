package de.chovy.ldavatar.placeholders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.NoHandlerFoundException;

import de.chovy.ldavatar.avatar.PlaceholderFactory;

class DummyAvatarServiceTest {
	
	public static final byte[] PLACEHOLDER_BYTES = "placeholder".getBytes();

	private DummyAvatarService service;
	
	@Mock
	private PlaceholderFactory pf;

	@BeforeEach
	void setUp() throws NoHandlerFoundException {
		MockitoAnnotations.openMocks(this);
		when(pf.getPlaceholderAvatar(any())).thenReturn(PLACEHOLDER_BYTES);
		service = new DummyAvatarService();
	}

	@Test
	void by_username_even_length() throws NoHandlerFoundException {
		byte[] result = service.getAvatarByUsername("test", pf);
		assertNotNull(result);
		assertTrue(StaticImage.DUMMY.is(result));
	}
	
	@Test
	void by_username_odd_length() throws NoHandlerFoundException {
		byte[] result = service.getAvatarByUsername("test0", pf);
		assertNotNull(result);
		assertEquals(PLACEHOLDER_BYTES, result);
	}
	
	@Test
	void by_email_even_length() throws NoHandlerFoundException {
		byte[] result = service.getAvatarByEmail("test@test.de", pf);
		assertNotNull(result);
		assertTrue(StaticImage.DUMMY.is(result));
	}
	
	@Test
	void by_email_odd_length() throws NoHandlerFoundException {
		byte[] result = service.getAvatarByEmail("test0@test.de", pf);
		assertNotNull(result);
		assertEquals(PLACEHOLDER_BYTES, result);
	}
}
