package com.github.wnebyte.jshell.util;

import java.util.*;

/**
 * This class declares utility methods for working with Collections.
 */
public final class CollectionUtil {

    /**
     * Returns the specified array as a Collection.
     * @param array the array.
     * @param <T> the ComponentType of the specified array.
     * @return the specified array as a Collection.
     */
    public static <T> Collection<T> toCollection(final T[] array) {
        return toList(array);
    }

    /**
     * Constructs and returns a new List from the specified array.
     * @param array the array.
     * @param <T> the ComponentType of the specified array.
     * @return the specified array as a List.
     */
    public static <T> List<T> toList(final T[] array) {
        return Arrays.asList(array);
    }

    /**
     * Constructs and returns a new ArrayList from the specified array.
     * @param array the array.
     * @param <T> the ComponentType of the specified array.
     * @return the specified array as an ArrayList.
     */
    public static <T> ArrayList<T> toArrayList(final T[] array) {
        return new ArrayList<T>(toList(array));
    }

    /**
     * Constructs and returns a new LinkedList from the specified array.
     * @param array the array.
     * @param <T> the ComponentType of the specified array.
     * @return the specified array as a LinkedList.
     */
    public static <T> LinkedList<T> toLinkedList(final T[] array) {
        return new LinkedList<T>(toList(array));
    }

    /**
     * Constructs and returns a new Set from the specified array.
     * @param array the array.
     * @param <T> the ComponentType of the specified array.
     * @return the specified array as a Set.
     */
    public static <T> Set<T> toSet(final T[] array) {
        return new HashSet<T>(toList(array));
    }

    /**
     * Returns a new Set containing the intersected elements of the two specified Collections.
     * @param c1 the first Collection.
     * @param c2 the second Collection.
     * @param <T> the ParameterizedType of the Collections.
     * @return a new Set containing the intersected elements of the two specified Collections,
     * or an empty Set if either of the Collections are <code>null</code>.
     */
    public static <T> Set<T> intersection(final Collection<T> c1, final Collection<T> c2) {
        Set<T> intersection = new HashSet<>();
        if ((c1 == null) || (c2 == null)) {
            return intersection;
        }
        Set<T> set1 = new HashSet<>(c1);
        Set<T> set2 = new HashSet<>(c2);

        for (T t : set1) {
            if ((t != null) && (set2.contains(t))) {
                intersection.add(t);
            }
        }
        return intersection;
    }

    /**
     * Determines whether two specified Collections have any intersected elements.
     * @param c1 the first Collection.
     * @param c2 the second Collection.
     * @param <T> the ParameterizedType of the Collections.
     * @return <code>true</code> if the specified Collections have one or more intersected elements,
     * otherwise <code>false</code>.
     */
    public static <T> boolean intersects(final Collection<T> c1, final Collection<T> c2) {
        return intersection(c1, c2).size() != 0;
    }

    /**
     * Determines the percentage of intersected elements within two Collections.
     * @param c1 the first Collection.
     * @param c2 the second Collection.
     * @param <T> the ParameterizedType of the Collections.
     * @return the number of intersected elements divided by the average size of the specified
     * Collections, or 0 if either Collection is <code>null</code> or empty.
     */
    public static <T> float percentage(final Collection<T> c1, final Collection<T> c2) {
        float value = intersection(c1, c2).size();
        return value / ((c1.size() + c2.size()) / 2.0f);
    }
}