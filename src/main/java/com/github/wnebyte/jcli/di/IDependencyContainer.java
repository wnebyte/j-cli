package com.github.wnebyte.jcli.di;

public interface IDependencyContainer {

    <T, R extends T> void registerDependency(Class<T> abs, R impl);

    Object newInstance(Class<?> abs);

    Object newConstructorInjection(Class<?> abs);

    void injectFields(Object object);
}