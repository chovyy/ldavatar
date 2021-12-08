package de.chovy.ldavatar.avatar;

import java.util.Optional;

/**
 * Delivers placeholder images as raw jpeg data for users without avatar.
 * 
 * @author chovyy
 */
public interface PlaceholderFactory {

	/**
	 * Delivers the default placeholder. 
	 * Can be called in cases where no user was found at all.
	 * Alias for {@code getPlaceholderAvatar(Optional.empty())}.
	 * 
	 * @return The default placeholder image
	 */
	public byte[] getPlaceholderAvatar();
	
	/**
	 * Delivers a placeholder image for a user without avatar.
	 * When {@code userOpt} is empty, the same default placeholder as in {@link #getPlaceholderAvatar()} will be returned.
	 * If the returned image for a non-empty {@code userOpt} depends on the given user, is defined by the implementation.	
	 * 
	 * @param userOpt Might contain the user for whom a placeholder is needed
	 * @return The placeholder image
	 */
	public byte[] getPlaceholderAvatar(Optional<? extends User> userOpt);
}
