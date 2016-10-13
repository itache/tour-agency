package me.itache.validation.rule;

import java.util.regex.Pattern;

/**
 * Checks that string contains only latin letters and numbers.
 */
public class LatinNumericRule implements Rule {
    private static final Pattern pattern = Pattern.compile("\\p{Alnum}{1,}");

    @Override
    public boolean validate(String data) {
        if (data == null) {
            return false;
        }
        return pattern.matcher(data).matches();
    }
}
