package de.chovy.ldavatar.placeholders;

import java.util.Optional;

import de.chovy.ldavatar.avatar.PlaceholderFactory;
import de.chovy.ldavatar.avatar.User;

/**
 * {@link PlaceholderFactory} that doesn't return any placeholder but throws {@link NoAvatarFoundException} instead.
 * 
 * @author chovyy
 */
class NoPlaceholderFactory implements PlaceholderFactory {
	
	/**
	 * Always throws {@link NoAvatarFoundException}.
	 * 
	 * @return nothing
	 * 
	 * @throws NoAvatarFoundException on every call
	 */
	@Override
	public byte[] getPlaceholderAvatar(final Optional<? extends User> userOpt)  {
		throw new NoAvatarFoundException();
	}

	/**
	 * Always throws {@link NoAvatarFoundException}.
	 * 
	 * @return nothing
	 * 
	 * @throws NoAvatarFoundException on every call
	 */
	@Override
	public byte[] getPlaceholderAvatar() {
		throw new NoAvatarFoundException();
	}
}
