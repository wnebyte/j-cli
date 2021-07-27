package convert;

import exception.runtime.ParseException;

public interface TypeConverter<T> {

    T convert(String value) throws ParseException;

    T defaultValue();

    boolean isArray();

    String WHITESPACE_REGEX = "\\s";

    String ARRAY_ELEMENT_SEPARATOR = ",";

    String STANDARD_REGEX = WHITESPACE_REGEX + "([^\\s-]*|\"[^\"-]*\")";
    //"[^\\s-]*";
    // "([^\\s-]*|\"[^\"]*\")"

    String ARRAY_REGEX = WHITESPACE_REGEX + "\\[([^\\s-]*|\"[^\"-]*\")*\\]";
    //\[[^\s-]*\]

    static String normalize(String str) {
        if (str != null) {
            return str.replace("[", "")
                    .replace("]", "")
                    .replace("\"", "")
                    .trim();
        }
        return "";
    }
}
