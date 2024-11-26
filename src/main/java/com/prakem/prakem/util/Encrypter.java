package com.prakem.prakem.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Encrypter {

    private static final int SALT_LENGTH = 16;
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";

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
        return hashToVerify.equals(hashedPassword);
    }
}
