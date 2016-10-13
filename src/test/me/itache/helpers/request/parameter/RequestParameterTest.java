package me.itache.helpers.request.parameter;

import me.itache.validation.rule.NotEmptyRule;
import org.junit.Assert;
import org.junit.Test;
import me.itache.validation.rule.AlphaNumericRule;

/**
 *
 */
public class RequestParameterTest {
    @Test
    public void validationShouldPass(){
        RequestParameter validator = new RequestParameter("valid");
        validator.addRules(new NotEmptyRule(), new AlphaNumericRule());
        validator.validate();
        Assert.assertTrue(validator.isValid());
    }

    @Test
    public void validationShouldNotPass() {
        RequestParameter validator = new RequestParameter("");
        validator.addRules(new NotEmptyRule(), new AlphaNumericRule());
        validator.validate();
        Assert.assertFalse(validator.isValid());
    }

    @Test
    public void validationShouldNotPassOnNull() {
        RequestParameter validator = new RequestParameter(null);
        validator.addRules(new NotEmptyRule(), new AlphaNumericRule());
        validator.validate();
        Assert.assertFalse(validator.isValid());
    }

}
