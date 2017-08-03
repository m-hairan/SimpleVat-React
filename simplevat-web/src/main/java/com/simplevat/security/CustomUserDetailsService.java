package com.simplevat.security;

import com.simplevat.entity.User;
import com.simplevat.service.UserServiceNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import javax.faces.context.FacesContext;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserServiceNew userService;

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String emailAddress)
            throws UsernameNotFoundException {
        Optional<User> user = userService.getUserByEmail(emailAddress);

        if (user.isPresent()) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedInUser", user.get());
            return new UserContext(user.get());
        } else {
            throw new UsernameNotFoundException("Email not found");
        }
    }
}
