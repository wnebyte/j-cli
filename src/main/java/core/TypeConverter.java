package core;

import exception.runtime.ParseException;
import util.StringUtil;

import java.util.function.Function;

/**
 * This interface declares operations used for the purpose of converting a <code>String</code> into an
 * arbitrary <code>Type</code>.
 * @param <T> the <code>Type</code> to be converted into.
 */
public interface TypeConverter<T> {

    /**
     * Converts the specified <code>String</code> into an instance of the specified <code>Type</code>.
     * @param value the value to be converted.
     * @return the converted value as an instance of the specified <code>Type</code>.
     * @throws ParseException if the conversion was unsuccessful.
     */
    T convert(final String value) throws ParseException;

    /**
     * @return a default value of the specified <code>Type</code>.
     */
    T defaultValue();

    /**
     * @return whether the specified <code>Type</code> is an <code>Array</code>.
     */
    boolean isArray();

    String WHITESPACE_REGEX = "\\s";

    String DEFAULT_REGEX = WHITESPACE_REGEX + "([^\\s\"]*|\"[^\"]*\")";

    String ARRAY_REGEX = WHITESPACE_REGEX + "\\[([^\\s\"\\[\\]]*|\"[^\"\\[\\]]*\")*\\]";

    /**
     * Normalizes the specified value by removing any occurrences of quotation characters.
     * @param value the value to be normalized.
     * @return the normalized value, or a new <code>String</code> if the specified value was <code>null</code>.
     */
    static String normalize(final String value) {
        if (value != null) {
            return value.replace("\"", "");
        }
        return "";
    }

    /**
     * Normalizes the specified value by removing the first occurrence of [ and the last occurrence of ].<br/>
     * @param value the value to be normalized.
     * @return the normalized value, or a new <code>String</code> if the specified value was <code>null</code>,
     * or the specified <code>String</code> if both characters are not present.
     */
    static String normalizeArray(final String value) {
        if (value != null) {
            return StringUtil.replaceFirstAndLast(value, '[', ']');
        }
        return "";
    }

    /**
     * Splits the specified value after normalizing it using the regex of a comma character
     * that has en even number of preceding quotation characters.
     * @param value the value to be split.
     * @return the split value.
     */
    static String[] arraySplit(final String value) {
        return StringUtil.splitByComma(value).toArray(new String[0]);
    }
}
