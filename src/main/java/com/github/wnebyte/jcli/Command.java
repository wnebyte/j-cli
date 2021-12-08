package com.github.wnebyte.jcli;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.function.Supplier;
import com.github.wnebyte.jarguments.Argument;
import com.github.wnebyte.jarguments.converter.TypeConverter;
import com.github.wnebyte.jarguments.exception.ParseException;
import com.github.wnebyte.jarguments.factory.AbstractArgumentCollectionFactory;
import com.github.wnebyte.jcli.exception.IllegalAnnotationException;
import com.github.wnebyte.jcli.parser.BaseCommandParser;
import com.github.wnebyte.jcli.util.Annotations;
import static com.github.wnebyte.jarguments.util.Objects.requireNonNullElseGet;

public class Command extends BaseCommand {

    private final Supplier<Object> objectSupplier;

    private final Method method;

    public Command(
            Supplier<Object> objectSupplier,
            Method method,
            AbstractArgumentCollectionFactory factory
    ) {
        super(resolvePrefix(method), resolveNames(method), resolveDescription(method), resolveArgs(method, factory));
        this.objectSupplier = objectSupplier;
        this.method = method;
    }

    private static String resolvePrefix(Method method) {
        String prefix = Annotations.getName(method.getDeclaringClass());
        return requireNonNullElseGet(prefix, () -> "");
    }

    private static Set<String> resolveNames(Method method) {
        Set<String> names = Annotations.getNames(method);
        assert names != null;
        return normalize(names);
    }

    private static String resolveDescription(Method method) {
        String desc = Annotations.getDescription(method);
        return requireNonNullElseGet(desc, () -> "");
    }

    private static Set<String> normalize(Set<String> names) {
        return names;
    }

    private static List<Argument> resolveArgs(Method method, AbstractArgumentCollectionFactory factory) {
        Parameter[] params = method.getParameters();

        try {
            for (Parameter param : params) {
                Class<? extends Argument> sClass = Annotations.getSubClass(param);
                if (sClass != null) {
                    factory.setSubClass(sClass);
                }
                String[] names = Annotations.getNames(param);
                if (names != null) {
                    factory.setNames(names);
                }
                String desc = Annotations.getDescription(param);
                if (desc != null) {
                    factory.setDescription(requireNonNullElseGet(desc, () -> ""));
                }
                factory.setType(param.getType());
                TypeConverter<?> converter = Annotations.getTypeConverter(param);
                if (converter != null) {
                    factory.setTypeConverter(converter);
                }
                String defaultValue = Annotations.getDefaultValue(param);
                if (defaultValue != null) {
                    factory.setDescription(defaultValue);
                }
                factory.append();
            }
        } catch (Exception e) {
            throw new IllegalAnnotationException(
                    e.getMessage()
            );
        }

        return factory.get();
    }

    public void run(String input) throws ParseException {
        Object[] args = new BaseCommandParser(this).parse(input);

        try {
            method.invoke(objectSupplier.get(), args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Method getMethod() {
        return method;
    }

    public Class<?> getDeclaringClass() {
        return method.getDeclaringClass();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null)
            return false;
        if (!(o instanceof Command))
            return false;
        Command cmd = (Command) o;
        return Objects.equals(cmd.objectSupplier, this.objectSupplier) &&
                Objects.equals(cmd.method, this.method) &&
                super.equals(cmd);
    }

    @Override
    public int hashCode() {
        int result = 55;
        return 5 * result +
                Objects.hashCode(objectSupplier) +
                Objects.hashCode(method);
    }
}