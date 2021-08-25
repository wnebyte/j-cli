package com.github.wnebyte.jshell.util;

import java.util.*;

/**
 * This class declares utility methods for working with Strings.
 */
public final class StringUtil {

    /**
     * Normalized the specified String by removing any occurrences of the following characters: <br>
     * <code>' ', '"', '[', ']', ',', '\n'</code>.<br>
     * @param s to be normalized.
     * @return the normalized String, or the specified String if it is <code>null</code>.
     */
    public static String normalize(final String s) {
        return normalize(s, Arrays.asList(' ', '"', '[', ']', ',', '\n'));
    }

    /**
     * Normalizes the specified String by removing any occurrences of the specified Characters.
     * @param s to be normalized.
     * @param chars the Characters.
     * @return the normalized String, or the specified String if it is <code>null</code> or the
     * specified Characters are <code>null</code>.
     */
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
     * Generates and returns a String of whitespaces of the specified <code>len</code>.
     * @param len the length of the String.
     * @return a new String.
     */
    public static String generateWhitespaces(final int len) {
        char[] chars = new char[len];
        Arrays.fill(chars, ' ');
        return new String(chars);
    }

    /**
     * Splits the specified String on each occurrence of a whitespace character
     * that is preceded by an even number of "\"" characters.
     * @param s to be split.
     * @return the result.
     */
    public static List<String> splitByWhitespace(final String s) {
        return split(s, ' ', '"');
    }

    /**
     * Splits the specified String on each occurrence of "," that is preceded by
     * an even number of "\"" characters.
     * @param s to be split.
     * @return the result.
     */
    public static List<String> splitByComma(final String s) {
        return split(s, ',', '"');
    }

    /**
     * Splits the specified String <code>s</code> on the specified char <code>splitOn</code> when
     * the the substring ending at the index of <code>splitOn</code> has an even number of preceding
     * occurrences of the specified char <code>c</code>.
     * @param s the String.
     * @param splitOn the delimiter to split on.
     * @param c the char that has to occur an even number of times in the substring ending at the
     *          index of <code>splitOn</code> for the split to occur.
     * @return the result.
     */
    public static List<String> split(final String s, final char splitOn, final char c) {
        List<String> elements = new ArrayList<>();
        if (s == null) { return elements; }
        char[] arr = s.toCharArray();
        int start = 0;

        for (int i = 0; i < arr.length; i++) {
            if ((arr[i] == splitOn) && (occurrences(s.substring(start, i), c) % 2 == 0)) {
                elements.add(s.substring(start, i));
                start = i + 1;
            }
        }
        elements.add(s.substring(start));
        return elements;
    }

    /**
     * Determines the number of times the specified char occurs in the specified String.
     * @param s the String
     * @param c the char
     * @return the result.
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
     * Removes the first occurrence of the specified char <code>first</code> and the last
     * occurrence of the specified char <code>last</code> if both are present within the specified
     * String and they are not positioned at the same index.
     * @param s the String.
     * @param first the first char.
     * @param last the second char.
     * @return the result.
     */
    public static String
    removeFirstAndLast(final String s, final char first, final char last) {
        if (s == null) { return null; }
        int firstIndex = s.indexOf(first);
        int lastIndex = s.lastIndexOf(last);

        if ((firstIndex != -1) && (lastIndex != -1) && (firstIndex != lastIndex)) {
            char[] array = new char[s.length() - 2];

            int j = 0;
            for (int i = 0; i < s.length(); i++) {
                if ((i == firstIndex) || (i == lastIndex)) {
                    continue;
                }
                array[j++] = s.charAt(i);
            }
            return new String(array);
        }
        return s;
    }

    /*
    unrelated functions
    public static boolean contains(final String s, final String substring) {
        if ((s == null) || (substring == null)) { return false; }
        return substrings(s).contains(substring);
    }

    public static List<String> substrings(final String s) {
        Set<String> substrings = new HashSet<>();
        if (s == null) { return new ArrayList<>(substrings); }

        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j <= s.length(); j++) {
                substrings.add(s.substring(i, j));
            }
        }
        return new ArrayList<>(substrings);
    }
     */
}