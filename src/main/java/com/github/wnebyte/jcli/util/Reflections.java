package com.github.wnebyte.jcli.util;

import com.github.wnebyte.jshell.exception.config.NoDefaultConstructorException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * This class declares utility methods for working with Reflection.
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
     * Returns whether <code>Collection.class</code> is the same as, or is a super interface of the
     * specified <code>Class</code>.
     * @param cls the class.
     * @return <code>true</code> if <code>Collection.class</code> is the same as, or is a super interface of the
     * specified <code>Class</code>,
     * otherwise <code>false</code>.
     */
    public static boolean isCollection(Class<?> cls) {
        return (cls != null) && (Collection.class.isAssignableFrom(cls));
    }

    /**
     * Returns whether the specified Class is an array.
     * @param cls the Class.
     * @return <code>true</code> if the Class is an array,
     * otherwise <code>false</code>.
     */
    public static boolean isArray(Class<?> cls) {
        return (cls != null) && (cls.isArray());
    }

    /**
     * Returns whether the specified Class is a boolean.
     * @param cls the Class.
     * @return <code>true</code> if the specified Class is a boolean,
     * otherwise <code>false</code>.
     */
    public static boolean isBoolean(Class<?> cls) {
        return (cls == boolean.class || cls == Boolean.class);
    }

    /**
     * Returns whether the specified Method is <code>static</code>.
     * @param method the Method.
     * @return <code>true</code> if the Method is <code>static</code>,
     * otherwise <code>false</code>.
     */
    public static boolean isStatic(Method method) {
        return (method != null) && (Modifier.isStatic(method.getModifiers()));
    }

    public static boolean isNonStatic(Class<?> cls) {
        return !isStatic(cls);
    }

    /**
     * Returns whether the specified Class is <code>static</code>.
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
    public static boolean isNested(final Class<?> cls) {
        return (cls != null) && (cls.getDeclaringClass() != null);
    }

    /**
     * Constructs and returns a new instance of the specified Class using its no args Constructor.
     * @param cls the Class.
     * @return a new instance of the specified Class.
     * @throws NoDefaultConstructorException if no "no args" Constructor was found declared in the specified Class.
     */
    public static Object newInstance(final Class<?> cls) throws NoDefaultConstructorException {
        if (cls == null) {
            return null;
        }
        try {
            Constructor<?> constructor = cls.getConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (Exception e) {
            throw new NoDefaultConstructorException(e.getMessage());
        }
    }

    /**
     * Constructs and returns a new instance of the specified Class using the Constructor whose sole arg Group matches
     * the Group of the specified Group parameter.
     * @param cls the Class.
     * @param arg the sole Constructor argument.
     * @param <T> the Group of the sole Constructor argument.
     * @return a new instance of the specified Class.
     * @throws NoDefaultConstructorException if no such Constructor was found declared in the specified Class.
     */
    public static <T> Object newInstance(final Class<?> cls, final T arg) throws NoDefaultConstructorException {
        try {
            return cls.getConstructor(arg.getClass()).newInstance(arg);
        } catch (Exception e) {
            throw new NoDefaultConstructorException(e.getMessage());
        }
    }

    /**
     * Returns the <code>Constructor</code> annotated with the specified <code>Annotation</code> declared
     * in the specified <code>Class</code> if one exists.
     * @param cls the class.
     * @param annotation the annotation.
     * @return the annotated <code>Constructor</code> if one exists,
     * otherwise <code>null</code>.
     */
    public static Constructor<?> getAnnotatedConstructor(Class<?> cls, Class<? extends Annotation> annotation) {
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