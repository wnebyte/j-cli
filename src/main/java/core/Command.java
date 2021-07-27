package core;

import exception.config.IllegalAnnotationException;
import exception.config.NoSuchTypeConverterException;
import exception.runtime.ParseException;
import util.AnnotationUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static util.ReflectionUtil.isBoolean;
import static util.AnnotationUtil.*;

public final class Command {

    private final Object methodClass;

    private final Method method;

    private final List<Argument> arguments;

    private final String prefix;

    private final String name;

    private final String description;

    public Command(Object methodClass, Method method) throws IllegalAnnotationException, NoSuchTypeConverterException {
        if (!(isAnnotated(method))) {
            throw new IllegalAnnotationException(
                    "\t\nMethod: " + method.toGenericString() + " is not annotated with " +
                            annotation.Command.class.toString()
            );
        }
        this.methodClass = methodClass;
        this.method = method;
        this.prefix = AnnotationUtil.isAnnotated(getDeclaringClass()) ?
                AnnotationUtil.getName(getDeclaringClass()) : "";
        this.name = AnnotationUtil.getName(method);
        this.description = AnnotationUtil.getDescription(method);
        this.arguments = initArguments();
    }

    private List<Argument> initArguments() throws NoSuchTypeConverterException {
        List<Argument> arguments = new ArrayList<>(method.getParameterCount());
        int index = hasPrefix() ? 1 : 0;

        for (Parameter parameter : method.getParameters())
        {
            Class<? extends Argument> type = AnnotationUtil.getType(parameter);

            if ((type == Optional.class) || (isBoolean(parameter.getType()))) {
                arguments.add(new Optional(parameter));
            }
            else if (type == Required.class) {
                arguments.add(new Required(parameter));
            }
            else if (type == Positional.class) {
                arguments.add(new Positional(parameter, index++));
            }
        }
        return arguments;
    }

    protected Object getMethodClass() {
        return methodClass;
    }

    public Class<?> getDeclaringClass() {
        return method.getDeclaringClass();
    }

    protected Method getMethod() {
        return method;
    }

    public List<Argument> getArguments() {
        return arguments;
    }

    public boolean hasPrefix() {
        return (getPrefix() != null) && !(getPrefix().equals(""));
    }

    public String getPrefix() {
        return prefix;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void call(final String input) throws ParseException {
        Object[] args = new Object[getArguments().size()];

        int i = 0;
        for (Argument argument : getArguments()) {
            try {
                args[i++] = argument.initialize(input);
            } catch (ParseException e) {
                e.setCommand(this);
                e.setArgument(argument);
                e.setUserInput(input);
                throw e;
            }
        }
        try {
            getMethod().invoke(getMethodClass(), args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return getPrefix() + getName() + " " +
                getArguments().stream().map(Argument::toString).collect(Collectors.joining(" "));
    }
}
