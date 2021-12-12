package com.github.wnebyte.jcli.processor;

import java.util.Collection;

public interface IInstanceTracker {

    boolean add(Object o);

    boolean addAll(Collection<Object> c);

    int size();

    Object get(Class<?> aClass) throws ReflectiveOperationException;

    Object newInstance(Class<?> aClass) throws ReflectiveOperationException;
}
