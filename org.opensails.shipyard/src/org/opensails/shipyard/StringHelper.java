package org.opensails.shipyard;

public class StringHelper {

    public static String capitalized(String string) {
        return Character.toUpperCase(string.charAt(0)) + string.substring(1);
    }

    public static String notCapitalized(String string) {
        return Character.toLowerCase(string.charAt(0)) + string.substring(1);
    }
    
    public static String sansSuffix(String string, String suffix) {
        if (string.endsWith(suffix))
            return string.substring(0, string.length() - suffix.length());
        return string;
    }
}
