package com.github.wnebyte.jcli;

import java.util.*;
import java.util.function.Supplier;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import com.github.wnebyte.jarguments.Argument;
import com.github.wnebyte.jarguments.util.AbstractArgumentFactory;
import com.github.wnebyte.jarguments.util.Strings;
import com.github.wnebyte.jcli.exception.IllegalAnnotationException;
import com.github.wnebyte.jcli.util.Annotations;
import static com.github.wnebyte.jarguments.util.Objects.requireNonNullElseGet;

/**
 * This class represents a Command mapped directly from the properties of a Java Method.
 */
public class Command extends AbstractCommand {

    /*
    ###########################
    #          FIELDS         #
    ###########################
    */

    private final Supplier<Object> supplier;

    private final Method method;

    /*
    ###########################
    #       CONSTRUCTORS      #
    ###########################
    */

    public Command(
            Supplier<Object> supplier,
            Method method,
            AbstractArgumentFactory factory
    ) {
        super(resolvePrefix(method, factory), resolveNames(method, factory),
                resolveDesc(method), resolveArgs(method, factory));
        this.supplier = supplier;
        this.method = method;
        this.method.setAccessible(true);
    }

    /*
    ###########################
    #     UTILITY METHODS     #
    ###########################
    */

    private static String resolvePrefix(Method method, AbstractArgumentFactory factory) {
        String prefix = Annotations.getName(method.getDeclaringClass());
        return Strings.removeAll(requireNonNullElseGet(prefix, () -> Strings.EMPTY), factory.getExcludeCharacters());
    }

    private static Set<String> resolveNames(Method method, AbstractArgumentFactory factory) {
        Set<String> names = Annotations.getNames(method);
        assert (names != null);
        return normalize(names, factory.getExcludeCharacters());
    }

    private static String resolveDesc(Method method) {
        String description = Annotations.getDescription(method);
        return requireNonNullElseGet(description, () -> Strings.EMPTY);
    }

    private static Set<String> normalize(Set<String> names, Collection<Character> exclude) {
        Set<String> set = new LinkedHashSet<>(names.size());

        for (String name : names) {
            name = Strings.removeAll(name, exclude);
            if (name.equals(Strings.EMPTY)) {
                throw new IllegalAnnotationException(
                        String.format(
                                "The name of a Command may not be left empty after normalization. " +
                                        "The following characters are configured to be removed during " +
                                        "normalization: '%s'.", Arrays.toString(exclude.toArray())
                        )
                );
            }
            set.add(name);
        }

        return set;
    }

    private static Set<Argument> resolveArgs(Method method, AbstractArgumentFactory factory) {
        Parameter[] params = method.getParameters();

        try {
            for (Parameter param : params) {
                factory.create(
                        Annotations.getNamesAsString(param),
                        Annotations.getDescription(param),
                        Annotations.isRequired(param),
                        Annotations.getChoices(param),
                        Annotations.getMetavar(param),
                        Annotations.getDefaultValue(param),
                        param.getType()

                );
            }
        } catch (Exception e) {
            throw new IllegalAnnotationException(
                    e.getMessage()
            );
        }

        return factory.getAll();
    }

    /*
    ###########################
    #         METHODS         #
    ###########################
    */

    /**
     * Executes this <code>Command</code> by invoking its underlying Java Method with the specified
     * <code>args</code>.
     * @param args to be passed to the underlying Java Method.
     */
    @Override
    final void execute(Object[] args) {
        Object object = supplier.get();

        try {
            method.invoke(object, args);
        }
        catch (Exception e) {
            System.err.printf("(Error): Method Threw an Exception: '%s'%n", e.getCause().getClass());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o == this)
            return true;
        if (!(o instanceof Command))
            return false;
        Command cmd = (Command) o;
        return Objects.equals(cmd.supplier, this.supplier) &&
                Objects.equals(cmd.method, this.method) &&
                super.equals(cmd);
    }

    @Override
    public int hashCode() {
        int result = 55;
        return 5 * result +
                Objects.hashCode(supplier) +
                Objects.hashCode(method);
    }
}