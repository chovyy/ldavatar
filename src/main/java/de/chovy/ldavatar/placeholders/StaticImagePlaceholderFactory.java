package de.chovy.ldavatar.placeholders;

import java.util.Optional;

import de.chovy.ldavatar.avatar.PlaceholderFactory;
import de.chovy.ldavatar.avatar.User;

/**
 * {@link PlaceholderFactory} that delivers static images as placeholders.
 * 
 * @author chovyy
 */
class StaticImagePlaceholderFactory implements PlaceholderFactory {
	
	private final StaticImage userImage;
	private final StaticImage adminImage;

	/**
	 * Constructor for a placeholder factory that delivers a special placeholder image for admin users.
	 * 
	 * @param userPlaceholderImage The placeholder image for normal users
	 * @param adminPlaceholderImage The placeholder image for admin users
	 */
	public StaticImagePlaceholderFactory(final StaticImage userPlaceholderImage, final StaticImage adminPlaceholderImage) {
		this.userImage = userPlaceholderImage;
		this.adminImage = adminPlaceholderImage;
	}
	
	/**
	 * Constructor for a placeholder factory that delivers the same placeholder image for normal and admin users.
	 * 
	 * @param placeholderImage The placeholder image for all users
	 */
	public StaticImagePlaceholderFactory(final StaticImage placeholderImage) {
		this.userImage = placeholderImage;
		this.adminImage = placeholderImage;
	}

	@Override
	public byte[] getPlaceholderAvatar() {
		return getPlaceholderAvatar(Optional.empty());
	}

	@Override
	public byte[] getPlaceholderAvatar(final Optional<? extends User> userOpt) {
		if (userOpt.isPresent() && userOpt.get().isAdmin()) {
			return adminImage.get();
		}
		return userImage.get();
	}
}
