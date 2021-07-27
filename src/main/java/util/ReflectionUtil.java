package util;

import exception.config.NoDefaultConstructorException;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public final class ReflectionUtil {

    public static boolean isPrimitive(Class<?> type) {
        return (type != null) &&
                (type.isPrimitive() || new HashSet<>() {
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
        }.contains(type));
    }

    public static boolean isCollection(Class<?> type) {
        return (type != null) && (type.isAssignableFrom(Collection.class));
    }

    public static boolean isArray(Class<?> type) {
        return (type != null) && (type.isArray());
    }

    public static boolean isBoolean(Class<?> type) {
        return (type == boolean.class || type == Boolean.class);
    }

    public static boolean isStatic(Method method) {
        return (method != null) && (Modifier.isStatic(method.getModifiers()));
    }

    public static boolean isNested(Class<?> objectClass) {
        return (objectClass != null) && (objectClass.getDeclaringClass() != null);
    }

    public static Object newInstance(Class<?> type) throws NoDefaultConstructorException {
        if (type == null) {
            return null;
        }
        try {
            return Arrays.stream(type.getConstructors()).filter(c -> c.getParameterCount() == 0).findFirst()
                    .orElseThrow(Exception::new).newInstance();
        } catch (Exception e) {
            throw new NoDefaultConstructorException(e.getMessage());
        }
    }

    public static <T> Object newInstance(Class<?> type, T arg) throws NoDefaultConstructorException {
        try {
            return type.getConstructor(arg.getClass()).newInstance(arg);
        } catch (Exception e) {
            throw new NoDefaultConstructorException(e.getMessage());
        }
    }
}
