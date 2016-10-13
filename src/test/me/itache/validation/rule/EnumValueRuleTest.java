package me.itache.validation.rule;

import org.junit.Assert;
import org.junit.Test;
import me.itache.model.entity.User;

/**
 *
 */
public class EnumValueRuleTest {
    @Test
    public void ruleShouldBeValid(){
        Rule rule = new EnumValueRule<>(User.Role.class);
        Assert.assertTrue(rule.validate("CUSTOMER"));
    }

    @Test
    public void ruleShouldBeValidInLowerCase(){
        Rule rule = new EnumValueRule<>(User.Role.class);
        Assert.assertTrue(rule.validate("customer"));
    }

    @Test
    public void ruleShouldBeInvalid() {
        Rule rule = new EnumValueRule<>(User.Role.class);
        Assert.assertFalse(rule.validate("vitaliy.karpachev@gmail"));
    }
}
