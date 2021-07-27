package util;

import convert.TypeConverter;
import convert.TypeConverterRepository;
import convert.ObjectTypeConverter;
import annotation.Argument;
import annotation.Command;
import annotation.Controller;
import exception.config.NoDefaultConstructorException;
import exception.config.NoSuchTypeConverterException;
import core.Required;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Locale;

public final class AnnotationUtil {

    public static boolean isAnnotated(Class<?> objectClass) {
        return (objectClass != null) && (objectClass.isAnnotationPresent(Controller.class));
    }

    public static boolean isAnnotated(Method method) {
        return (method != null) && (method.isAnnotationPresent(Command.class));
    }

    public static boolean isAnnotated(Parameter parameter) {
        return (parameter != null) && (parameter.isAnnotationPresent(Argument.class));
    }

    public static String getName(Class<?> objectClass) {
        if ((isAnnotated(objectClass)) && !(hasDefaultName(objectClass.getAnnotation(Controller.class)))) {
            return objectClass.getAnnotation(Controller.class).name();
        }
        return objectClass.getSimpleName().toLowerCase(Locale.ROOT).replace("controller", "");
    }

    public static String getName(Method method) {
        if (!(isAnnotated(method))) {
            throw new IllegalArgumentException(
                    "Method is not annotated with @Command."
            );
        }
        return hasDefaultName(method) ?
                method.getName().toLowerCase(Locale.ROOT) :
                method.getAnnotation(Command.class).name();

    }

    public static boolean hasDefaultName(Controller controller) {
        return (controller != null) && (controller.name().equals(""));
    }

    public static boolean hasDefaultTypeConverter(Argument argument) {
        return (argument != null) && (argument.typeConverter() == ObjectTypeConverter.class);
    }

    public static boolean hasDefaultName(Method method) {
        return (isAnnotated(method)) &&
                method.getAnnotation(Command.class).name().equals(Command.DEFAULT_NAME);
    }

    public static boolean hasDefaultName(Argument argument) {
        return (argument != null) && (argument.name().equals(""));
    }

    public static String getName(Parameter parameter) {
        if (parameter == null) {
            throw new IllegalArgumentException(
                    "Parameter must not be null."
            );
        }
        return isAnnotated(parameter) ?
                parameter.getAnnotation(Argument.class).name() :
                parameter.getName().toLowerCase(Locale.ROOT);
    }

    public static String getDescription(Method method) {
        if (method == null) {
            throw new IllegalArgumentException(
                    "Method must not be null."
            );
        }
        return isAnnotated(method) ?
                method.getAnnotation(Command.class).description() :
                Command.DEFAULT_DESCRIPTION;
    }

    public static String getDescription(Parameter parameter) {
        if (parameter == null) {
            throw new IllegalArgumentException(
                    "Parameter must not be null."
            );
        }
        return isAnnotated(parameter) ?
                parameter.getAnnotation(Argument.class).description() :
                "";
    }

    public static TypeConverter<?> getTypeConverter(Parameter parameter) throws NoSuchTypeConverterException {
        if ((isAnnotated(parameter)) && !(hasDefaultTypeConverter(parameter.getAnnotation(Argument.class)))) {
            Class<?> typeOf = parameter.getAnnotation(Argument.class).typeConverter();
            try {
                return (TypeConverter<?>) ReflectionUtil.newInstance(typeOf);
            } catch (NoDefaultConstructorException e) {
                throw new NoSuchTypeConverterException(e.getMessage());
            }
        }

        return TypeConverterRepository.getTypeConverter(parameter.getType());
    }

    public static Class<? extends core.Argument> getType(Parameter parameter) {
        if (parameter == null) {
            throw new IllegalArgumentException(
                    "Parameter must not be null."
            );
        }
        return parameter.isAnnotationPresent(Argument.class) ?
                parameter.getAnnotation(Argument.class).type() : Required.class;
    }
}
