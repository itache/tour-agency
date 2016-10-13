package me.itache.utils;

/**
 * Contains convenient methods to string processing
 */
public class StringUtils {
    /**
     * Converts camelCaseString to underscore_string
     *
     * @param string
     * @return converted string
     */
    public static String toUnderscore(String string) {
        if (string == null) {
            return null;
        }
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        return string.replaceAll(regex, replacement).toLowerCase();
    }

    /**
     * Converts underscore_string to camelCaseString
     *
     * @param string to convert
     * @return converted string
     */
    public static String toCamelCase(String string) {
        String[] parts = string.split("_");
        String camelCaseString = "";
        for (int i = 0; i < parts.length; i++) {
            if (i == 0) {
                camelCaseString = parts[i];
                continue;
            }
            camelCaseString = camelCaseString + toProperCase(parts[i]);
        }
        return camelCaseString;
    }

    private static String toProperCase(String s) {
        return s.substring(0, 1).toUpperCase() +
                s.substring(1).toLowerCase();
    }
}
