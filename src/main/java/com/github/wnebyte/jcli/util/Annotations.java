package com.github.wnebyte.jcli.util;

import java.util.*;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.annotation.Annotation;
import com.github.wnebyte.jarguments.util.Strings;
import com.github.wnebyte.jcli.annotation.*;

/**
 * This class is a utility class for the classes declared in the
 * {@link com.github.wnebyte.jcli.annotation} package.
 */
public class Annotations {

    /*
    ###########################
    #   CONTROLLER METHODS   #
    ###########################
    */

    public static boolean isAnnotated(Class<?> cls) {
        return (cls != null) && (cls.isAnnotationPresent(Controller.class));
    }

    public static Scope getScope(Class<?> cls) {
        if (isAnnotated(cls)) {
            return cls.getAnnotation(Controller.class).scope();
        }
        return null;
    }

    public static Scope getScopeOrDefaultValue(Class<?> cls, Scope scope) {
        return Objects.requireNonNullElseGet(getScope(cls), () -> scope);
    }

    public static boolean isTransient(Class<?> cls) {
        if (isAnnotated(cls)) {
            return (cls.getAnnotation(Controller.class).scope() == Scope.TRANSIENT);
        }
        return false;
    }

    public static boolean isSingleton(Class<?> cls) {
        if (isAnnotated(cls)) {
            return (cls.getAnnotation(Controller.class).scope() == Scope.SINGLETON);
        }
        return false;
    }

    public static String getName(Class<?> cls) {
        if (isAnnotated(cls)) {
            return cls.getAnnotation(Controller.class).value();
        }
        return null;
    }

    /*
    ###########################
    #     COMMAND METHODS     #
    ###########################
    */

    public static boolean isAnnotated(Method method) {
        return (method != null) && (method.isAnnotationPresent(Command.class));
    }

    public static boolean isNotAnnotated(Method method) {
        return !isAnnotated(method);
    }

    public static Set<String> getNames(Method method) {
        if (isAnnotated(method)) {
            Set<String> names = new LinkedHashSet<>();
            String name = method.getAnnotation(Command.class).value();
            if (name.equals("")) {
                names.add(method.getName().toLowerCase());
            } else {
                String[] array = name.replace(" ", "").split(",");
                Collections.addAll(names, array);
            }
            return names;
        }

        return null;
    }

    public static String getDescription(Method method) {
        if (isAnnotated(method)) {
            return method.getAnnotation(Command.class).description();
        }
        return null;
    }

    /*
    ###########################
    #     ARGUMENT METHODS    #
    ###########################
    */

    public static boolean isAnnotated(Parameter param) {
        return (param != null) && (param.isAnnotationPresent(Argument.class));
    }

    public static boolean isAnnotated(Parameter param, Class<? extends Annotation> cls) {
        return (param != null && param.isAnnotationPresent(cls));
    }

    public static boolean isNotAnnotated(Parameter param) {
        return !isAnnotated(param);
    }

    /*
    public static String[] getNames(Parameter param) {
        if (isAnnotated(param)) {
            Set<String> names = new LinkedHashSet<>();
            String name = param.getAnnotation(Argument.class).value();
            if (name.equals("")) {
                names.add(param.getName().toLowerCase());
            } else {
                String[] array = name.replace(" ", "").split(",");
                Collections.addAll(names, array);
            }
            return names.toArray(new String[0]);
        }

       return null;
    }
     */

    public static String getNamesAsString(Parameter param) {
        if (isAnnotated(param)) {
            String name = param.getAnnotation(Argument.class).value();
            return (name == null || name.equals("")) ? null : name;
        }
        return null;
    }

    public static String getDescription(Parameter param) {
        if (isAnnotated(param)) {
            return param.getAnnotation(Argument.class).description();
        }
        return "";
    }

    public static String getDefaultValue(Parameter param) {
        if (isAnnotated(param)) {
            String defaultValue = param.getAnnotation(Argument.class).defaultValue();
            return defaultValue.equals("") ? null : defaultValue;
        }
        return null;
    }

    public static boolean isRequired(Parameter param) {
        if (isAnnotated(param)) {
            return param.getAnnotation(Argument.class).required();
        }
        return false;
    }

    public static String[] getChoices(Parameter param) {
        if (isAnnotated(param)) {
            String[] array = param.getAnnotation(Argument.class).choices();
            return (array.length == 1 && array[0].equals("")) ? null : array;
        }
        return null;
    }

    public static String getMetavar(Parameter param) {
        if (isAnnotated(param)) {
            String metavar = param.getAnnotation(Argument.class).metavar();
            return Strings.isNullOrEmpty(metavar) ? null : metavar;
        }
        return null;
    }
}
