package de.chovy.ldavatar.cache;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
	 * Time in seconds for which the responses of the Ldavatar webservice are cached
	 */
	@Value("${'de.chovy.ldavatar.cache.expire.seconds:10}")
	private long duration;
	
	/**
	 * Configures the {@link CacheManager}.
	 */
	@Bean
	CacheManager cacheManager() {
		final CaffeineCacheManager cacheManager = new CaffeineCacheManager(AVATAR_CACHE);
		cacheManager.setCaffeine(caffeineCacheBuilder());
		return cacheManager;
	}
	
	private Caffeine<Object,Object> caffeineCacheBuilder() {
		return Caffeine.newBuilder().expireAfterWrite(duration, TimeUnit.SECONDS);
	}
}
