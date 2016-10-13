package me.itache.validation.rule;

import java.util.regex.Pattern;

/**
 *
 */
public class EmailRule implements Rule {
    private static final Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    @Override
    public boolean validate(String data) {
        if(data == null) {
            return false;
        }
        return pattern.matcher(data).matches();
    }
}
