package services;

import org.junit.Test;

/**
 * Created by iMac on 18/03/17.
 */
public class SecurityTest {
    @Test
    public void getHash() throws Exception {

        HashGenerator security = new HashGenerator();
        System.out.println(security.getHash("password"));

    }

}