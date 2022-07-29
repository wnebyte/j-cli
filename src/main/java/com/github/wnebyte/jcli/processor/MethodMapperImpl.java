package com.github.wnebyte.jcli.processor;

import java.util.Set;
import java.util.HashSet;
import java.util.function.Supplier;
import java.lang.reflect.Method;
import com.github.wnebyte.jarguments.adapter.AbstractTypeAdapterRegistry;
import com.github.wnebyte.jarguments.util.ArgumentFactory;
import com.github.wnebyte.jcli.*;
import com.github.wnebyte.jcli.annotation.Scope;
import com.github.wnebyte.jcli.exception.ConfigException;
import com.github.wnebyte.jcli.util.Annotations;
import com.github.wnebyte.jcli.util.Reflections;

public class MethodMapperImpl implements MethodMapper {

    /*
    ###########################
    #          FIELDS         #
    ###########################
    */

    private final InstanceTracker tracker;

    private final AbstractTypeAdapterRegistry adapters;

    private final Set<Class<?>> classes;

    /*
    ###########################
    #       CONSTRUCTORS      #
    ###########################
    */

    public MethodMapperImpl(InstanceTracker tracker, AbstractTypeAdapterRegistry adapters) {
        this.tracker = tracker;
        this.adapters = adapters;
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
        else if (scope == Scope.TRANSIENT) {
            // a new object will be used for each subsequent invocation.
            if (classes.add(cls)) {
                if (!tracker.canInstantiate(cls)) {
                    throw new ConfigException(
                            String.format(
                                    "Class: '%s' could not be instantiated.", cls.getSimpleName()
                            )
                    );
                }
            }
            supplier = () -> {
                try {
                    return tracker.newInstance(cls);
                } catch (ReflectiveOperationException e) {
                    throw new ConfigException(
                            e.getMessage()
                    );
                }
            };
        }
        else {
            throw new IllegalStateException(
                    String.format(
                            "Scope: '%s' is not recognized", scope
                    )
            );
        }

        return new Command(supplier, method, new ArgumentFactory(adapters, null, null));
    }

}