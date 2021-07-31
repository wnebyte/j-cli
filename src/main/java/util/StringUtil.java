package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

public final class StringUtil {

    /*
    public static String normalize(String s, char prefix, boolean add) {
        if ((s != null) && (1 <= s.length())) {

            // iterate over the string
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);

                if (c == prefix) {
                    // if char equals prefix then fetch leading string.
                    var sub = s.substring(0, i);

                    if (!(equals(sub, prefix))) {
                        // if every char of leading string is not identical then remove every occurrence of char.
                        s = remove(s, prefix);
                    }
                }
                else if (!(Character.isLetter(c))) {
                    // if char is not letter then remove char.
                    s = remove(s, c);
                }
            }
        }
        if ((add) && (s != null) && !(s.startsWith(String.valueOf(prefix)))) {
            s = prefix + s;
        }
        return s;
    }
     */

    /**
     * Normalizes the specified <code>String</code> by removing any non leading characters equal to the specified prefix,
     * and any non letters anywhere in the specified <code>String</code>.
     * Also Inserts the specified prefix at the head, if insert is true.
     */
    public static String normalize(String str, char prefix, boolean insert) {
        String regex = "^" + prefix + "+" + "[a-zA-Z]*$";
        Pattern pattern = Pattern.compile(regex);

        return (pattern.matcher(str).matches()) ?
                str :
                str.transform(new Function<String, String>() {
                    @Override
                    public String apply(String s) {
                        StringBuilder builder = new StringBuilder();
                        if (insert) {
                            builder.append(prefix);
                        }
                        for (char c : s.toCharArray()) {
                            if (Character.isLetter(c)) {
                                builder.append(c);
                            }
                        }
                        return builder.toString();
                    }
                });
    }

    /**
     * Returns the specified String, not containing any of the following characters: < ' ', '"', '[', ']' >.
     */
    public static String normalizeName(String name) {
        String regex = "^[^\\s\"\\[\\]]*$";
        Pattern pattern = Pattern.compile(regex);

        return (pattern.matcher(name).matches()) ?
                name :
                name.transform(new Function<String, String>() {
                    final List<Character> characters = Arrays.asList(
                            ' ', '"', '[', ']'
                    );
                    @Override
                    public String apply(String s) {
                        StringBuilder builder = new StringBuilder();
                        for (char character : s.toCharArray()) {
                            if (!(characters.contains(character))) {
                                builder.append(character);
                            }
                        }
                        return builder.toString();
                    }
                });
    }

    /**
     * Generates a <code>String</code> consisting of the specified number of whitespace characters.
     */
    public static String whitespace(int len) {
        char[] chars = new char[len];
        Arrays.fill(chars, ' ');
        return new String(chars);
    }

    /*
    foo "test sentence " hej hej
     */
    public static int numberOfWhitespaces(String str) {
        int val = 0;
        boolean opened = false;

        for (char c : str.toCharArray()) {
            // if white space
            if (c == ' ') {
                if (!opened) {
                    val++;
                }
            }
            if (c == '"') {
                opened = !opened;
            }
        }
        return val;
    }

    public static int indexOfNthWhiteSpace(String str, int nth) {
        char[] array = str.toCharArray();
        int val = 0;
        int index = -1;
        boolean opened = false;
        boolean arrayOpened = false;

        for (int i = 0; i < array.length; i++) {
            char c = array[i];

            if (c == ' ') {
                if (!opened && !arrayOpened) {
                    val++;
                }
            }
            if (c == '"') {
                opened = !opened;
            }
            if (c == '[') {
                arrayOpened = true;
            }
            if (c == ']') {
                arrayOpened = false;
            }
            if (val == nth && !opened && !arrayOpened) {
                index = i + 1;
            }
        }
        return index;
    }

    public static List<String> split(String str) {
        List<String> elements = new ArrayList<>();
        char[] array = str.toCharArray();
        int startIndex = 0;

        for (int i = 0; i < array.length; i++) {
            char character = array[i];

            if ((character == ' ') && (evenNumberOfPrecedingOccurrences(str, i))) {
                String element = str.substring(startIndex, i);
                elements.add(element);
                startIndex = i + 1;
            }
        }

        String element = str.substring(startIndex);
        elements.add(element);
        return elements;
    }

    public static boolean evenNumberOfPrecedingOccurrences(String str, int endIndex) {
        if (str == null) {
            return true;
        }
        char[] array = str.toCharArray();
        int counter = 0;
        int secondCounter = 0;

        for (int i = 0; i <= endIndex; i++) {
            char character = array[i];

            switch (character) {
                case '"':
                    counter++;
                    break;
                case '[':
                    secondCounter++;
                    break;
                case ']':
                    secondCounter--;
                    break;
            }
        }

        return (counter % 2 == 0) && (secondCounter == 0);
    }
}
