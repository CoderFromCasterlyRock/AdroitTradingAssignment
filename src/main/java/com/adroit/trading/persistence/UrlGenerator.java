package com.adroit.trading.persistence;

import java.security.SecureRandom;

/**
 * Generates a 6 digit random character string.
 *
 * NOTE:
 * This is a poor man's attempt to generate unique 6 digit values. A serious application will use MD5/Base 64 based hashing.
 */

public final class UrlGenerator {

    private static final int MAX_LENGTH     = 6;
    private static final SecureRandom RND   = new SecureRandom();
    private static final String ALPHABET    = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";


    public final String generateRandomUrl( ){
        var builder   = new StringBuilder(MAX_LENGTH);
        for(int i=0; i < MAX_LENGTH; i++) {
            builder.append(ALPHABET.charAt(RND.nextInt(ALPHABET.length())));
        }

        return builder.toString();
    }


}
