package com.simplevat.web.exception;

public class UnauthorizedException extends Exception {

    public UnauthorizedException(String message, Exception ex) {
        super(message, ex);
    }
}