package de.chovy.ldavatar.placeholders;

import java.util.Arrays;
import java.util.stream.Collectors;

import de.chovy.ldavatar.avatar.PlaceholderFactory;

/**
 * Enum of all Placeholder options.
 * 
 * @author chovyy
 */
public enum Placeholders {
	
	/**
	 * Contains {@link PlaceholderFactory} that delivers Placeholder avatars made from 
	 * <a href="https://fontawesome.com">Fontawesome icons</a> with special placeholder
	 * for admin users.
	 */
	AWESOME(new StaticImagePlaceholderFactory(StaticImage.AWESOME_USER, StaticImage.AWESOME_ADMIN)),
	
	/**
	 * Contains {@link PlaceholderFactory} that delivers the same placeholder icon that
	 * shown in MS Outlook 2016 for users with no avatar.
	 */
	OUTLOOK(new StaticImagePlaceholderFactory(StaticImage.OUTLOOK)),
	
	/**
	 * Contains {@link PlaceholderFactory} that delivers a placeholder of 1 white pixel.
	 */
	BLANK(new StaticImagePlaceholderFactory(StaticImage.BLANK)),
	
	/**
	 * Contains {@link PlaceholderFactory} that delivers no placeholder but throws 
	 * {@link NoAvatarFoundException} instead.
	 */
	NONE(new NoPlaceholderFactory());
	
	private final PlaceholderFactory factory;

	private Placeholders(final PlaceholderFactory factory) {
		this.factory = factory;
	}
	
	/**
	 * @return The {@link PlaceholderFactory} of this placeholder option.
	 */
	public PlaceholderFactory getFactory() {
		return factory;
	}
	
	/**
	 * @return A comma-separated list of all placeholder option names in lower case.
	 */
	public static String joinNames() {
		return Arrays.stream(values()).map(p -> p.name().toLowerCase()).collect(Collectors.joining(", "));
	}
}
