package de.chovy.ldavatar.cache;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * Spring + Caffeine cache configuration
 * 
 * @see 
 * <a href="https://www.baeldung.com/spring-boot-caffeine-cache">
 *   https://www.baeldung.com/spring-boot-caffeine-cache
 * </a>
 * 
 * @author chovyy
 */
@Configuration
@EnableCaching
public class CacheConfiguration {

	/**
	 * Name of the avatar cache
	 */
	public static final String AVATAR_CACHE = "avatars";
	
	/**
	 * Name of the email hashes cache
	 */
	public static final String HASHES_CACHE = "hashes";
	
	/**
	 * Name of the email hashes cache
	 */
	public static final String HASHES_CACHE_MANAGER = "hashesCacheManager";
	
	private static final TimeUnit DURATION_UNIT_AVATARS = TimeUnit.SECONDS;
	private static final TimeUnit DURATION_UNIT_HASHES = TimeUnit.MINUTES;

	/**
	 * Time in seconds for which the responses of the Ldavatar webservice are cached
	 */
	@Value("${de.chovy.ldavatar.cache.avatars.expire.seconds:10}")
	private int durationAvatars;
	
	/**
	 * Time in minutes for which the email hashes are cached
	 */
	@Value("${de.chovy.ldavatar.cache.hashes.expire.minutes:60}")
	private int durationHashes;
	
	/**
	 * Configures the {@link CacheManager} for avatars
	 */
	@Bean
	@Primary
	CacheManager cacheManager() {
		final CaffeineCacheManager cacheManager = new CaffeineCacheManager(AVATAR_CACHE);
		cacheManager.setCaffeine(caffeine(durationAvatars, DURATION_UNIT_AVATARS));
		return cacheManager;
	}
	
	/**
	 * Configures the {@link CacheManager} for email hashes
	 */
	@Bean
	CacheManager hashesCacheManager() {
		final CaffeineCacheManager cacheManager = new CaffeineCacheManager(HASHES_CACHE);
		cacheManager.setCaffeine(caffeine(durationHashes, DURATION_UNIT_HASHES));
		return cacheManager;
	}
	
	private Caffeine<Object,Object> caffeine(final int duration, final TimeUnit durationUnit) {
		return Caffeine.newBuilder().expireAfterWrite(duration, durationUnit);
	}
}
