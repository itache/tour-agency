package me.itache.utils;

import org.junit.Assert;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

/**
 *
 */
public class HasherTest {
    @Test
    public void shouldProduceCorrectHashedString() throws NoSuchAlgorithmException {
        String expected = "21232f297a57a5a743894a0e4a801fc3";
        Assert.assertEquals(expected, Hasher.hash("admin", "md5"));
    }
}
