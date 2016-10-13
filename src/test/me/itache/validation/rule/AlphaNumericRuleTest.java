package me.itache.validation.rule;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class AlphaNumericRuleTest {
    @Test
    public void ruleShouldBeValidWithNotLatinSymbols(){
        Rule rule = new AlphaNumericRule();
        Assert.assertTrue(rule.validate("пример1"));
    }

    @Test
    public void ruleShouldBeValidWithLatinSymbols(){
        Rule rule = new AlphaNumericRule();
        Assert.assertTrue(rule.validate("example1"));
    }

    @Test
    public void ruleShouldBeInvalid() {
        Rule rule = new AlphaNumericRule();
        Assert.assertFalse(rule.validate("_()"));
    }

    @Test
    public void ruleShouldBeInvalidOnEmptyString(){
        Rule rule = new AlphaNumericRule();
        Assert.assertFalse(rule.validate(""));
    }
}
