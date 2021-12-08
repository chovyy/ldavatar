package de.chovy.ldavatar.placeholders;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import org.apache.commons.io.IOUtils;
import org.springframework.util.DigestUtils;

import de.chovy.ldavatar.avatar.PlaceholderFactory;

/**
 * Enum that gives easy access to all images in {@code src/main/resources/static}.
 * 
 * @author chovyy
 */
public enum StaticImage {

	/**
	 * Dummy image used by {@link DummyAvatarService}
	 */
	DUMMY("dummy.jpg", "2f2edd2d289ec3244c3208cdf39d85cf"),
	
	/**
	 * Placeholder image for normal users used by the {@link PlaceholderFactory} of {@link Placeholders#AWESOME}
	 */
	AWESOME_USER("awesome_user.jpg", "7214297f3d267ae529823fccbe9de78f"),
	
	/**
	 * Placeholder image for admin users used by the {@link PlaceholderFactory} of {@link Placeholders#AWESOME}
	 */
	AWESOME_ADMIN("awesome_admin.jpg", "b125196f5fc7f68b9874c5dbb311b6b5"),
	
	/**
	 * Placeholder image used by the {@link PlaceholderFactory} of {@link Placeholders#OUTLOOK}
	 */
	OUTLOOK("outlook.jpg", "2ddc94045c36536e3c29bd32163af97c"),
	
	/**
	 * Placeholder image used by the {@link PlaceholderFactory} of {@link Placeholders#BLANK}
	 */
	BLANK("blank.jpg", "cca7e6273c0e8e0db1cb19ae1af8a798");
	
	private final String filename;
	private final String md5;
	private byte[] image;

	private StaticImage(final String filename, final String md5) {
		this.filename = filename;
		this.md5 = md5;
	}
	
	/**
	 * @return The image as raw jpeg data.
	 */
	public byte[] get() {
		if (image == null) {
			image = load();
		}
		return image.clone();
	}
	
	@SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
	private byte[] load() {
		try (final InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("static/" + filename)) {
			return IOUtils.toByteArray(input);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Checks if some image data is exactly this image. For testing purposes.
	 * 
	 * @param image Some byte array
	 * @return true if {@code image} contains the raw jpeg data of this image, otherwise false
	 */
	@SuppressWarnings("PMD.ShortMethodName")
	public boolean is(final byte[] image) {
		Objects.requireNonNull(image);
		return md5.equals(DigestUtils.md5DigestAsHex(image));
	}
}
