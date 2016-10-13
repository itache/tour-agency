package me.itache.validation.rule;

/**
 *
 */
public class MoreThanInclusivelyRule implements RestrictiveRule<Integer> {
    private int bound;

    public MoreThanInclusivelyRule(int bound) {
        this.bound = bound;
    }

    @Override
    public boolean validate(String data) {
        try {
            int value = Integer.parseInt(data);
            return value >= bound;
        } catch (RuntimeException ex) {
            return false;
        }
    }

    @Override
    public Integer getRestrictiveValue() {
        return bound;
    }
}
