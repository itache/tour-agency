package me.itache.validation.rule;

import me.itache.model.meta.EntityField;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class OneOfRule implements RestrictiveRule<List<String>> {
    private List<String> validValues;

    public OneOfRule(EntityField... fields) {
        this.validValues = new ArrayList<>();
        for (EntityField field : fields) {
            validValues.add(field.getFieldName());
        }
    }

    @Override
    public boolean validate(String data) {
        return validValues.contains(data);
    }

    @Override
    public List<String> getRestrictiveValue() {
        return validValues;
    }
}
