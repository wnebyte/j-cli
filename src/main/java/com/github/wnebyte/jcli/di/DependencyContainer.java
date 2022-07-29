package com.github.wnebyte.jcli.di;

import java.util.*;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;
import java.lang.annotation.Annotation;
import com.github.wnebyte.jcli.annotation.Inject;
import com.github.wnebyte.jcli.util.Reflections;
import static com.github.wnebyte.jcli.util.Reflections.getNoArgsConstructor;
import static com.github.wnebyte.jcli.util.Reflections.getFirstAnnotatedConstructor;

public class DependencyContainer implements IDependencyContainer {

    /*
    ###########################
    #       STATIC FIELDS     #
    ###########################
    */

    public static final Class<? extends Annotation> DEFAULT_ANNOTATION = Inject.class;

    /*
    ###########################
    #          FIELDS         #
    ###########################
    */

    private final Map<Class<?>, Object> dependencies;

    private final Class<? extends Annotation> annotation;

    /*
    ###########################
    #       CONSTRUCTORS      #
    ###########################
    */

    public DependencyContainer() {
        this(DEFAULT_ANNOTATION);
    }

    public DependencyContainer(Class<? extends Annotation> annotation) {
        this.dependencies = new HashMap<>();
        this.annotation = annotation;
    }

    /*
    ###########################
    #          METHODS        #
    ###########################
    */

    @Override
    public <T, R extends T> void register(Class<T> base, R impl) {
        if (base != null && impl != null) {
            dependencies.put(base, impl);
        }
    }

    @Override
    public void unregister(Class<?> base) {
        if (base != null) {
            dependencies.remove(base);
        }
    }

    @Override
    public Object newInstance(Class<?> cls) throws ReflectiveOperationException {
        Object object = newConstructorInjection(cls);
        if (object != null) {
            injectFields(object);
        }
        return object;
    }

    @Override
    public Object newConstructorInjection(Class<?> cls) throws ReflectiveOperationException {
        Constructor<?> cons = getFirstAnnotatedConstructor(cls, annotation);
        cons = (cons == null) ? getNoArgsConstructor(cls) : cons;

        if (cons == null) {
            throw new InstantiationException(
                    "Class: '" + cls + "' does not declare an appropriate constructor, " +
                            "and can therefore not be instantiated."
            );
        }
        else {
            cons.setAccessible(true); // Todo: reset accessibility flag for reflective member
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

            return cons.newInstance(args);
        }
    }

    @Override
    public void injectFields(Object obj) throws ReflectiveOperationException {
        Class<?> cls = obj.getClass();

        for (Field field : cls.getDeclaredFields()) {
            field.setAccessible(true); // Todo: reset accessibility flag for reflective member
            Class<?> type = field.getType();

            if (field.isAnnotationPresent(annotation)) {
                Object dependency = dependencies.get(type);

                if (dependency != null) {
                    field.set(obj, dependency);
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

    @Override
    public boolean canInstantiate(Class<?> cls) {
        // constructor can be used
        Constructor<?> cons = getFirstAnnotatedConstructor(cls, annotation);
        cons = (cons == null) ? getNoArgsConstructor(cls) : cons;

        if (cons == null) {
            return false;
        }
        else {
            cons.setAccessible(true);
            for (Class<?> type : cons.getParameterTypes()) {
                Object dependency = dependencies.get(type);
                if (dependency == null) {
                    return false;
                }
            }
        }

        // fields can be used
        for (Field field : cls.getDeclaredFields()) {
            field.setAccessible(true);
            Class<?> type = field.getType();

            if (field.isAnnotationPresent(annotation)) {
                Object dependency = dependencies.get(type);

                if (dependency == null) {
                    return false;
                }
            }
        }

        return true;
    }
}