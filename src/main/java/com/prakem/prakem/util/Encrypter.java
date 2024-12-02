package com.prakem.prakem.util;

import com.prakem.prakem.exception.PasswordValidationException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.regex.Pattern;

public class Encrypter {

    private static final int SALT_LENGTH = 16;
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";

    private static final String PASSWORD_PATTERN = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,}";

    /**
     * Generates a random salt for password hashing.
     *
     * @return A Base64-encoded salt string.
     */
    public static String generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Hashes a password using PBKDF2 with the provided salt.
     *
     * @param password The password to hash.
     * @param salt     The salt to use for hashing (Base64-encoded).
     * @return A Base64-encoded hash of the password.
     * @throws NoSuchAlgorithmException If the algorithm is not supported.
     */
    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        try {
            byte[] decodedSalt = Base64.getDecoder().decode(salt);
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), decodedSalt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hash = keyFactory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new NoSuchAlgorithmException("Error while hashing the password: " + e.getMessage(), e);
        }
    }

    /**
     * Verifies if the provided password matches the hashed password.
     *
     * @param password       The plain-text password to verify.
     * @param salt           The salt used during hashing (Base64-encoded).
     * @param hashedPassword The previously hashed password (Base64-encoded).
     * @return True if the password matches; otherwise, false.
     * @throws NoSuchAlgorithmException If the algorithm is not supported.
     */
    public static boolean verifyPassword(String password, String salt, String hashedPassword) throws NoSuchAlgorithmException {
        String hashToVerify = hashPassword(password, salt);
        return secureEquals(hashToVerify, hashedPassword);
    }

    /**
     * Verifies if the provided password matches the minimum complexity
     *
     * @param password The plain-text password to validate.
     */
    public static void validatePasswordComplexity(String password) {
        if (!password.matches(PASSWORD_PATTERN) || password.contains(" ")) {
            throw new PasswordValidationException(
                    "Password must have minimum 8 characters long, include at least one uppercase letter, one lowercase letter, one number, and one special character, and must not contain spaces."
            );
        }
    }

    /**
     * Function that implements constant time comparison
     * https://www.huy.rocks/toylisp/02-07-2022-security-timing-attack-and-constant-time-compare-algorithm - Reference Link
     *
     * @param a String to compare to b
     * @param b String to compare to a
     * @return True if Strings are bite a bite equals
     */
    private static boolean secureEquals(String a, String b) {
        if (a.length() != b.length()) return false;
        int result = 0;
        for (int i = 0; i < a.length(); i++) {
            result |= a.charAt(i) ^ b.charAt(i);
        }
        return result == 0;
    }
}