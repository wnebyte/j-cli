package com.github.wnebyte.jcli.processor;

import java.lang.reflect.Method;
import java.util.function.Supplier;
import com.github.wnebyte.jarguments.factory.AbstractArgumentCollectionFactoryBuilder;
import com.github.wnebyte.jcli.*;
import com.github.wnebyte.jcli.annotation.Scope;
import com.github.wnebyte.jcli.exception.IllegalAnnotationException;
import com.github.wnebyte.jcli.util.Annotations;
import com.github.wnebyte.jcli.util.Reflections;
import com.github.wnebyte.jcli.exception.ConfigException;
import static com.github.wnebyte.jcli.util.Reflections.isNested;
import static com.github.wnebyte.jcli.util.Reflections.isNonStatic;

public class MethodTransformation implements IMethodTransformation {

    private final IInstanceTracker tracker;

    private final AbstractArgumentCollectionFactoryBuilder builder;

    public MethodTransformation(IInstanceTracker tracker, AbstractArgumentCollectionFactoryBuilder builder) {
        this.tracker = tracker;
        this.builder = builder;
    }

    @Override
    public Command apply(Method method) {
        Class<?> cls = method.getDeclaringClass();
        Scope scope = Annotations.getScopeOrDefaultValue(cls, Scope.SINGLETON);
        Supplier<Object> objectSupplier;

        if (Annotations.isNotAnnotated(method)) {
            throw new IllegalAnnotationException(
                    "Method: " + method + " is not annotated with @Command."
            );
        }

        if ((isNested(cls)) && (isNonStatic(cls))) {
            throw new ConfigException(
                    "\n\tA Command may not be declared within a nested class that is not declared as static."
            );
        }

        if (Reflections.isStatic(cls)) {
            objectSupplier = () -> null;
        }
        else if (scope == Scope.SINGLETON) {
            Object object = tracker.get(cls);
            objectSupplier = () -> object;
        }
        else {
            objectSupplier = () -> tracker.newInstance(cls);
        }

        return new Command(objectSupplier, method, builder.build());
    }
}