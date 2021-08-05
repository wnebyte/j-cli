package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

public final class StringUtil {

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
                    final List<Character> forbidden = Arrays.asList(
                            ' ', '"', '[', ']'
                    );
                    @Override
                    public String apply(String s) {
                        StringBuilder builder = new StringBuilder();
                        for (char character : s.toCharArray()) {
                            if (!(forbidden.contains(character))) {
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
    public static String genWhitespace(int len) {
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

    /**
     * Used to split userInput.
     */
    public static List<String> splitByWhitespace(final String string) {
        List<String> elements = new ArrayList<>();
        if (string == null) {
            return elements;
        }
        char[] array = string.toCharArray();
        int startIndex = 0;

        for (int i = 0; i < array.length; i++) {
            char character = array[i];
            /*
            if character is a whitespace, and String has an even number of preceding
            quotation characters, and the same number of opening square brackets as closing square brackets.
             */
            if ((character == ' ') && (evenNumberOfPrecedingOccurrences(string, i))) {
                // get substring
                String element = string.substring(startIndex, i);
                // add substring to list
                elements.add(element);
                // set index
                startIndex = i + 1;
            }
        }
        String element = string.substring(startIndex);
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
                    /*
                case '[':
                    secondCounter++;
                    break;
                case ']':
                    secondCounter--;
                    break;
                     */
            }
        }

        return (counter % 2 == 0); // && (secondCounter == 0);
    }

    /**
     * Used to split arrays.
     */
    public static List<String> splitByComma(final String string) {
        List<String> elements = new ArrayList<>();
        if (string == null) {
            return elements;
        }
        char[] array = string.toCharArray();
        int startIndex = 0;

        for (int i = 0; i < array.length; i++) {
            char character = array[i];

            if ((character == ',') &&
                    (evenNumberOfPrecedingQuotationChars(string, i))) {
                String element = string.substring(startIndex, i);
                elements.add(element);
                startIndex = i + 1;
            }
        }
        String element = string.substring(startIndex);
        elements.add(element);
        return elements;
    }

    public static boolean evenNumberOfPrecedingQuotationChars(final String string, final int index) {
        if (string == null) {
            return true;
        }
        char[] array = string.toCharArray();
        int counter = 0;
        for (int i = 0; i <= index; i++) {
            if (array[i] == '"') {
                counter++;
            }
        }
        return counter % 2 == 0;
    }

    public static String
    replaceFirstAndLast(final String value, final char firstCharacter, final char lastCharacter) {
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
