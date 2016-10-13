package me.itache.validation.rule;

/**
 *
 */
public class EqualsRule implements RestrictiveRule<String> {
    private String parameter;

    public EqualsRule(String parameter) {
        this.parameter = parameter;
    }

    @Override
    public boolean validate(String data) {
        if(parameter == null) {
            return data == null;
        }
        return parameter.equals(data);
    }

    @Override
    public String getRestrictiveValue() {
        return parameter;
    }
}
