package me.itache.validation.rule;

public class LessThanOrEqualsRule implements Rule {
    private String bound;

    public LessThanOrEqualsRule(String bound) {
        this.bound = bound;
    }

    @Override
    public boolean validate(String data) {
        try {
            return Integer.parseInt(data) <= Integer.parseInt(bound);
        } catch (RuntimeException ex) {
            return false;
        }
    }
}
