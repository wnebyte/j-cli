package com.github.wnebyte.jcli;

import java.util.function.Function;

public interface Formatter<T> extends Function<T, String> {
}
