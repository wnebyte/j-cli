package com.github.wnebyte.jshell.util;

import java.util.function.Supplier;

/**
 * This class declares utility methods for working with Objects.
 */
public final class ObjectUtil {

    /**
     * Returns the specified value if it is non <code>null</code>, otherwise uses the specified
     * supplier to return a value.
     * @param value the value.
     * @param supplier the supplier.
     * @param <T> the Type of the value.
     * @return the value if it is non <code>null</code>, otherwise the value supplied by the supplier.
     */
    public static <T> T requireNonNullElseGet(final T value, final Supplier<T> supplier) {
        return value != null ? value : supplier.get();
    }

    /**
     * Performs an equality check on the specified Objects.
     * @param t1 the first Object.
     * @param t2 the second Object.
     * @param <T> the Type of the Objects.
     * @return <code>t1.equals(t2)</code> if both objects are non <code>null</code>,
     * otherwise returns whether both Objects are <code>null</code>.
     */
    public static <T> boolean equals(final T t1, final T t2) {
        if ((t1 != null) && (t2 != null)) {
            return t1.equals(t2);
        }
        return (t1 == null) && (t2 == null);
    }

    /**
     * Performs {@link Object#hashCode()} on the specified Object if it is non <code>null</code>.
     * @param t the Object.
     * @param <T> the Type of the Object.
     * @return <code>t.hashCode()</code> if the Object is non <code>null</code>,
     * otherwise <code>0</code>.
     */
    public static <T> int hashCode(final T t) {
        if (t == null) {
            return 0;
        }
        return t.hashCode();
    }
}