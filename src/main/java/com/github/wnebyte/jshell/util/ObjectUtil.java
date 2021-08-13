package com.github.wnebyte.jshell.util;

import java.util.function.Supplier;

public class ObjectUtil {

    public static <T> T requireNonNullElseGet(T value, Supplier<T> supplier) {
        return value != null ? value : supplier.get();
    }
}
