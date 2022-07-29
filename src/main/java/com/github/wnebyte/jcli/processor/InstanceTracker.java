package com.github.wnebyte.jcli.processor;

import java.util.Collection;

public interface InstanceTracker {

    boolean add(Object o);

    boolean addAll(Collection<Object> c);

    int size();

    Object get(Class<?> cls) throws ReflectiveOperationException;

    Object newInstance(Class<?> cls) throws ReflectiveOperationException;

    boolean canInstantiate(Class<?> cls);
}
