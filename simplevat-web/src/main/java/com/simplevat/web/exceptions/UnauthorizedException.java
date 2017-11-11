package com.simplevat.web.exceptions;

public class UnauthorizedException extends Exception {

    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String message, Exception ex) {
        super(message, ex);
    }
}
