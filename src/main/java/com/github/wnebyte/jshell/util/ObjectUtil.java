package com.github.wnebyte.jshell.util;

import java.util.function.Supplier;

/**
 * This class declares utility-methods for operating on Objects.
 */
public final class ObjectUtil {

    /**
     * Returns the specified value if it is non null, otherwise uses the specified
     * supplier to return a value.
     * @param value the value.
     * @param supplier the supplier.
     * @param <T> the <code>Type</code> of the value.
     * @return the value if non null, else a value supplied by the supplier.
     */
    public static <T> T requireNonNullElseGet(final T value, final Supplier<T> supplier) {
        return value != null ? value : supplier.get();
    }

    /**
     * Performs an equality check on the specified objects.
     * @param t1 object.
     * @param t2 object.
     * @param <T> the Type of the objects.
     * @return <code>t1.equals(t2)</code> if both objects are non <code>null</code>,
     * otherwise returns whether both objects are <code>null</code>.
     */
    public static <T> boolean equals(final T t1, final T t2) {
        if ((t1 != null) && (t2 != null)) {
            return t1.equals(t2);
        }
        return (t1 == null) && (t2 == null);
    }

    /**
     * Performs a hashCode operation on the specified object.
     * @param t object.
     * @param <T> the Type of the object.
     * @return <code>0</code> if the specified object is <code>null</code>,
     * otherwise <code>t.hashCode()</code>.
     */
    public static <T> int hashCode(final T t) {
        if (t == null) {
            return 0;
        }
        return t.hashCode();
    }
}
