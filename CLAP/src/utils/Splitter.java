
package utils;

import java.util.Vector;
import java.util.regex.Pattern;

public class Splitter {

    private final static String UPPER_CASE = "(?=[A-Z])";
    private final static Pattern SPECIAL_CHARS = Pattern
            .compile(" |\\n|\\r|\\t|\\.|,|\\(|\\)|\\{|\\}|\\[|\\]|;|=|<|>|'|\""
                    + "|\\+|\\-|\\*|/|\\||!|\\\\|\\@|#|\\$|%|\\?|&|\\^|_|:|\\||~|`|[0-9]+ ");

    public static Vector<String> methodSplitter(String methodName) {

        final String[] splitted = methodName.split(UPPER_CASE);
        final Vector<String> result = new Vector<String>();

        for (final String word : splitted) {

            if (word.length() > 0) {
                result.addElement(word.toLowerCase());
            }
        }
        return result;
    }

    public static Vector<String> variableSplitter(String variableName) {

        if (variableName.equals(variableName.toUpperCase())) {
            variableName = variableName.toLowerCase();
        }

        final String[] splitted = variableName.split("(?=[A-Z])|\\_");
        final Vector<String> result = new Vector<String>();

        for (final String word : splitted) {

            if (word.length() > 0) {
                result.addElement(word.toLowerCase());
            }
        }
        return result;
    }

    public static Vector<String> commentSplitter(final String comments) {

        final String[] splitted = SPECIAL_CHARS.split(comments);
        final Vector<String> result = new Vector<String>();

        for (final String word : splitted) {

            if (word.length() > 0) {
                result.addElement(word.toLowerCase());
            }
        }
        return result;
    }

    public static String toString(final Vector<String> splittedMethod) {

        String result = "";
        for (final String word : splittedMethod) {
            result = result + word + " ";
        }
        if (result.endsWith(" ")) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

}
