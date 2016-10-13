package me.itache.validation.rule;

/**
 *
 */
public class NotEmptyRule implements Rule {
    @Override
    public boolean validate(String data) {
        return (data != null && data.trim().length() != 0);
    }
}
