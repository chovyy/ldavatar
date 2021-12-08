package de.chovy.ldavatar.placeholders;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.chovy.ldavatar.rest.AvatarRestController;

/**
 * Exception thrown by {@link NoPlaceholderFactory} when placeholder is requested.
 * Leads to 404 error in {@link AvatarRestController}.
 * 
 * @author chovyy
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
class NoAvatarFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("PMD.CommentRequired")
	public NoAvatarFoundException() {
		super("No Avatar found!");
	}
}
