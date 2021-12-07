package com.github.wnebyte.jcli.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import com.github.wnebyte.jarguments.Optional;
import com.github.wnebyte.jarguments.Positional;
import com.github.wnebyte.jarguments.Required;
import com.github.wnebyte.jarguments.converter.TypeConverter;
import com.github.wnebyte.jarguments.util.Strings;
import com.github.wnebyte.jcli.StubTypeConverter;
import com.github.wnebyte.jcli.annotation.*;

/**
 * This is a utility class for the {@link com.github.wnebyte.jcli.annotation} package.
 */
public class Annotations {

    /*
    ####################################
    #    @CONTROLLER UTILITY METHODS   #
    ####################################
    */

    public static boolean isAnnotated(Class<?> cls) {
        return cls != null && cls.isAnnotationPresent(Controller.class);
    }

    public static Scope getScope(Class<?> cls) {
        if (isAnnotated(cls)) {
            return cls.getAnnotation(Controller.class).value();
        }
        return null;
    }

    public static Scope getScopeOrDefaultValue(Class<?> cls, Scope scope) {
        return Objects.requireNonNullElseGet(getScope(cls), () -> scope);
    }

    public static boolean isTransient(Class<?> cls) {
        if (isAnnotated(cls)) {
            return cls.getAnnotation(Controller.class).value() == Scope.TRANSIENT;
        }
        return false;
    }

    public static boolean isSingleton(Class<?> cls) {
        if (isAnnotated(cls)) {
            return cls.getAnnotation(Controller.class).value() == Scope.SINGLETON;
        }
        return false;
    }

    public static String getName(Class<?> cls) {
        if (isAnnotated(cls)) {
            return cls.getAnnotation(Controller.class).name();
        }
        return null;
    }

    /*
    ####################################
    #      @COMMAND UTILITY METHODS    #
    ####################################
    */

    public static boolean isAnnotated(Method method) {
        return method != null && method.isAnnotationPresent(Command.class);
    }

    public static boolean isNotAnnotated(Method method) {
        return !isAnnotated(method);
    }

    public static Set<String> getNames(Method method) {
        if (isAnnotated(method)) {
            HashSet<String> names = new HashSet<>();
            String name = method.getAnnotation(Command.class).name();
            if (name.equals("")) {
                names.add(method.getName().toLowerCase());
            }
            else {
                String[] arr = name.replace(" ", "").split(",");
                Collections.addAll(names, arr);
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

    /* ------------ @Argument Utility Functions ------------ */

    public static String[] getNames(Parameter param) {
        if (isAnnotated(param)) {
            String name = param.getAnnotation(Argument.class).name();
            if (!Strings.isEmpty(name)) {
                return name.replace(" ", "").split(",");
            }
        }

        return new String[]{param.getName().toLowerCase()};
    }

    public static String getDescription(Parameter param) {
        if (isAnnotated(param)) {
            return param.getAnnotation(Argument.class).description();
        }
        return null;
    }

    public static String getDefaultValue(Parameter param) {
        if (isAnnotated(param)) {
            String defaultValue = param.getAnnotation(Argument.class).defaultValue();
            if (!Strings.isEmpty(defaultValue)) {
                return defaultValue;
            }
        }
        return null;
    }

    public static boolean isAnnotated(Parameter param) {
        return param != null && param.isAnnotationPresent(Argument.class);
    }

    public static Class<?> getType(Parameter param) {
        return param.getType();
    }

    public static TypeConverter<?> getTypeConverter(Parameter param) {
        if (isAnnotated(param)) {
            Class<?> cls = param.getAnnotation(Argument.class).typeConverter();
            if (cls == StubTypeConverter.class) {
                return null;
            } else {
                try {
                    @SuppressWarnings("unchecked")
                    Constructor<TypeConverter<?>> cons = (Constructor<TypeConverter<?>>) cls.getConstructor();
                    return cons.newInstance();
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

        return null;
    }

    public static Class<? extends com.github.wnebyte.jarguments.Argument> getSubClass(Parameter param) {
        if (isAnnotated(param)) {
            Group type = param.getAnnotation(Argument.class).group();
            Class<? extends com.github.wnebyte.jarguments.Argument> sClass = null;

            switch (type) {
                case REQUIRED:
                    sClass = Required.class;
                    break;
                case OPTIONAL:
                    sClass = Optional.class;
                    break;
                case POSITIONAL:
                    sClass = Positional.class;
                    break;
            }

            return sClass;
        }

        return null;
    }
}
