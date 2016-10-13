package me.itache.helpers.request.parameter;

/**
 * Holder for validate error data.
 */
public class Error {
    private String value;
    private String code;

    /**
     * Creates new error object
     *
     * @param code underscore validation rule class name(MinLengthRule -> min_length_rule)
     * @param value restriction value of rule.
     */
    public Error(String code, String value) {
        this.value = value;
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public String getCode() {
        return code;
    }
}
