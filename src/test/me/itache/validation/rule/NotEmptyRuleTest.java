package me.itache.validation.rule;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class NotEmptyRuleTest {
    @Test
    public void ruleShouldBeValid(){
        Rule rule = new NotEmptyRule();
        Assert.assertTrue(rule.validate("vitaliy.karpachev@gmail.com"));
    }
    @Test
    public void ruleShouldBeInvalid() {
        Rule rule = new NotEmptyRule();
        Assert.assertFalse(rule.validate(""));
        Assert.assertFalse(rule.validate(null));
    }
}
