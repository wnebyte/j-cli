package com.github.wnebyte.jcli.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class DerivedSet<E> {

    private static final int DEFAULT_INITIAL_CAPACITY = 10;

    protected final Set<E> set;

    public DerivedSet() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public DerivedSet(int initialCapacity) {
        this.set = new HashSet<>(initialCapacity);
    }

    protected abstract boolean add(E e);

    protected abstract boolean contains(Object o);

    protected abstract boolean addAll(Collection<? extends E> c);
}
