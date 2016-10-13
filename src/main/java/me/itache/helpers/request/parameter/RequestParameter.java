package me.itache.helpers.request.parameter;

import me.itache.validation.rule.Rule;
import me.itache.utils.StringUtils;
import me.itache.validation.rule.RestrictiveRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents request parameter with validation by specified rules
 */
public class RequestParameter {
    private boolean isValidated;
    private String value;
    private List<Rule> rules = new ArrayList<>();
    private List<Error> errors = new ArrayList<>();

    /**
     * Creates new parameter with given value.
     *
     * @param value
     */
    public RequestParameter(String value) {
        this.value = value;
    }

    /**
     * Creates new parameter with given value and constraint rules
     *
     * @param value
     * @param rules
     */
    public RequestParameter(String value, Rule... rules) {
        this(value);
        addRules(rules);
    }

    public String getValue() {
        return value == null ? null : value.trim();
    }

    public void addRule(Rule rule) {
        rules.add(rule);
    }

    public void addRules(Rule... rules) {
        this.rules.addAll(Arrays.asList(rules));
    }

    /**
     * Checks that parameter value satisfied specified rules
     * @return is value valid
     */
    public boolean isValid() {
        if(!isValidated) {
            validate();
        }
        return errors.size() == 0;
    }

    /**
     * If parameter not validated, validates it and form errors list
     * Error code equals failed rule class name in underscore view.
     * Error value set to restrictive rule value by calling toString method,
     * if rule does not have restrictive value - to empty string.
     *
     * @return errors list
     */
    public List<Error> getErrors() {
        if(!isValidated) {
            validate();
        }
        return new ArrayList<>(errors);
    }

    /**
     * Validates parameter value over specified rules
     */
    public void validate() {
        if(isValidated){
            return;
        }
        for (Rule rule : rules) {
            if (!rule.validate(value)) {
                errors.add(new Error(StringUtils.toUnderscore(rule.getClass().getSimpleName()),
                        (rule instanceof RestrictiveRule)
                                ? String.valueOf(((RestrictiveRule) rule).getRestrictiveValue())
                                : ""));
            }
        }
        isValidated = true;
    }

}
