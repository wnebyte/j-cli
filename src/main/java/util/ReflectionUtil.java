package util;

import exception.config.NoDefaultConstructorException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.regex.Pattern;

public final class ReflectionUtil {

    public static boolean isPrimitive(final Class<?> cls) {
        return (cls != null) && (cls.isPrimitive() || new HashSet<>() {
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

    public static boolean isCollection(final Class<?> cls) {
        return (cls != null) && (cls.isAssignableFrom(Collection.class));
    }

    public static boolean isArray(final Class<?> cls) {
        return (cls != null) && (cls.isArray());
    }

    public static boolean isBoolean(final Class<?> cls) {
        return (cls == boolean.class) || (cls == Boolean.class);
    }

    public static boolean isStatic(final Method method) {
        return (method != null) && (Modifier.isStatic(method.getModifiers()));
    }

    public static boolean isStatic(final Class<?> cls) {
        return (cls != null) && (Modifier.isStatic(cls.getModifiers()));
    }

    public static boolean isNested(final Class<?> cls) {
        return (cls != null) && (cls.getDeclaringClass() != null);
    }

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

    public static <T> Object newInstance(Class<?> cls, T arg) throws NoDefaultConstructorException {
        try {
            return cls.getConstructor(arg.getClass()).newInstance(arg);
        } catch (Exception e) {
            throw new NoDefaultConstructorException(e.getMessage());
        }
    }

    /*
    public static Set<Package> getPackages(ClassLoader classLoader) {
        classLoader = Objects.requireNonNullElse(classLoader, ClassLoader.getSystemClassLoader());
        return Set.of(classLoader.getDefinedPackages());
    }

    public static Set<Package> filterPackages(Set<Package> packages, String prefix) {
        Pattern pattern = Pattern.compile(prefix + "(.*|)$");
        Set<Package> filteredPackages = new HashSet<>();

        for (Package pack : packages) {
            if (pattern.matcher(pack.getName()).matches()) {
                filteredPackages.add(pack);
            }
        }

        return filteredPackages;
     }
     */
}
