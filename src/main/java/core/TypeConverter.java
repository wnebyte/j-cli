package core;

import exception.runtime.ParseException;
import util.StringUtil;

public interface TypeConverter<T> {

    T convert(final String value) throws ParseException;

    T defaultValue();

    boolean isArray();

    String WHITESPACE_REGEX = "\\s";

    String DEFAULT_REGEX = WHITESPACE_REGEX + "([^\\s\"]*|\"[^\"]*\")";

    String ARRAY_REGEX = WHITESPACE_REGEX + "\\[([^\\s\"\\[\\]]*|\"[^\"\\[\\]]*\")*\\]";

    static String normalize(final String value) {
        if (value != null) {
            return value.replace("[", "")
                    .replace("]", "")
                    .replace("\"", "")
                    .trim();
        }
        return "";
    }

    static String[] arraySplit(final String value) {
        return StringUtil.splitByComma(value).toArray(new String[0]);
    }
}
