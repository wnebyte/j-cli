package com.github.wnebyte.jcli.util;

import java.util.function.Supplier;

public class Objects {

    public static <T> T requireNonNullElseGet(T value, Supplier<T> supplier) {
        return (value == null) ? supplier.get() : value;
    }

    public static boolean nonNull(Object o) {
        return java.util.Objects.nonNull(o);
    }
}