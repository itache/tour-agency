package me.itache.validation.rule;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 */
public class LatinNumericRuleTest {
    private static Rule rule;

    @BeforeClass
    public static void init() {
        rule = new LatinNumericRule();
    }
    @Test
    public void ruleShouldBeInvalidWithNotLatinSymbols(){
        Assert.assertFalse(rule.validate("пример1"));
    }

    @Test
    public void ruleShouldBeValidWithLatinSymbols(){
        Assert.assertTrue(rule.validate("example1"));
    }

    @Test
    public void ruleShouldBeInvalid() {
        Assert.assertFalse(rule.validate("_()"));
    }

    @Test
    public void ruleShouldBeInvalidOnEmptyString(){
        Assert.assertFalse(rule.validate(""));
    }
}
