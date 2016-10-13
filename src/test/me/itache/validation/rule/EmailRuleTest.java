package me.itache.validation.rule;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class EmailRuleTest {
    @Test
    public void emailShouldBeValid(){
        Rule rule = new EmailRule();
        Assert.assertTrue(rule.validate("vitaliy.karpachev@gmail.com"));
    }

    @Test
    public void emailShouldBeInvalid() {
        Rule rule = new EmailRule();
        Assert.assertFalse(rule.validate("vitaliy.karpachev@gmail"));
    }

    @Test
    public void emailShouldBeInvalidOnNull() {
        Rule rule = new EmailRule();
        Assert.assertFalse(rule.validate(null));
    }
}
