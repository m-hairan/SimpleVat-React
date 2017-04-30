package com.simplevat.security;

import com.simplevat.exception.UnauthorizedException;
import org.springframework.security.core.context.SecurityContextHolder;

public class ContextUtils {

    public static UserContext getUserContext() throws UnauthorizedException {
        try {
            return (UserContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception ex) {
            throw new UnauthorizedException("Unable to get logged-in user : " + ex.getMessage(), ex);
        }
    }
}
