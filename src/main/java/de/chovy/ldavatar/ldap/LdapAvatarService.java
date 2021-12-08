package de.chovy.ldavatar.ldap;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import de.chovy.ldavatar.avatar.AvatarService;
import de.chovy.ldavatar.avatar.PlaceholderFactory;

/**
 * @{link {@link AvatarService} that fetches user avatars via LDAP
 * .
 * @author chovyy
 */
@Service
@Profile("default")
public class LdapAvatarService implements AvatarService {
	
	private static final Logger LOG = LoggerFactory.getLogger(LdapAvatarService.class);

	private final LdapUserRepository repo;
	
	@PostConstruct
	void init() {
		LOG.debug("Loaded. Avatars will be fetched via LDAP.");
	}

	/**
	 * @param repo The repository that delivers user information from the LDAP connection.
	 */
	@Autowired
	public LdapAvatarService(final LdapUserRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public byte[] getAvatarByUsername(final String username, final PlaceholderFactory placeholderFactory) {
		final Optional<LdapUser> userOpt = repo.findByUsername(username);
		return getAvatarForUser(userOpt, placeholderFactory);
	}

	@Override
	public byte[] getAvatarByEmail(final String email, final PlaceholderFactory placeholderFactory) {
		final Optional<LdapUser> userOpt = repo.findByEmail(email);
		return getAvatarForUser(userOpt, placeholderFactory);
	}
	
	private byte[] getAvatarForUser(final Optional<LdapUser> userOpt, final PlaceholderFactory placeholderFactory) {
		if (userOpt.isPresent()) {
			final Optional<byte[]> avatarOpt = userOpt.get().getAvatar();
			if (avatarOpt.isPresent()) {
				return avatarOpt.get();
			}
		}
		return placeholderFactory.getPlaceholderAvatar(userOpt);
	}
}
