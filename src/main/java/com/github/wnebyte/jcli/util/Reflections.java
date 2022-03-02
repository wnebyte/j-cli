package com.github.wnebyte.jcli.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.annotation.Annotation;

/**
 * This class declares utility methods for working with reflective operations.
 */
public final class Reflections {

    private static final List<Class<?>> WRAPPER_CLASSES = Arrays.asList(
            Boolean.class,
            Byte.class,
            Double.class,
            Float.class,
            Integer.class,
            Long.class,
            Short.class,
            String.class
    );

    /**
     * Returns whether the specified <code>Class</code> is a primitive/wrapper <code>Class</code>.
     * @param cls the class.
     * @return <code>true</code> if the specified <code>Class</code> is a primitive/wrapper <code>Class</code>,
     * otherwise <code>false</code>.
     */
    public static boolean isPrimitive(Class<?> cls) {
        return (cls != null) && (cls.isPrimitive() || WRAPPER_CLASSES.contains(cls));
    }

    /**
     * Returns whether the type <code>Collection.class</code> is the same as, or is a super interface of the
     * specified <code>Class</code>.
     * @param cls the class.
     * @return <code>true</code> if the type <code>Collection.class</code> is the same as, or is a super interface of the
     * specified <code>Class</code>,
     * otherwise <code>false</code>.
     */
    public static boolean isCollection(Class<?> cls) {
        return (cls != null) && (Collection.class.isAssignableFrom(cls));
    }

    /**
     * Returns whether the specified <code>Class</code> is an array.
     * @param cls the Class.
     * @return <code>true</code> if the Class is an array,
     * otherwise <code>false</code>.
     */
    public static boolean isArray(Class<?> cls) {
        return (cls != null) && (cls.isArray());
    }

    /**
     * Returns whether the specified <code>Class</code> is a boolean.
     * @param cls the Class.
     * @return <code>true</code> if the Class is a boolean,
     * otherwise <code>false</code>.
     */
    public static boolean isBoolean(Class<?> cls) {
        return (cls == boolean.class || cls == Boolean.class);
    }

    /**
     * Returns whether the specified <code>Method</code> is <code>static</code>.
     * @param method the Method.
     * @return <code>true</code> if the Method is <code>static</code>,
     * otherwise <code>false</code>.
     */
    public static boolean isStatic(Method method) {
        return (method != null) && (Modifier.isStatic(method.getModifiers()));
    }

    /**
     * Returns whether the specified <code>Class</code> is <code>static</code>.
     * @param cls the Class.
     * @return <code>true</code> if the Class is <code>static</code>,
     * otherwise <code>false</code>.
     */
    public static boolean isStatic(Class<?> cls) {
        return (cls != null) && (Modifier.isStatic(cls.getModifiers()));
    }

    /**
     * Returns whether the specified Class is a nested Class.
     * @param cls the Class.
     * @return <code>true</code> if the specified Class has a declaring Class,
     * otherwise <code>false</code>.
     */
    public static boolean isNested(Class<?> cls) {
        return (cls != null) && (cls.getDeclaringClass() != null);
    }

    /**
     * Returns the first <code>Constructor</code> annotated with the specified <code>Annotation</code> declared
     * in the specified <code>Class</code> if one exists.
     * @param cls the class.
     * @param annotation the annotation.
     * @return the first annotated <code>Constructor</code> if one exists,
     * otherwise <code>null</code>.
     */
    public static Constructor<?> getFirstAnnotatedConstructor(Class<?> cls, Class<? extends Annotation> annotation) {
        if (cls == null) {
            return null;
        }
        return Arrays.stream(cls.getDeclaredConstructors())
                .filter(c -> c.isAnnotationPresent(annotation))
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns the no-args <code>Constructor</code> declared in the specified <code>Class</code> if one exists.
     * @param cls the class.
     * @return the no-args <code>Constructor</code> if one exists,
     * otherwise <code>null</code>.
     */
    public static Constructor<?> getNoArgsConstructor(Class<?> cls) {
        if (cls == null) {
            return null;
        }
        return Arrays.stream(cls.getDeclaredConstructors())
                .filter(c -> c.getParameterCount() == 0)
                .findFirst()
                .orElse(null);
    }
}