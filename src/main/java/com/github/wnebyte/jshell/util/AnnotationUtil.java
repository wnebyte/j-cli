package com.github.wnebyte.jshell.util;

import com.github.wnebyte.jshell.TypeConverter;
import com.github.wnebyte.jshell.TypeConverterRepository;
import com.github.wnebyte.jshell.annotation.Argument;
import com.github.wnebyte.jshell.annotation.Command;
import com.github.wnebyte.jshell.annotation.Controller;
import com.github.wnebyte.jshell.annotation.Type;
import com.github.wnebyte.jshell.exception.config.NoDefaultConstructorException;
import com.github.wnebyte.jshell.exception.config.NoSuchTypeConverterException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * This class declares utility-methods for operating on the annotations declared in the
 * {@linkplain com.github.wnebyte.jshell.annotation} package.
 */
public final class AnnotationUtil {

    public static boolean isAnnotated(final Class<?> cls) {
        return (cls != null) && (cls.isAnnotationPresent(Controller.class));
    }

    public static boolean isAnnotated(final Method method) {
        return (method != null) && (method.isAnnotationPresent(Command.class));
    }

    public static boolean isAnnotated(final Parameter parameter) {
        return (parameter != null) && (parameter.isAnnotationPresent(Argument.class));
    }

    public static boolean hasDefaultName(final Parameter parameter) {
        return isAnnotated(parameter) && parameter.getAnnotation(Argument.class).name().equals("");
    }

    public static String getName(final Class<?> cls) {
        if (cls == null) {
            throw new IllegalArgumentException(
                    "Class must not be null."
            );
        }
        if (isAnnotated(cls)) {
            Controller annotation = cls.getAnnotation(Controller.class);

            return hasDefaultName(annotation) ?
                    cls.getSimpleName().toLowerCase(Locale.ROOT) :
                    annotation.name();
        }
        return "";
    }

    public static String getName(final Method method) {
        if (!(isAnnotated(method))) {
            throw new IllegalArgumentException(
                    "Method is not annotated with @Command."
            );
        }
        return hasDefaultName(method) ?
                method.getName().toLowerCase(Locale.ROOT) :
                method.getAnnotation(Command.class).name();

    }

    public static String getName(final Parameter parameter) {
        if (parameter == null) {
            throw new IllegalArgumentException(
                    "Parameter must not be null."
            );
        }
        if (isAnnotated(parameter)) {
            return hasDefaultName(parameter) ?
                    parameter.getName().toLowerCase(Locale.ROOT) :
                    parameter.getAnnotation(Argument.class).name();
        }
        else {
            return parameter.getName().toLowerCase(Locale.ROOT);
        }
    }

    public static boolean hasDefaultName(final Controller controller) {
        return (controller != null) && (controller.name().equals(""));
    }

    public static boolean hasDefaultTypeConverter(final Argument argument) {
        return (argument != null) && (argument.typeConverter() == ObjectTypeConverter.class);
    }

    public static boolean hasTypeConverter(final Parameter parameter) {
        return (isAnnotated(parameter)) &&
                !(parameter.getAnnotation(Argument.class)
                        .typeConverter() == ObjectTypeConverter.class);
    }

    public static boolean hasDefaultName(final Method method) {
        return (isAnnotated(method)) &&
                method.getAnnotation(Command.class).name().equals(Command.DEFAULT_NAME);
    }

    public static boolean hasDefaultName(final Argument argument) {
        return (argument != null) && (argument.name().equals(""));
    }

    public static String getDescription(final Method method) {
        if (method == null) {
            throw new IllegalArgumentException(
                    "Method must not be null."
            );
        }
        return isAnnotated(method) ?
                method.getAnnotation(Command.class).description() :
                Command.DEFAULT_DESCRIPTION;
    }

    public static String getDescription(final Parameter parameter) {
        if (parameter == null) {
            throw new IllegalArgumentException(
                    "Parameter must not be null."
            );
        }
        return isAnnotated(parameter) ?
                parameter.getAnnotation(Argument.class).description() :
                "";
    }

    public static TypeConverter<?> getTypeConverter(final Parameter parameter) throws NoSuchTypeConverterException {
        if (hasTypeConverter(parameter)) {
            Class<?> typeOf = parameter.getAnnotation(Argument.class).typeConverter();
            try {
                TypeConverter<?> typeConverter =
                        (TypeConverter<?>) ReflectionUtil.newInstance(typeOf);
                return typeConverter;
            } catch (NoDefaultConstructorException e) {
                throw new NoSuchTypeConverterException(e.getMessage());
            }
        }
        return TypeConverterRepository.getTypeConverter(parameter.getType());
    }

    public static Type getType(final Parameter parameter) {
        if (parameter == null) {
            throw new IllegalArgumentException(
                    "Parameter must not be null."
            );
        }
        return parameter.isAnnotationPresent(Argument.class) ?
                parameter.getAnnotation(Argument.class).type() : Type.REQUIRED;
    }
}
