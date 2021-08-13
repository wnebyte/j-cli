package com.github.wnebyte.jshell.util;

import java.util.*;

public final class CollectionUtil {

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
     * Determines whether two <code>Collections</code> intersect.
     */
    public static <T> boolean intersects(final Collection<T> c1, final Collection<T> c2) {
        return intersection(c1, c2).size() != 0;
    }

    /**
     * Determines to which percentage <code>c1</code> intersects with <code>c2</code>.
     * @return the number of intersections in the specified <code>Collections</code>, divided
     * by the average size of the Collections, as a float.
     */
    public static <T> float intersections(final Collection<T> c1, final Collection<T> c2) {
        float value = 0f;
        if ((c1 == null) || (c2 == null) || (c1.isEmpty()) || (c2.isEmpty())) {
            return value;
        }
        for (T t : c2) {
            if ((t != null) && (c1.contains(t))) {
                value++;
            }
        }
        return value / ((c1.size() + c2.size()) / 2.0f);
    }
}
