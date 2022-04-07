package com.github.wnebyte.jcli.util;

import java.util.Set;
import java.util.HashSet;

public class Sets {

    public static <E> Set<E> emptySet() {
        return new HashSet<E>(0);
    }

    public static boolean intersects(Set<?> set1, Set<?> set2) {
        if (set1 == null || set2 == null) {
            return false;
        }
        for (Object obj : set1) {
            if (set2.contains(obj)) {
                return true;
            }
        }

        return false;
    }

    public static <E> Set<E> intersection(Set<E> set1, Set<E> set2) {
        Set<E> set = new HashSet<>();
        if (set1 == null || set2 == null) {
            return set;
        }
        for (E element : set1) {
            if (set2.contains(element)) {
                set.add(element);
            }
        }

        return set;
    }
}
