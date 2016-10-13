package me.itache.validation.rule;

import org.junit.Assert;
import org.junit.Test;

public class MoreThanInclusivelyRuleTest {
    @Test
    public void ruleShouldBeValid(){
        Rule rule = new MoreThanInclusivelyRule(6);
        Assert.assertTrue(rule.validate("6"));
        Assert.assertTrue(rule.validate("100"));
    }
    @Test
    public void ruleShouldBeInvalid() {
        Rule rule = new MoreThanInclusivelyRule(7);
        Assert.assertFalse(rule.validate(""));
        Assert.assertFalse(rule.validate(null));
        Assert.assertFalse(rule.validate("pers"));
        Assert.assertFalse(rule.validate("5"));
    }
}
