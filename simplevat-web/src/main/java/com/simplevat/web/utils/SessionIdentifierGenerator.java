package com.simplevat.web.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

public final class SessionIdentifierGenerator {
    private SecureRandom random = new SecureRandom();

    public String randomPassword() {
        return new BigInteger(60, random).toString(32);
    }
}