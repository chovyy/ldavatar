package de.chovy.ldavatar.ldap;

import java.util.Map;
import java.util.Optional;

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
	
	private final LdapUserRepository repo;
	private final LdapEmailHashService hashService;
	 
	/**
	 * @param repo The repository that delivers user information from the LDAP connection.
	 * @param hashService The service which calculates the email hashes
	 */
	@Autowired
	public LdapAvatarService(final LdapUserRepository repo, final LdapEmailHashService hashService) {
		this.repo = repo;
		this.hashService = hashService;
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

	@Override
	public byte[] getAvatarByHash(final String hash, final PlaceholderFactory placeholderFactory) {
		Optional<LdapUser> userOpt = Optional.empty();
		final Map<String, String> hashes2email = hashService.getHashes2EmailMap();
		if (hashes2email.containsKey(hash)) {
			userOpt = repo.findByEmail(hashes2email.get(hash));
		}
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
