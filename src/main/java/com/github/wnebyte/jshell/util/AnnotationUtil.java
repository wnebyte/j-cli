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
 * This class declares utility methods for working on the annotations declared in the
 * {@link com.github.wnebyte.jshell.annotation} package.
 */
public final class AnnotationUtil {

    /**
     * Returns whether the specified Class is annotated with the
     * {@link com.github.wnebyte.jshell.annotation.Controller} annotation.
     * @param cls the Class.
     * @return <code>true</code> if the specified Class is annotated,
     * otherwise <code>false</code>.
     */
    public static boolean isAnnotated(final Class<?> cls) {
        return (cls != null) && (cls.isAnnotationPresent(Controller.class));
    }

    /**
     * Returns whether the specified Method is annotated with the
     * {@link com.github.wnebyte.jshell.annotation.Command} annotation.
     * @param method the Method.
     * @return <code>true</code> if the specified Method is annotated,
     * otherwise <code>false</code>.
     */
    public static boolean isAnnotated(final Method method) {
        return (method != null) && (method.isAnnotationPresent(Command.class));
    }

    /**
     * Returns whether the specified Parameter is annotated with the
     * {@link com.github.wnebyte.jshell.annotation.Argument} annotation.
     * @param parameter the Parameter.
     * @return <code>true</code> if the specified Parameter is annotated,
     * otherwise <code>false</code>.
     */
    public static boolean isAnnotated(final Parameter parameter) {
        return (parameter != null) && (parameter.isAnnotationPresent(Argument.class));
    }

    /**
     * Returns whether the specified Parameter is annotated with the
     * {@link Argument} annotation and has a default name.
     * @param parameter the Parameter.
     * @return <code>true</code> if the specified Parameter is annotated and has a
     * default name value,
     * otherwise <code>false</code>.
     */
    public static boolean hasName(final Parameter parameter) {
        return isAnnotated(parameter) &&
                parameter.getAnnotation(Argument.class).name().equals(Argument.DEFAULT_NAME);
    }

    /**
     * Returns whether the specified Method is annotated with the
     * {@link Command} annotation and has a default name.
     * @param method the Method.
     * @return <code>true</code> if the specified Method is annotated and has a default
     * name, otherwise <code>false</code>.
     */
    public static boolean hasName(final Method method) {
        return isAnnotated(method) &&
                method.getAnnotation(Command.class).name().equals(Command.DEFAULT_NAME);
    }

    /**
     * Returns whether the specified Controller is non <code>null</code> and has a
     * default name.
     * @param controller the Controller.
     * @return <code>true</code> if the specified Controller is non <code>null</code> and has
     * a default name, otherwise <code>false</code>.
     */
    private static boolean hasName(final Controller controller) {
        return (controller != null) &&
                (controller.name().equals(Controller.DEFAULT_NAME));
    }

    /**
     * Returns whether the specified Parameter is annotated with the
     * {@link Argument} annotation and has a non default typeConverter.
     * @param parameter the Parameter.
     * @return <code>true</code> if the specified Parameter is annotated and has a non default
     * typeConverter, otherwise <code>false</code>.
     */
    public static boolean hasTypeConverter(final Parameter parameter) {
        return (isAnnotated(parameter)) &&
                !(parameter.getAnnotation(Argument.class)
                        .typeConverter() == ObjectTypeConverter.class);
    }

    /**
     * Returns the {@link Controller#name()} for the specified Class if the annotation is present and
     * the name is non default, otherwise returns <code>cls.getSimpleName().toLowerCase()</code>.
     * @param cls the Class.
     * @return the name.
     */
    public static String getName(final Class<?> cls) {
        if (cls == null) {
            throw new IllegalArgumentException(
                    "Class must not be null."
            );
        }
        if (isAnnotated(cls)) {
            Controller annotation = cls.getAnnotation(Controller.class);
            return hasName(annotation) ?
                    cls.getSimpleName().toLowerCase(Locale.ROOT) :
                    annotation.name();
        }
        return "";
    }

    /**
     * Returns the {@link Command#name()} for the specified Method if the annotation is present and the
     * name is non default, otherwise returns <code>method.getName().toLowerCase()</code>.
     * @param method the Method.
     * @return the name.
     */
    public static String getName(final Method method) {
        if (!(isAnnotated(method))) {
            throw new IllegalArgumentException(
                    "Method is not annotated with @Command."
            );
        }
        return hasName(method) ?
                method.getName().toLowerCase(Locale.ROOT) :
                method.getAnnotation(Command.class).name();

    }

    /**
     * Returns the {@link Argument#name()} for the specified Parameter if the annotation is present and the
     * name is non default, otherwise returns <code>parameter.getName().toLowerCase()</code>.
     * @param parameter the Parameter.
     * @return the name.
     */
    public static String getName(final Parameter parameter) {
        if (parameter == null) {
            throw new IllegalArgumentException(
                    "Parameter must not be null."
            );
        }
        if (isAnnotated(parameter)) {
            return hasName(parameter) ?
                    parameter.getName().toLowerCase(Locale.ROOT) :
                    parameter.getAnnotation(Argument.class).name();
        }
        else {
            return parameter.getName().toLowerCase(Locale.ROOT);
        }
    }

    /**
     * Returns the {@link Command#description()} for the specified Method if the annotation is present.
     * @param method the Method.
     * @return the description if the annotation is present,
     * otherwise an empty String.
     */
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

    /**
     * Returns the {@link Argument#description()} for the specified Parameter if the annotation is present.
     * @param parameter the Parameter.
     * @return the description if the annotation is present,
     * otherwise an empty String.
     */
    public static String getDescription(final Parameter parameter) {
        if (parameter == null) {
            throw new IllegalArgumentException(
                    "Parameter must not be null."
            );
        }
        return isAnnotated(parameter) ?
                parameter.getAnnotation(Argument.class).description() :
                Argument.DEFAULT_DESCRIPTION;
    }

    /**
     * Returns the {@link Argument#type()} for the specified Parameter if the annotation is present.
     * @param parameter the Parameter.
     * @return the type if the annotation is present,
     * otherwise {@link Type#REQUIRED}.
     */
    public static Type getType(final Parameter parameter) {
        if (parameter == null) {
            throw new IllegalArgumentException(
                    "Parameter must not be null."
            );
        }
        return parameter.isAnnotationPresent(Argument.class) ?
                parameter.getAnnotation(Argument.class).type() :
                Type.REQUIRED;
    }

    /**
     * Returns a new instance of the {@link Argument#typeConverter()} for the specified Parameter if the
     * annotation is present and the typeConverter is non default.
     * @param parameter the Parameter.
     * @return a new instance of its typeConverter if the specified Parameter is annotated and has a
     * non default typeConverter,
     * otherwise returns
     * <code>TypeConverterRepository.getTypeConverter(parameter.getType())</code>.
     * @throws NoSuchTypeConverterException if the specified typeConverter could not be
     * instantiated.
     */
    public static TypeConverter<?> getTypeConverter(final Parameter parameter)
            throws NoSuchTypeConverterException {
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
}