package me.itache.helpers.request.parameter;

import me.itache.dao.modifier.EntityCondition;
import me.itache.validation.rule.Rule;

/**
 * Request parameter that bind with some EntityCondition for filtering purposes.
 */
public class FilterRequestParameter extends RequestParameter {
    private EntityCondition condition;

    public FilterRequestParameter(String value, EntityCondition condition, Rule... rules) {
        super(value, rules);
        this.condition = condition;
    }

    public EntityCondition getCondition() {
        return condition;
    }
}
