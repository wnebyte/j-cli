package com.github.wnebyte.jcli.di;

import com.github.wnebyte.jcli.annotation.Inject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DependencyContainer implements IDependencyContainer {

    /*
    ###########################
    #          FIELDS         #
    ###########################
    */

    private final Map<Class<?>, Object> dependencies;

    /*
    ###########################
    #       CONSTRUCTORS      #
    ###########################
    */

    public DependencyContainer() {
        this.dependencies = new HashMap<>();
    }

    /*
    ###########################
    #     STATIC UTILITIES    #
    ###########################
    */

    private static Constructor<?> getInjectableConstructor(Class<?> cls) {
        if (cls == null)
            return null;
        return Arrays.stream(cls.getDeclaredConstructors())
                .filter(c -> c.isAnnotationPresent(Inject.class))
                .findFirst()
                .orElse(null);
    }

    private static Constructor<?> getNoArgsConstructor(Class<?> cls) {
        if (cls == null)
            return null;
        return Arrays.stream(cls.getDeclaredConstructors())
                .filter(c -> c.getParameterCount() == 0)
                .findFirst()
                .orElse(null);
    }

    /*
    ###########################
    #          METHODS        #
    ###########################
    */
    @Override
    public <T, R extends T> void registerDependency(Class<T> abs, R impl) {
        dependencies.put(abs, impl);
    }

    @Override
    public <T, R extends T> void unregisterDependency(Class<T> abs) {
        dependencies.remove(abs);
    }

    @Override
    public Object newInstance(Class<?> abs) throws ReflectiveOperationException {
        Object object = newConstructorInjection(abs);
        if (object != null) {
            injectFields(object);
        }
        return object;
    }

    @Override
    public Object newConstructorInjection(Class<?> abs) throws InstantiationException {
        Constructor<?> cons = getInjectableConstructor(abs);
        cons = (cons == null) ? getNoArgsConstructor(abs) : cons;

        if (cons == null) {
            throw new InstantiationException(
                    "Class: " + abs + " declares no appropriate constructor, " +
                            "and can therefore not be instantiated."
            );
        }
        else {
            cons.setAccessible(true);
            Object[] args = new Object[cons.getParameterCount()];
            int i = 0;
            for (Class<?> type : cons.getParameterTypes()) {
                Object dependency = dependencies.get(type);
                if (dependency != null) {
                    args[i++] = dependency;
                } else {
                    throw new InstantiationException(
                            String.format(
                                    "No Object of type: '%s' has been registered with this dependency container.%n",
                                    type.getSimpleName()
                            )
                    );
                }
            }
            try {
                return cons.newInstance(args);
            } catch (Exception e) {
                throw new InstantiationException(
                        e.getMessage()
                );
            }
        }
    }

    @Override
    public void injectFields(Object object) throws ReflectiveOperationException {
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
                    throw new ReflectiveOperationException(
                            String.format(
                                    "No Object of type: '%s' has been registered with this dependency container.%n",
                                    type.getSimpleName()
                            )
                    );
                }
            }
        }
    }
}