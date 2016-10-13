package me.itache.helpers.request.parameter;

import me.itache.validation.rule.Rule;

/**
 * Request parameter with default value
 */
public class DefaultRequestParameter extends RequestParameter {
    private String defaultValue;

    public DefaultRequestParameter(String value, String defaultValue, Rule... rules) {
        super(value, rules);
        this.defaultValue = defaultValue;
    }

    /**
     * @return provided value it is valid, default value - otherwise.
     */
    public String getValidValue() {
        if(isValid()) {
            return getValue();
        }
        return defaultValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
