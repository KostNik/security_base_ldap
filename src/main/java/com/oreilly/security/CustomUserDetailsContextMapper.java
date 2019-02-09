package com.oreilly.security;

import com.oreilly.security.domain.entities.AutoUser;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("contextMapper")
public class CustomUserDetailsContextMapper implements UserDetailsContextMapper {

    @Override
    public UserDetails mapUserFromContext(DirContextOperations operations, String username, Collection<? extends GrantedAuthority> authorities) {
        AutoUser autoUser = new AutoUser();
        autoUser.setFirstName("givenName");
        autoUser.setLastName("sn");
        autoUser.setEmail("mail");
        autoUser.setUsername(username);
        autoUser.setAuthorities(authorities);
        return autoUser;
    }

    @Override
    public void mapUserToContext(UserDetails userDetails, DirContextAdapter contextAdapter) {
        AutoUser autoUser = (AutoUser) userDetails;
        contextAdapter.setAttributeValue("givenName", autoUser.getFirstName());
        contextAdapter.setAttributeValue("sn", autoUser.getLastName());
        contextAdapter.setAttributeValue("mail", autoUser.getEmail());
        contextAdapter.setAttributeValue("password", autoUser.getPassword());
        contextAdapter.setAttributeValue("uid", autoUser.getUsername());
    }
}
