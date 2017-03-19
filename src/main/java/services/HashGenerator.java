package services;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Wrapper for MessageDigest class, generates hash from a string
 */
public class HashGenerator {

    // Hash algorithm
    private static final String ALGORITHM = "MD5";
    private MessageDigest digest;

    /**
     * Creates a new instance of HashGenerator
     * @throws NoSuchAlgorithmException can't happen
     */
    public HashGenerator() throws NoSuchAlgorithmException {
        digest = MessageDigest.getInstance(ALGORITHM);
    }

    /**
     * Generates hash from a string
     * @param password string to hash
     * @return hashed string
     */
    public String getHash(String password)  {
        digest.reset();
        try {
            byte[] result = digest.digest(password.getBytes("UTF-8"));
            return new String(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
