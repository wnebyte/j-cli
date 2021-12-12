package com.github.wnebyte.jcli.di;

public interface IDependencyContainer {

    <T, R extends T> void registerDependency(Class<T> abs, R impl);

    <T, R extends T> void unregisterDependency(Class<T> abs);

    Object newInstance(Class<?> abs) throws ReflectiveOperationException;

    Object newConstructorInjection(Class<?> abs) throws ReflectiveOperationException;

    void injectFields(Object object) throws ReflectiveOperationException;
}