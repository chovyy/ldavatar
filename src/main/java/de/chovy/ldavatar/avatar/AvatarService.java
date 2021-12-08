package de.chovy.ldavatar.avatar;

/**
 * Service that delivers user avatars as raw jpeg data.
 * 
 * @author chovyy
 */
public interface AvatarService {

	/**
	 * Delivers the avatar for a given user with a given username.
	 * 
	 * @param username The username of the user for whom the avatar is wanted
	 * @param placeholderFactory The {@link PlaceholderFactory} from which the placeholder avatar will be taken if no user avatar is found
	 * @return The avatar image of the given user if the user was found and has an avatar, otherwise the placeholder avatar taken from the given {@link PlaceholderFactory}
	 */
	public byte[] getAvatarByUsername(String username, PlaceholderFactory placeholderFactory);
	
	
	/**
	 * Delivers the avatar for a given user with a given email address.
	 * 
	 * @param email The email address of the user for whom the avatar is wanted
	 * @param placeholderFactory The {@link PlaceholderFactory} from which the placeholder avatar will be taken if no user avatar is found
	 * @return The avatar image of the given user if the user was found and has an avatar, otherwise the placeholder avatar taken from the given {@link PlaceholderFactory}
	 */
	public byte[] getAvatarByEmail(String email, PlaceholderFactory placeholderFactory);
}
