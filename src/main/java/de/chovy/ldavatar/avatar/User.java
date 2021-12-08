package de.chovy.ldavatar.avatar;

import java.util.Optional;

/**
 * Represents a user who might have an avatar.
 * 
 * @author chovyy
 */
public interface User {

	/**
	 * @return The identifying username of this user
	 */
	String getUsername();

	/**
	 * @return The email address of this user
	 */
	String getEmail();

	/**
	 * @return The avatar of this user if he/she has one
	 */
	Optional<byte[]> getAvatar();
	
	/**
	 * Guesses if this is this user is an administrator.
	 * Might be called by a {@link PlaceholderFactory} to deliver a placeholder image specific for administrators.
	 * Never to be used for any authorization purposes!!!
	 * 
	 * @return {@code true} if this user seems to be an administrator, otherwise {@code false}
	 */
	boolean isAdmin();

}