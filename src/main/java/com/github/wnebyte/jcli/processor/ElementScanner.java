package com.github.wnebyte.jcli.processor;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.function.Predicate;

public interface ElementScanner<E> {

    void scanObject(Object object);

    void scanObjects(Set<Object> objects);

    void scanMethod(Method method);

    void scanMethods(Set<Method> methods);

    void scanClass(Class<?> cls);

    void scanClasses(Set<Class<?>> classes);

    void scanUrl(String url);

    void scanUrls(Set<String> urls);

    void removedScannedElementIf(Predicate<E> p);

    Set<E> getScannedElements();
}