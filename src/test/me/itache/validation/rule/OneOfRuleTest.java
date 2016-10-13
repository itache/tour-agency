package me.itache.validation.rule;

import org.junit.Assert;
import org.junit.Test;
import me.itache.model.meta.TourMeta;

public class OneOfRuleTest {
    @Test
    public void ruleShouldBeValid(){
        Rule rule = new OneOfRule(TourMeta.PERSONS, TourMeta.DURATION, TourMeta.PRICE);
        Assert.assertTrue(rule.validate("persons"));
    }
    @Test
    public void ruleShouldBeInvalid() {
        Rule rule = new OneOfRule(TourMeta.PERSONS);
        Assert.assertFalse(rule.validate(""));
        Assert.assertFalse(rule.validate(null));
        Assert.assertFalse(rule.validate("pers"));
    }
}
