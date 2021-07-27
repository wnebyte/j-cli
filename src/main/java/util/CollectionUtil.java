package util;
import java.util.*;

public class CollectionUtil {

    public static <T> Collection<T> toCollection(T[] array) {
        return toList(array);
    }

    public static <T> List<T> toList(T[] array) {
        return Arrays.asList(array);
    }

    public static <T> ArrayList<T> toArrayList(T[] array) {
        return new ArrayList<T>(toList(array));
    }

    public static <T> LinkedList<T> toLinkedList(T[] array) {
        return new LinkedList<T>(toList(array));
    }

    public static <T> Set<T> toSet(T[] array) {
        return new HashSet<T>(toList(array));
    }
}
