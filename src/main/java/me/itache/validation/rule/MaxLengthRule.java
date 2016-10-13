package me.itache.validation.rule;

/**
 *
 */
public class MaxLengthRule implements RestrictiveRule<Integer> {
    private final int length;

    public MaxLengthRule(int length) {
        this.length = length;
    }

    @Override
    public boolean validate(String data) {
        if (data == null) {
            return false;
        }
        return data.length() <= length;
    }

    @Override
    public Integer getRestrictiveValue() {
        return length;
    }
}
