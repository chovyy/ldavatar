package de.chovy.ldavatar.rest;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.chovy.ldavatar.avatar.AvatarService;
import de.chovy.ldavatar.avatar.PlaceholderFactory;
import de.chovy.ldavatar.cache.CacheConfiguration;
import de.chovy.ldavatar.placeholders.Placeholders;

/**
 * Rest interface delivering the avatars.
 * 
 * @author chovyy
 */
@RestController
public class AvatarRestController {
	
	private static final Logger LOG = LoggerFactory.getLogger(AvatarRestController.class);
	private static final Placeholders DEFAULT_PLACEHOLDERS = Placeholders.AWESOME;
	
	private final AvatarService avatarService;
	
	private final String removeSuffix;
	
	/**
	 * @param removeSuffix suffix that will be removed from usernames before searching for users.
	 * @param avatarService {@link AvatarService} that delivers the avatars
	 */
	public AvatarRestController(
			@Value("${de.chovy.ldavatar.rest.username.remove.suffix:}") final String removeSuffix,
			final AvatarService avatarService) {
		this.removeSuffix = removeSuffix.toLowerCase();
		LOG.debug("Remove suffix: " + this.removeSuffix);
		this.avatarService = avatarService;
	}

	/**
	 * Delivers the avatars by username or email as query parameter {@code user}.
	 * 
	 * @param user Optional containing username or email address of the user for whom the avatar is requested. 
	 * @param d Name of the placeholder option defined in {@link Placeholders}
	 * 
	 * @return {@link ResponseEntity} containing the requested avatar as jpeg image if {@code user} is not empty 
	 * and an avatar is found for the given username or email address. Otherwise, the placeholder defined by {@code d}
	 * is returned, or if {@code d == 'none'} a 404 error.
	 */
	@GetMapping("/avatar.jpg")
	@Cacheable(CacheConfiguration.CACHE_NAME)
	@SuppressWarnings("PMD.ShortVariable")
	public ResponseEntity<byte[]> getAvatarByUser(@RequestParam final Optional<String> user, @RequestParam final Optional<String> d)  {
		final PlaceholderFactory placeholderFactory = getPlaceholderFactory(d);
		final byte[] avatar = findAvatarByUser(user, placeholderFactory);
		return new ResponseEntity<>(avatar, buildHeaders(), HttpStatus.OK);
	}
	
	/**
	 * Gravatar-compatible interface. Delivers the avatars by email hash passed as path variable.
	 * 
	 * @param hash Optional containing Gravatar-compatible MD5 hash of the email address of the user for whom the avatar is requested. 
	 * @param d Name of the placeholder option defined in {@link Placeholders}
	 * 
	 * @return {@link ResponseEntity} containing the requested avatar as jpeg image if {@code user} is not empty 
	 * and an avatar is found for the given username or email address. Otherwise, the placeholder defined by {@code d}
	 * is returned, or if {@code d == 'none'} a 404 error.
	 */
	@GetMapping("/gravatar/{hash}")
	@Cacheable(CacheConfiguration.CACHE_NAME)
	@SuppressWarnings("PMD.ShortVariable")
	public ResponseEntity<byte[]> getAvatarByHash(@PathVariable final Optional<String> hash, @RequestParam final Optional<String> d)  {
		final PlaceholderFactory placeholderFactory = getPlaceholderFactory(d);
		final byte[] avatar = findAvatarByHash(hash, placeholderFactory);
		return new ResponseEntity<>(avatar, buildHeaders(), HttpStatus.OK);
	}
	
	private PlaceholderFactory getPlaceholderFactory(final Optional<String> placeholderParam) {
		if (placeholderParam.isPresent()) {
			final String param = placeholderParam.get().trim().toUpperCase();
			try {
				final Placeholders placeholders = Placeholders.valueOf(param);
				return placeholders.getFactory();
			}
			catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("No valid value for parameter d given, expected one of: " + Placeholders.joinNames(), e);
			}
		}
		return DEFAULT_PLACEHOLDERS.getFactory();
	}
	 
	private byte[] findAvatarByUser(final Optional<String> userParam, final PlaceholderFactory placeholderFactory)  {
		LOG.debug("user: " + userParam.orElse(""));
		if (userParam.isPresent()) {
			final String user = normalizeUserParam(userParam.get());
			LOG.debug("normalized: " + user);
			if (user.indexOf('@') < 0) {
				LOG.debug("...is username.");
				return avatarService.getAvatarByUsername(user, placeholderFactory);
			}
			else {
				LOG.debug("...is email.");
				return avatarService.getAvatarByEmail(user, placeholderFactory);
			}
		}
		return placeholderFactory.getPlaceholderAvatar();
	}
	
	private byte[] findAvatarByHash(final Optional<String> hashParam, final PlaceholderFactory placeholderFactory)  {
		LOG.debug("hash: " + hashParam.orElse(""));
		if (hashParam.isPresent()) {
			return avatarService.getAvatarByHash(hashParam.get(), placeholderFactory);
		}
		return placeholderFactory.getPlaceholderAvatar();
	}
	
	private String normalizeUserParam(final String userParam) {
		String normalized = userParam.trim().toLowerCase();
		if (removeSuffix != null && !removeSuffix.isBlank() && normalized.endsWith(removeSuffix.trim())) {
			normalized = normalized.substring(0, normalized.length() - removeSuffix.trim().length());
		}
		return normalized;
	}
	
	private HttpHeaders buildHeaders() {
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		return headers;
	}
}
