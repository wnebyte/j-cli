package com.github.wnebyte.jcli;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import com.github.wnebyte.jarguments.Argument;
import com.github.wnebyte.jarguments.converter.TypeConverter;
import com.github.wnebyte.jarguments.exception.ParseException;
import com.github.wnebyte.jarguments.factory.AbstractArgumentCollectionFactory;
import com.github.wnebyte.jarguments.factory.AbstractArgumentCollectionFactoryBuilder;
import com.github.wnebyte.jarguments.factory.ArgumentCollectionFactoryBuilder;
import com.github.wnebyte.jcli.util.Annotations;

public class Command extends BaseCommand {

    private static final AbstractArgumentCollectionFactoryBuilder DEFAULT_FACTORY_BUILDER =
            new ArgumentCollectionFactoryBuilder();

    private final Supplier<Object> objectSupplier;

    private final Method method;

    public Command(Supplier<Object> objectSupplier, Method method) {
        this(objectSupplier, method, DEFAULT_FACTORY_BUILDER.build());
    }

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
        return Annotations.getName(method.getDeclaringClass());
    }

    private static Set<String> resolveNames(Method method) {
        return Annotations.getNames(method);
    }

    private static String resolveDescription(Method method) {
        return Annotations.getDescription(method);
    }

    private static List<Argument> resolveArgs(Method method, AbstractArgumentCollectionFactory factory) {
        Parameter[] params = method.getParameters();

        for (Parameter param : params) {
            Class<? extends Argument> sClass = Annotations.getSubClass(param);
            if (sClass != null) {
                factory.setSubClass(sClass);
            }
            String[] names = Annotations.getNames(param);
            if (names != null) {
                factory.setNames(names);
            }
            String description = Annotations.getDescription(param);
            if (description != null) {
                factory.setDescription(description);
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

    protected Method getMethod() {
        return method;
    }

    public Class<?> getDeclaringClass() {
        return method.getDeclaringClass();
    }
}
