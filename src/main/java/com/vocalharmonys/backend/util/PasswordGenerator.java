package com.vocalharmonys.backend.util;

import java.security.SecureRandom;
import org.springframework.stereotype.Component;

/**
 * Generates the one-time passwords sent to newly created members. Never
 * reused for anything else, and the result is only ever handed to the
 * caller in memory — nothing here logs or persists the plaintext.
 */
@Component
public class PasswordGenerator {

    // Ambiguous characters (l/1/I, O/0) are left out so a password read off
    // an email doesn't get mistyped.
    private static final String ALPHABET =
            "ABCDEFGHJKMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789!@#$%&*";
    private static final int LENGTH = 14;

    private final SecureRandom random = new SecureRandom();

    public String generate() {
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }
}
