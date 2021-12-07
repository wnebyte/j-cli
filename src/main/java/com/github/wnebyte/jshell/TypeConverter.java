package com.github.wnebyte.jshell;

import com.github.wnebyte.jshell.exception.runtime.ParseException;
import com.github.wnebyte.jshell.util.StringUtil;

/**
 * This interface declares methods for converting a String into an
 * arbitrary Group.
 * @param <T> the Group the String should be converted into.
 */
public interface TypeConverter<T> {

    /**
     * Converts the specified <code>value</code> into a new instance of the Class specified Group.
     * @param value to be converted.
     * @return a new instance of the Class specified Group.
     * @throws ParseException if unsuccessful.
     */
    T convert(final String value) throws ParseException;

    /**
     * @return a default value of the Class specified Group.
     */
    T defaultValue();

    /**
     * @return whether the Class specified Group is an Array.
     */
    boolean isArray();

    /**
     * Normalizes the specified <code>value</code> by removing all occurrences of "\"".
     * @param value to be normalized.
     * @return the normalized <code>value</code>, or a new String if the specified <code>value</code>
     * was <code>null</code>.
     */
    static String normalize(final String value) {
        if (value != null) {
            return value.replace("\"", "");
        }
        return "";
    }

    /**
     * Normalizes the specified <code>value</code> by removing the first occurrence of "["
     * and the last occurrence of "]".<br>
     * @param value to be normalized.
     * @return the normalized <code>value</code>, or a new String if the specified <code>value</code>
     * was <code>null</code>,
     * or the specified <code>value</code> if both characters are not present.
     */
    static String normalizeArray(final String value) {
        if (value != null) {
            return StringUtil.removeFirstAndLast(value, '[', ']');
        }
        return "";
    }

    /**
     * Splits the specified <code>value</code> on occurrences of ","
     * that have an even number of preceding "\"" characters.
     * @param value to be split.
     * @return the split <code>value</code>.
     */
    static String[] arraySplit(final String value) {
        return StringUtil.splitByComma(value).toArray(new String[0]);
    }
}