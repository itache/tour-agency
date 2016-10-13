package me.itache.validation.rule;

/**
 *
 */
public class MinLengthRule implements RestrictiveRule<Integer> {
    private  final int length;

    public MinLengthRule(int length) {
        this.length = length;
    }

    @Override
    public boolean validate(String data) {
        if(data == null) {
            return false;
        }
        return data.length() >= length;
    }

    @Override
    public Integer getRestrictiveValue() {
        return length;
    }
}
