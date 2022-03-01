package com.github.wnebyte.jcli.di;

public interface IDependencyContainer {

    <T, R extends T> void registerDependency(Class<T> base, R impl);

    void unregisterDependency(Class<?> base);

    Object newInstance(Class<?> cls) throws ReflectiveOperationException;

    Object newConstructorInjection(Class<?> cls) throws ReflectiveOperationException;

    void injectFields(Object obj) throws ReflectiveOperationException;
}