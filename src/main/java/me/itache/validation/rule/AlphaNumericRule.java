package me.itache.validation.rule;

import java.util.regex.Pattern;

/**
 * Checks that string contains only letters(utf-8) and numbers.
 */
public class AlphaNumericRule implements Rule {
    private static final Pattern pattern = Pattern.compile("\\p{Alnum}{1,}", Pattern.UNICODE_CHARACTER_CLASS);

    @Override
    public boolean validate(String data) {
        if (data == null) {
            return false;
        }
        return pattern.matcher(data).matches();
    }
}
