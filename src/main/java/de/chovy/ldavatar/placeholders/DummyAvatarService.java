package de.chovy.ldavatar.placeholders;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import de.chovy.ldavatar.avatar.AvatarService;
import de.chovy.ldavatar.avatar.PlaceholderFactory;
import de.chovy.ldavatar.avatar.User;

/**
 * {@link AvatarService} for testing purposes. Delivers the dummy avatar from {@code src/main/resources/static/dummy.jpg}.
 * 
 * @author chovyy
 */
@Service
@Profile("dummy")
public class DummyAvatarService implements AvatarService {
	
	private static final Logger LOG = LoggerFactory.getLogger(DummyAvatarService.class);
	
	@PostConstruct
	void init() {
		LOG.debug("Loaded. Dummy avatars will be delivered.");
	}
	
	/**
	 * @return The dummy avatar if {@code username.length() % 2 == 0}, otherwise the placeholder avatar from {@code pf}.
	 */
	@Override
	public byte[] getAvatarByUsername(final String username, final PlaceholderFactory placeholderFactory)  {
		if (username.length() % 2 == 0) {
			return StaticImage.DUMMY.get();
		}
		return placeholderFactory.getPlaceholderAvatar(Optional.of(dummyUserByUsername(username)));
	}
	
	/**
	 * @return The dummy avatar if {@code email.length() % 2 == 0}, otherwise the placeholder avatar from {@code pf}.
	 */
	@Override
	public byte[] getAvatarByEmail(final String email, final PlaceholderFactory placeholderFactory)  {
		if (email.length() % 2 == 0) {
			return StaticImage.DUMMY.get();
		}
		return placeholderFactory.getPlaceholderAvatar(Optional.of(dummyUserByEmail(email)));
	}
	
	private User dummyUserByUsername(final String username) {
		final String email = username + "@ldavatar.chovy.de";
		return new DummyUser(username, email);
	}
	
	private User dummyUserByEmail(final String email) {
		final String username = email.substring(0, email.indexOf('@'));
		return new DummyUser(username, email);
	}
	
	/**
	 * Dummy user that is passed to the {@link PlaceholderFactory}.
	 */
	private static class DummyUser implements User {
		
		private final String username;
		private final String email;
		
		DummyUser(final String username, final String email) {
			this.username = username;
			this.email = email;
		}
		
		@Override
		public String getUsername() {
			return username;
		}

		@Override
		public String getEmail() {
			return email;
		}

		@Override
		public Optional<byte[]> getAvatar() {
			return Optional.empty();
		}

		@Override
		public boolean isAdmin() {
			return username.toLowerCase().startsWith("adm");
		}
	}

	@Override
	public byte[] getAvatarByHash(final String hash, final PlaceholderFactory placeholderFactory) {
		return placeholderFactory.getPlaceholderAvatar(Optional.of(dummyUserByUsername(hash)));
	}
}
