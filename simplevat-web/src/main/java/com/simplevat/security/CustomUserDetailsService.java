package com.simplevat.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simplevat.entity.User;
import com.simplevat.service.UserServiceNew;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserServiceNew userService;

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String emailAddress)
            throws UsernameNotFoundException {
        Optional<User> user = userService.getUserByEmail(emailAddress);

        if (user.isPresent()) {
            return new UserContext(user.get());
        } else {
            throw new UsernameNotFoundException("Email not found");
        }
    }
}
