package com.github.wnebyte.jcli.di;

import com.github.wnebyte.jcli.annotation.Inject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DependencyContainer implements IDependencyContainer {

    private final Map<Class<?>, Object> dependencies;

    public DependencyContainer() {
        this.dependencies = new HashMap<>();
    }

    @Override
    public <T, R extends T> void registerDependency(Class<T> abs, R impl) {
        dependencies.put(abs, impl);
    }

    @Override
    public Object newInstance(Class<?> abs) {
        Object object = newConstructorInjection(abs);
        if (object != null) {
            injectFields(object);
        }
        return object;
    }

    @Override
    public Object newConstructorInjection(Class<?> abs) {
        Constructor<?> cons = Arrays.stream(abs.getDeclaredConstructors())
                .filter(c -> c.isAnnotationPresent(Inject.class))
                .findFirst()
                .orElse(Arrays.stream(abs.getConstructors())
                        .filter(c -> c.getParameterCount() == 0)
                        .findFirst()
                        .orElse(null));

        if (cons != null) {
            cons.setAccessible(true);
            Object[] args = new Object[cons.getParameterCount()];
            int i = 0;
            for (Class<?> argType : cons.getParameterTypes()) {
                Object dependency = dependencies.get(argType);
                if (dependency != null) {
                    args[i++] = dependency;
                } else {
                    throw new IllegalArgumentException(
                            "No object of type: " + argType.getSimpleName() +
                                    " has been registered with this container."
                    );
                }
            }
            try {
                return cons.newInstance(args);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        return null;
    }

    @Override
    public void injectFields(Object object) {
        Class<?> cls = object.getClass();

        for (Field field : cls.getDeclaredFields()) {
            field.setAccessible(true);
            Class<?> type = field.getType();

            if (field.isAnnotationPresent(Inject.class)) {
                Object dependency = dependencies.get(type);

                if (dependency != null) {
                    try {
                        field.set(object, dependency);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.err.printf(
                            "Group: '%s' has not been registered with this dependency container.%n",
                            type.getSimpleName()
                    );
                }
            }
        }
    }

    private static Constructor<?> getInjectableConstructor(Class<?> cls) {
        for (Constructor<?> cons : cls.getDeclaredConstructors()) {
            if (cons.isAnnotationPresent(Inject.class)) {
                return cons;
            }
        }
        return null;
    }

    private static Constructor<?> getNoArgsConstructor(Class<?> cls) {
        for (Constructor<?> cons : cls.getDeclaredConstructors()) {
            if (cons.getParameterCount() == 0) {
                return cons;
            }
        }
        return null;
    }
}
