package com.simplevat.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserContext extends User {

    private int userId;
    private String emailAddress;
    private String firstName;
    private String lastName;

    public UserContext(int userId, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
        this.emailAddress = username;
    }

    public UserContext(com.simplevat.entity.User user) {
        this(user.getUserId(), user.getUserEmail(), user.getPassword(), user.getIsActive(), true, true, true, getGrantedAuthorities(user));
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }

    public int getUserId() {
        return userId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    private static List<GrantedAuthority> getGrantedAuthorities(com.simplevat.entity.User user) {
        String role = "ROLE_EMPLOYEE";
        if(user.getRole().getRoleName().equalsIgnoreCase("ADMIN")){
            role = "ROLE_ADMIN";
        }
        return new ArrayList<>(Collections.singletonList(new SimpleGrantedAuthority(role)));
    }

}
