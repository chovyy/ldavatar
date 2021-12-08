package de.chovy.ldavatar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Starter class
 * 
 * @author chovyy
 */
@SpringBootApplication
@SuppressWarnings("PMD.UseUtilityClass")
public class LdavatarApplication {

	@SuppressWarnings("PMD.CommentRequired")
	public static void main(final String[] args) {
		SpringApplication.run(LdavatarApplication.class, args);
	}
}
