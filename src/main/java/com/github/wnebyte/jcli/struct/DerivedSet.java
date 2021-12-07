package com.github.wnebyte.jcli.struct;

import java.util.Collection;
import java.util.Set;

public abstract class DerivedSet<E> {

    protected Set<E> set;

    protected abstract boolean add(E e);

    protected abstract boolean contains(Object o);

    protected abstract boolean addAll(Collection<? extends E> c);
}
