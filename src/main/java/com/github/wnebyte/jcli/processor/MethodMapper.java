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

public class MethodMapper implements IMethodMapper {

    /*
    ###########################
    #          FIELDS         #
    ###########################
    */

    private final IInstanceTracker tracker;

    private final AbstractArgumentFactoryBuilder builder;

    /**
     * Stores unique hashes of transient controllers that have been successfully instantiated.
     */
    private final Set<Integer> hashes = new HashSet<Integer>();

    /*
    ###########################
    #       CONSTRUCTORS      #
    ###########################
    */

    public MethodMapper(IInstanceTracker tracker, AbstractArgumentFactoryBuilder builder) {
        this.tracker = tracker;
        this.builder = builder;
    }

    /*
    ###########################
    #          METHODS        #
    ###########################
    */

    /**
     * Constructs a new instance of <code>Command</code> from the specified <code>Method</code>.
     * @param method the Method to be mapped.
     * @return a new Command instance.
     * @throws ConfigException if the specified <code>Method</code> is not annotated
     * with {@link com.github.wnebyte.jcli.annotation.Command}, or if its declaring class is both nested and
     * non-static, or if it is non-static and its declaring class could not be instantiated.
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
            if (!hashes.contains(cls.hashCode())) {
                try {
                    // dry run to make sure objects can be successfully instantiated at 'runtime'.
                    Object junk = tracker.newInstance(cls);
                    hashes.add(cls.hashCode());
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