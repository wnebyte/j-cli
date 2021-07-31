package core;

import exception.config.IllegalAnnotationException;
import exception.config.NoSuchTypeConverterException;
import exception.runtime.ParseException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import util.AnnotationUtil;
import util.StringUtil;

import static util.ReflectionUtil.isBoolean;
import static util.AnnotationUtil.*;

/**
 * Class represents a Command mapped directly from a java Method.
 */
public final class Command {

    /** The Object to which the Method belongs to */
    private final Object owner;

    /** The Method */
    private final Method method;

    /** List of Command Arguments */
    private final List<Argument> arguments;

    /** Command Prefix */
    private final String prefix;

    /** Command Name */
    private final String name;

    /** Command Description */
    private final String description;

    /**
     * Primary Constructor.
     * @param owner the object to which the specified Method belongs to.
     * @param method the Method.
     * @throws IllegalAnnotationException in the event of the specified Method not being annotated with
     * <code>Command</code>.
     * @throws NoSuchTypeConverterException in the event of there not being any registered <code>TypeConverters</code>
     * with one or more of the Parameters belonging to the specified Method.
     * @see annotation.Command
     * @see TypeConverter
     */
    Command(Object owner, Method method) throws IllegalAnnotationException, NoSuchTypeConverterException {
        if (!(isAnnotated(method))) {
            throw new IllegalAnnotationException(
                    "\t\nMethod: " + method.toGenericString() + " is not annotated with " +
                            annotation.Command.class.toString()
            );
        }
        this.owner = owner;
        this.method = method;
        this.prefix = StringUtil.normalizeName(AnnotationUtil.getName(getDeclaringClass()));
        this.name = StringUtil.normalizeName(AnnotationUtil.getName(method));
        this.description = AnnotationUtil.getDescription(method);
        this.arguments = createArguments();
    }

    /**
     * @return a list of Command Arguments.
     * @throws NoSuchTypeConverterException in the event of there not being any registered <code>TypeConverters</code>
     * with one or more of the Parameters belonging to the specified Method.
     */
    private List<Argument> createArguments() throws NoSuchTypeConverterException {
        List<Argument> arguments = new ArrayList<>(method.getParameterCount());
        int position = hasPrefix() ? 1 : 0;

        for (int index = 0; index < method.getParameterCount(); index++) {
            Parameter parameter = method.getParameters()[index];
            Class<? extends Argument> type = AnnotationUtil.getType(parameter);

            if ((type == Optional.class) || (isBoolean(parameter.getType()))) {
                arguments.add(new Optional(parameter, index));
            }
            else if (type == Required.class) {
                arguments.add(new Required(parameter, index));
            }
            else if (type == Positional.class) {
                arguments.add(new Positional(parameter, index, position++));
            }
        }
        return arguments;
    }

    /**
     * Returns the "owner" of <code>this</code> Method.
     */
    protected Object getOwner() {
        return owner;
    }

    /**
     * Returns the declaring class of <code>this</code> Method.
     */
    public Class<?> getDeclaringClass() {
        return method.getDeclaringClass();
    }

    /**
     * Returns the arguments belonging to this class.
     */
    public List<Argument> getArguments() {
        return arguments;
    }

    /**
     * Returns whether this class has a prefix.
     */
    public boolean hasPrefix() {
        return (getPrefix() != null) && !(getPrefix().equals(""));
    }

    /**
     * Returns the prefix of this class.
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Returns the name of this class.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of this class.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the underlying method of this class.
     */
    Method getMethod() {
        return method;
    }

    /*
    void execute(final String input) throws ParseException {
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
            getMethod().invoke(getOwner(), args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * Parses the specified input and invokes the underlying Java Method.
     * @param input the input received from the user.
     */
    void execute(final String input) throws ParseException {
        Parser parser = new Parser(this, input);

        try {
            Object[] args = parser.parse();
            getMethod().invoke(getOwner(), args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a <code>String</code> representation of this class.
     */
    public String toString() {
        String prefix = hasPrefix() ? getPrefix().concat(" ") : "";
        return prefix + getName() + " " +
                getArguments().stream().map(Argument::toString).collect(Collectors.joining(" "));
    }
}
