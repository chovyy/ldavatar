package de.chovy.ldavatar.ldap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.ldap.DataLdapTest;
import org.springframework.context.annotation.Import;

import de.chovy.ldavatar.avatar.User;

@DataLdapTest
@Import(LdapConfiguration.class)
class LdapUserRepositoryIT {
	
	@Autowired
	private LdapUserRepository repo;

	@Test
	void repo_loaded() {
		assertNotNull(repo);
	}
	
	@Test
	void userByUsername_tesla() {
		Optional<LdapUser> result = repo.findByUsername("tesla");
		assertTrue(result.isPresent());
		User user = result.get();
		assertEquals("tesla", user.getUsername());
		assertEquals("tesla@ldap.forumsys.com", user.getEmail());
		assertFalse(user.isAdmin());
		assertTrue(user.getAvatar().isEmpty());
	}
	
	@Test
	void userByEmail_tesla() {
		Optional<LdapUser> result = repo.findByEmail("tesla@ldap.forumsys.com");
		assertTrue(result.isPresent());
		User user = result.get();
		assertEquals("tesla", user.getUsername());
		assertEquals("tesla@ldap.forumsys.com", user.getEmail());
		assertFalse(user.isAdmin());
		assertTrue(user.getAvatar().isEmpty());
	}
	
	@Test
	void all() {
		List<LdapUser> all = repo.findAll();
		assertNotNull(all);
		assertFalse(all.isEmpty());
		all.forEach(u -> System.out.println(u.getEmail()));
	}
}
