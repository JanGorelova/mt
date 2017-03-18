package services;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by iMac on 18/03/17.
 */
public class HashGenerator {

    private final String ALGORITHM = "MD5";
    private MessageDigest digest;

    public HashGenerator() throws NoSuchAlgorithmException {
        digest = MessageDigest.getInstance(ALGORITHM);
    }

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
