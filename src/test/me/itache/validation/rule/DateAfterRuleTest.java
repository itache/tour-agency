package me.itache.validation.rule;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class DateAfterRuleTest {
    @Test
    public void ruleShouldBeValid(){
        Rule rule = new DateAfterRule(LocalDate.now(), "yyyy-MM-dd");
        Assert.assertTrue(rule.validate("2016-09-30"));
    }

    @Test
    public void ruleShouldBeInvalid(){
        Rule rule = new DateAfterRule(LocalDate.now(), "yyyy-MM-dd");
        Assert.assertFalse(rule.validate("2015-09-30"));
        Assert.assertFalse(rule.validate(null));
        Assert.assertFalse(rule.validate("000-32-234"));
    }
}
