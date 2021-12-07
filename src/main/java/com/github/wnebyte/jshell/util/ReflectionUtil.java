package com.github.wnebyte.jshell.util;

import com.github.wnebyte.jshell.exception.config.NoDefaultConstructorException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * This class declares utility methods for working with Reflection.
 */
public final class ReflectionUtil {

    /**
     * Returns whether the specified Class is of a primitive/wrapper class Group.
     * @param cls the Class.
     * @return <code>true</code> if the Class is a primitive type or a wrapper class,
     * otherwise <code>false</code>.
     */
    public static boolean isPrimitive(final Class<?> cls) {
        return (cls != null) && (cls.isPrimitive() || new HashSet<Object>() {
            {
                addAll(Arrays.asList
                        (
                                Boolean.class,
                                Byte.class,
                                Double.class,
                                Float.class,
                                Integer.class,
                                Long.class,
                                Short.class,
                                String.class
                        )
                );
            }
        }.contains(cls));
    }

    /**
     * Returns <code>Collection.class.isAssignableFrom(cls)</code>.
     * @param cls the Class.
     * @return <code>Collection.class.isAssignableFrom(cls)</code>.
     */
    public static boolean isCollection(final Class<?> cls) {
        return (cls != null) && (Collection.class.isAssignableFrom(cls));
    }

    /**
     * Returns whether the specified Class is an array.
     * @param cls the Class.
     * @return <code>true</code> if the Class is an array,
     * otherwise <code>false</code>.
     */
    public static boolean isArray(final Class<?> cls) {
        return (cls != null) && (cls.isArray());
    }

    /**
     * Returns whether the specified Class is a boolean.
     * @param cls the Class.
     * @return <code>true</code> if the specified Class is a boolean,
     * otherwise <code>false</code>.
     */
    public static boolean isBoolean(final Class<?> cls) {
        return (cls == boolean.class || cls == Boolean.class);
    }

    /**
     * Returns whether the specified Method is <code>static</code>.
     * @param method the Method.
     * @return <code>true</code> if the Method is <code>static</code>,
     * otherwise <code>false</code>.
     */
    public static boolean isStatic(final Method method) {
        return (method != null) && (Modifier.isStatic(method.getModifiers()));
    }

    public static boolean isNotStatic(final Class<?> cls) {
        return !isStatic(cls);
    }

    /**
     * Returns whether the specified Class is <code>static</code>.
     * @param cls the Class.
     * @return <code>true</code> if the Class is <code>static</code>,
     * otherwise <code>false</code>.
     */
    public static boolean isStatic(final Class<?> cls) {
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
}