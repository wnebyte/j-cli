package com.github.wnebyte.jcli.util;

import java.util.function.Supplier;

public class Objects {

    public static <T> T requireNonNullElseGet(T t, Supplier<T> supplier) {
        return t != null ? t : supplier.get();
    }

    public static boolean nonNull(Object o) {
        return java.util.Objects.nonNull(o);
    }
}