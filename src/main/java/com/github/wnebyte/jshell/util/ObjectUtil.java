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
}
