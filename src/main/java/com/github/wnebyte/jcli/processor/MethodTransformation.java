package com.github.wnebyte.jcli.processor;

import java.lang.reflect.Method;
import java.util.function.Supplier;

import com.github.wnebyte.jarguments.factory.AbstractArgumentCollectionFactoryBuilder;
import com.github.wnebyte.jcli.*;
import com.github.wnebyte.jcli.util.Annotations;
import com.github.wnebyte.jcli.util.Reflections;
import com.github.wnebyte.jshell.exception.config.ConfigException;
import static com.github.wnebyte.jshell.util.ReflectionUtil.*;

public class MethodTransformation implements ElementTransformation<Method, Command> {

    private final InstanceTracker tracker;

    private final AbstractArgumentCollectionFactoryBuilder builder;

    public MethodTransformation(InstanceTracker tracker, AbstractArgumentCollectionFactoryBuilder builder) {
        this.tracker = tracker;
        this.builder = builder;
    }

    @Override
    public Command apply(Method method) {
        Class<?> cls = method.getDeclaringClass();
        Supplier<Object> objectSupplier;

        if ((isNested(cls)) && (isNotStatic(cls))) {
            try {
                throw new ConfigException(
                        "\n\tA Command may not be declared within a nested class that is not declared static."
                );
            } catch (ConfigException e) {
                e.printStackTrace();
                return null;
            }
        }
        if (Reflections.isStatic(cls)) {
            objectSupplier = () -> null;
        }
        else if (Annotations.isSingleton(cls)) {
            Object object = tracker.get(cls);
            objectSupplier = () -> object;
        }
        else {
            objectSupplier = () -> tracker.newInstance(cls);
        }
        return new Command(objectSupplier, method, builder.build());
    }
}