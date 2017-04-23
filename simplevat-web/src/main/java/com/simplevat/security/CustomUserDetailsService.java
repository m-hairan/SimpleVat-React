package com.simplevat.security;

import com.simplevat.entity.User;
import com.simplevat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String emailAddress)
            throws UsernameNotFoundException {
        User user = userService.getUserByEmail(emailAddress);
        if (user == null) {
            throw new UsernameNotFoundException("Email not found");
        }
        return mapToUserDetails(user);
    }

    private UserDetails mapToUserDetails(User user) {
        return new org.springframework.security.core.userdetails.User(user.getUserEmail(), "admin",
                user.getDeleteFlag(), true, true, true, getGrantedAuthorities(user));
    }


    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        return new ArrayList<>(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRoleName())));
    }

}
