package de.chovy.ldavatar.ldap;

import java.util.Optional;

import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

/**
 * Delivers user information from the LDAP connection.
 * 
 * @author chovyy
 */
@Repository
public interface LdapUserRepository extends LdapRepository<LdapUser> {

	/**
	 * Searches for a user by the username attribute configured in {@link LdapUser}.
	 * 
	 * @param username The username to search for
	 * @return {@link Optional} containing the user with the given username or empty {@link Optional} if no user was found with the given username
	 */
	Optional<LdapUser> findByUsername(String username);

	/**
	 * Searches for a user by the email attribute configured in {@link LdapUser}.
	 * 
	 * @param email The email address to search for
	 * @return {@link Optional} containing the user with the given email address or empty {@link Optional} if no user was found with the given email
	 */
	Optional<LdapUser> findByEmail(String email);
}