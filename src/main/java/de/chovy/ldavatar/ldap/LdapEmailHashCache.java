package de.chovy.ldavatar.ldap;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import de.chovy.ldavatar.cache.CacheConfiguration;

/**
 * Retrieves all users from {@link LdapUserRepository} and calculates the gravatar hashes of their email addresses.
 * @author chovyy
 */
@Service
class LdapEmailHashCache {
	
	private static final Logger LOG = LoggerFactory.getLogger(LdapEmailHashCache.class);
	
	private final LdapUserRepository repo;

	/**
	 * @param repo The repository that delivers user information from the LDAP connection.
	 */
	@Autowired
	public LdapEmailHashCache(final LdapUserRepository repo) {
		this.repo = repo;
	}

	/**
	 * @return A map of all user email address hashes to the email addresses themselves
	 */
	@Cacheable(cacheNames = CacheConfiguration.HASHES_CACHE, cacheManager = CacheConfiguration.HASHES_CACHE_MANAGER)
	public Map<String, String> getHashes2EmailMap() {
		LOG.debug("Calculate email hashes...");
		final long startTime = System.currentTimeMillis();
		final Map<String, String> hashes2emails = repo.findAll()
				   .stream()
				   .map(LdapUser::getEmail)
				   .filter(Objects::nonNull)
				   .distinct()
				   .collect(Collectors.toMap(email -> hash(email), email -> email));
		final long endTime = System.currentTimeMillis();
		final long duration = endTime - startTime;
		LOG.debug("Number of calculated hashes: {}, duration: {} ms", hashes2emails.size(), duration);
		return hashes2emails;
		
	}
	
	private String hash(final String text) {
		return DigestUtils.md5Hex(text.trim().toLowerCase());
	}
}
