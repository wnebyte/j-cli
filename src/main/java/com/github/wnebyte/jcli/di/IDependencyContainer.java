package com.github.wnebyte.jcli.di;

public interface IDependencyContainer {

    boolean canInstantiate(Class<?> cls);

    <T, R extends T> void register(Class<T> base, R impl);

    void unregister(Class<?> base);

    Object newInstance(Class<?> cls) throws ReflectiveOperationException;

    Object newConstructorInjection(Class<?> cls) throws ReflectiveOperationException;

    void injectFields(Object obj) throws ReflectiveOperationException;
}