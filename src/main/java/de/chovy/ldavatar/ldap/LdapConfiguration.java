package de.chovy.ldavatar.ldap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;
import org.springframework.ldap.core.support.LdapContextSource;

/**
 * Spring LDAP configuration
 * 
 * @see 
 * <a href="https://www.baeldung.com/spring-data-ldap">
 *   https://www.baeldung.com/spring-data-ldap
 * </a>
 * 
 * @author chovyy
 */
@Configuration
@EnableLdapRepositories
class LdapConfiguration {
	
	/**
	 * URL of the LDAP server
	 */
	@Value("${de.chovy.ldavatar.ldap.url:}") 
	private String url;
	
	/**
	 * LDAP base to search for users
	 */
	@Value("${de.chovy.ldavatar.ldap.base:}") 
	private String base;
	
	/**
	 * Full DN of the user to bind with
	 */
	@Value("${de.chovy.ldavatar.ldap.userdn:}") 
	private String userDn;
	
	/**
	 * Password of the bind user
	 */
	@Value("${de.chovy.ldavatar.ldap.password:}") 
	private String password;
	
	/**
	 * Configures the {@link LdapContextSource}.
	 */
	@Bean
	public LdapContextSource contextSource() {
	    final LdapContextSource contextSource = new LdapContextSource();
	    contextSource.setUrl(url);
	    contextSource.setBase(base);
	    contextSource.setUserDn(userDn);
	    contextSource.setPassword(password);
	    
	    return contextSource;
	}
}
