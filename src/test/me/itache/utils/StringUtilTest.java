package me.itache.utils;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilTest {
    @Test
    public void shouldConvertStringToUnderscore() {
        String expected = "camel_case";
        Assert.assertEquals(expected, StringUtils.toUnderscore("camelCase"));
    }

    @Test
    public void shouldConvertStringToCamelCase() {
        String expected = "camelCase";
        Assert.assertEquals(expected, StringUtils.toCamelCase("camel_case"));
    }
}
