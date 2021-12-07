package com.github.wnebyte.jcli;

import java.util.Collection;

public interface IInstanceTracker {

    boolean add(Object o);

    boolean addAll(Collection<Object> c);

    int size();

    Object get(Class<?> aClass);

    Object newInstance(Class<?> aClass);
}
