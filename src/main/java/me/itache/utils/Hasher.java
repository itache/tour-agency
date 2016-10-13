package me.itache.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Converts string to hex with specified algorithm.
 */
public class Hasher {
    /**
     * Converts string to hex with specified hash algorithm.
     *
     * @param input     string to convert
     * @param algorithm algorithm to get hash
     * @return converted string.
     * @throws NoSuchAlgorithmException if algorithm not found
     */
    public static String hash(final String input, final String algorithm) throws
            NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        digest.update(input.getBytes(Charset.defaultCharset()));
        byte[] hash = digest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
