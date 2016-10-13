package me.itache.validation.rule;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 */
public class DateRule implements RestrictiveRule<String> {
    private String pattern;

    public DateRule(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean validate(String data) {
        if(data == null) {
            return false;
        }
        try {
            DateFormat df = new SimpleDateFormat(pattern);
            df.setLenient(false);
            df.parse(data);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public String getRestrictiveValue() {
        return pattern;
    }
}
