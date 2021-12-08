# ldavatar

Gravatar-compatible service that provides avatar images from an LDAP backend, e.g. user photos from an Active Directory.

Build with [Java](https://www.oracle.com/java/), [Spring Boot](https://spring.io/projects/spring-boot) and [Maven](https://maven.apache.org/).

## Configure

### 1. Set LDAP connection and credentials in [application.properties](src/main/resources/application.properties)

```
de.chovy.ldavatar.ldap.url=ldap://ldap.forumsys.com:389
de.chovy.ldavatar.ldap.base=dc=example,dc=com
de.chovy.ldavatar.ldap.userdn=uid=tesla,dc=example,dc=com
de.chovy.ldavatar.ldap.password=password
```

### 2. Change attribute mapping in [LdapUser](src/main/java/de/chovy/ldavatar/ldap/LdapUser.java)

```java
@Attribute(name = "uid") 
private String username;

@Attribute(name = "mail") 
private String email;

@Attribute(name = "thumbnailPhoto") 
private byte[] avatar;
```

For Active Directory change the username attribute from `"uid"` to `"sAMAccountName"`.

## Build

```shell
mvn package
```

## Run

```shell
mvn spring:boot run
```

Open in browser: http://localhost:8080/avatar.jpg?user=test@example.com
