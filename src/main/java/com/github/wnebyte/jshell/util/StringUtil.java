package com.github.wnebyte.jshell.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This class declares utility Methods for operating on Strings.
 */
public final class StringUtil {

    /**
     * Returns the specified <code>String</code> not containing any of the characters:
     * whitespace, double quotation,
     * opening square bracket, closing square bracket, and comma.
     */
    public static String normalize(final String s) {
        String regex = "^[\\s\"\\[\\],]*$";
        Pattern pattern = Pattern.compile(regex);

        if (pattern.matcher(s).matches()) {
            return s;
        }
        else {
            final List<Character> forbidden = Arrays.asList(
                    ' ', '"', '[', ']', ','
            );
            StringBuilder builder = new StringBuilder();
            for (char character : s.toCharArray()) {
                if (!(forbidden.contains(character))) {
                    builder.append(character);
                }
            }
            return builder.toString();
        }
    }

    public static String normalize(final String s, final List<Character> chars) {
        if ((s == null) || (chars == null)) {
            return s;
        }
        StringBuilder builder = new StringBuilder();
        for (char character : s.toCharArray()) {
            if (!(chars.contains(character))) {
                builder.append(character);
            }
        }
        return builder.toString();
    }

    /**
     * Generates a <code>String</code> consisting of the specified number of whitespace characters.
     */
    public static String generateWhitespaces(final int len) {
        char[] chars = new char[len];
        Arrays.fill(chars, ' ');
        return new String(chars);
    }

    /**
     * Splits the specified <code>String</code> on each occurrence of a whitespace character
     * that is preceded by an even number of double quotation characters.
     */
    public static List<String> splitByWhitespace(final String s) {
        return split(s, ' ', '"');
    }

    /**
     * Splits the specified <code>String</code> on each occurrence of a comma character that is preceded by
     * an even number of double quotation characters.
     */
    public static List<String> splitByComma(final String s) {
        return split(s, ',', '"');
    }

    /**
     * Splits the specified String <code>s</code> on the specified char <code>by</code> when
     * the the substring ending at the index of <code>by</code> has an even number of occurrences of the
     * specified char <code>c</code>.
     * @param s the String.
     * @param by the delimiter to split on.
     * @param c the char that has to occur an even number of times in the substring ending at the
     *          index of <code>by</code> for the split to happen.
     * @return the split String.
     */
    public static List<String> split(final String s, final char by, final char c) {
        List<String> elements = new ArrayList<>();
        if (s == null) { return elements; }
        char[] arr = s.toCharArray();
        int start = 0;

        for (int i = 0; i < arr.length; i++) {
            if ((arr[i] == by) && (occurrences(s.substring(start, i), c) % 2 == 0)) {
                elements.add(s.substring(start, i));
                start = i + 1;
            }
        }
        elements.add(s.substring(start));
        return elements;
    }

    /**
     * Determines how many times the specified <code>char</code> occurs within the specified <code>String</code>.
     * @param s the <code>String</code>.
     * @param c the <code>char</code>.
     * @return the number of occurrences of the specified <code>char</code> within the specified
     * <code>String</code>.
     */
    public static int occurrences(final String s, final char c) {
        int count = 0;
        if (s == null) { return count; }
        for (char character : s.toCharArray()) {
            if (character == c) {
                count++;
            }
        }
        return count;
    }

    /**
     * Removes the first occurrence of the specified <code>firstCharacter</code>, and the last
     * occurrence of the specified <code>lastCharacter</code> if both are present within the specified
     * <code>value</code>.
     */
    public static String
    removeFirstAndLast(final String value, final char firstCharacter, final char lastCharacter) {
        if (value == null) {
            return null;
        }
        int firstIndex = value.indexOf(firstCharacter);
        int lastIndex = value.lastIndexOf(lastCharacter);

        if ((firstIndex != -1) && (lastIndex != -1) && (firstIndex != lastIndex)) {
            char[] array = new char[value.length() - 2];

            int j = 0;
            for (int i = 0; i < value.length(); i++) {
                if ((i == firstIndex) || (i == lastIndex)) {
                    continue;
                }
                array[j++] = value.charAt(i);
            }
            return new String(array);
        }
        return value;
    }
}