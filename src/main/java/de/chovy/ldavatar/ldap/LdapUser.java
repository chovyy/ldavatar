package de.chovy.ldavatar.ldap;

import java.util.Optional;

import javax.naming.Name;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import de.chovy.ldavatar.avatar.User;

/**
 * {@link User} object that is filled from LDAP.
 * 
 * Edit the attributes if they doesn't match your directory attributes,
 * e.g. change "uid" to "sAMAccountName" or "userPrincipalName".
 * 
 * @author chovyy
 */
@Entry(objectClasses = "person")
public final class LdapUser implements User {
   
	@Id
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.ShortVariable" })
	private Name id;
    
    @Attribute(name = "uid") 
    private String username;
    
    @Attribute(name = "mail") 
    private String email;
    
    @Attribute(name = "thumbnailPhoto") 
    private byte[] avatar;

    @Override
	public String getUsername() {
		return username;
	}
    
    @Override
	public String getEmail() {
		return email;
	}
    
    @Override
	public Optional<byte[]> getAvatar() {
		return Optional.ofNullable(avatar);
	}
	
	/**
	 * @return {@code true} if the username of this user starts with {@code "adm"}, otherwise false
	 */
	@Override
	public boolean isAdmin() {
		return username.toLowerCase().startsWith("adm");
	}
}