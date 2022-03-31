package com.github.wnebyte.jcli.processor;

import java.util.Set;
import java.util.HashSet;
import java.util.function.Supplier;
import java.lang.reflect.Method;
import com.github.wnebyte.jarguments.factory.AbstractArgumentFactoryBuilder;
import com.github.wnebyte.jcli.*;
import com.github.wnebyte.jcli.annotation.Scope;
import com.github.wnebyte.jcli.exception.ConfigException;
import com.github.wnebyte.jcli.exception.IllegalAnnotationException;
import com.github.wnebyte.jcli.util.Annotations;
import com.github.wnebyte.jcli.util.Reflections;
import static com.github.wnebyte.jcli.util.Reflections.*;

public class MethodMapperImpl implements MethodMapper {

    /*
    ###########################
    #          FIELDS         #
    ###########################
    */

    private final InstanceTracker tracker;

    private final AbstractArgumentFactoryBuilder builder;

    private final Set<Class<?>> classes;

    /*
    ###########################
    #       CONSTRUCTORS      #
    ###########################
    */

    public MethodMapperImpl(InstanceTracker tracker, AbstractArgumentFactoryBuilder builder) {
        this.tracker = tracker;
        this.builder = builder;
        this.classes = new HashSet<>();
    }

    /*
    ###########################
    #          METHODS        #
    ###########################
    */

    /**
     * Constructs a new instance of <code>Command</code> from the specified <code>Method</code>.
     * @param method a Method.
     * @return a new instance of Command.
     * @throws ConfigException if the specified Method is not annotated
     * with {@link com.github.wnebyte.jcli.annotation.Command}, or if the Method's declaring class is both nested and
     * non-static, or if the Method is non-static and its declaring class could not be instantiated.
     */
    @Override
    public Command apply(Method method) {
        final Class<?> cls = method.getDeclaringClass();
        final Scope scope = Annotations.getScopeOrDefaultValue(cls, Scope.SINGLETON);
        Supplier<Object> supplier;

        if (Annotations.isNotAnnotated(method)) {
            throw new IllegalAnnotationException(
                    String.format(
                            "Method: %s is not annotated with com.github.wnebyte.jcli.annotation.Command.", method
                    )
            );
        }
        if (isNested(cls) && !isStatic(cls) && !isStatic(method)) {
            throw new ConfigException(
                    String.format(
                            "Non-static, Command annotated Method: %s is declared inside a nested class that is not " +
                                    "declared as static.", method
                    )
            );
        }
        if (Reflections.isStatic(method)) {
            supplier = () -> null;
        }
        else if (scope == Scope.SINGLETON) {
            // the same object will be used for all subsequent invocations.
            try {
                Object object = tracker.get(cls);
                supplier = () -> object;
            } catch (ReflectiveOperationException e) {
                throw new ConfigException(
                        e.getMessage()
                );
            }
        }
        else {
            // scope is Scope.TRANSIENT
            if (!classes.contains(cls)) {
                try {
                    // dry run to make sure class can be instantiated at 'runtime'.
                    Object junk = tracker.newInstance(cls);
                    classes.add(cls);
                    junk = null;
                } catch (ReflectiveOperationException e) {
                    throw new ConfigException(
                            e.getMessage()
                    );
                }
            }
            supplier = () -> {
                try {
                    return tracker.newInstance(cls);
                } catch (ReflectiveOperationException e) {
                    // should not be thrown seeing as the previous dry run was successful.
                    throw new ConfigException(
                            e.getMessage()
                    );
                }
            };
        }

        return new Command(supplier, method, builder.build());
    }

}