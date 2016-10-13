package me.itache.validation.rule;

/**
 *
 */
public class EnumValueRule<T extends Enum<T>> implements Rule {
    private Class<T> enumClass;

    public EnumValueRule(Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public boolean validate(String data) {
        try {
            T.valueOf(enumClass, data.toUpperCase());
            return true;
        }catch (RuntimeException ex) {
            return false;
        }
    }
}
