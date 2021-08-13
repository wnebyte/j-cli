package com.github.wnebyte.jshell;

import com.github.wnebyte.jshell.annotation.Type;
import com.github.wnebyte.jshell.exception.config.IllegalAnnotationException;
import com.github.wnebyte.jshell.exception.config.NoSuchTypeConverterException;
import com.github.wnebyte.jshell.exception.runtime.ParseException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.github.wnebyte.jshell.util.AnnotationUtil;
import com.github.wnebyte.jshell.util.CollectionUtil;
import com.github.wnebyte.jshell.util.StringUtil;
import static com.github.wnebyte.jshell.util.ReflectionUtil.isBoolean;
import static com.github.wnebyte.jshell.util.AnnotationUtil.*;

/**
 * This class represents a Command mapped directly from a Java Method.
 * @see com.github.wnebyte.jshell.annotation.Command
 */
public final class Command {

    private final Object owner;

    private final Method method;

    private final List<Argument> arguments;

    private final String prefix;

    private final String name;

    private final String description;

    // lateinit
    private Set<List<String>> signature;

    /**
     * Constructs a new Command instance using the specified Object and Method.
     * @param owner the Object to which the specified Method belongs to.<br/>
     *              Is expected to be <code>null</code> if the Method does not belong to an Object.
     * @param method the Method that is to be mapped into a Command.
     * @throws IllegalAnnotationException if the specified Method is not annotated with
     * {@linkplain com.github.wnebyte.jshell.annotation.Command}.
     * @throws NoSuchTypeConverterException if the {@linkplain TypeConverterRepository} lacks an implementation
     * for the Type of one of the specified Method's Parameters,
     * or if one of the Method's Arguments have a specified TypeConverter class which can not be
     * reflectively instantiated.
     */
    Command(Object owner, Method method) throws IllegalAnnotationException, NoSuchTypeConverterException {
        if (!(isAnnotated(method))) {
            throw new IllegalAnnotationException(
                    "\t\nMethod: " + method.toGenericString() + " is not annotated with " +
                            com.github.wnebyte.jshell.annotation.Command.class.toString()
            );
        }
        this.owner = owner;
        this.method = method;
        this.prefix = StringUtil.normalize(AnnotationUtil.getName(getDeclaringClass()));
        this.name = StringUtil.normalize(AnnotationUtil.getName(method));
        this.description = AnnotationUtil.getDescription(method);
        this.arguments = createArguments();
        this.arguments.sort(Argument.getComparator());
    }

    /**
     * Initializes and returns the Arguments of this Command.
     * @return this Command's Arguments.
     * @throws NoSuchTypeConverterException if the {@linkplain TypeConverterRepository} lacks an implementation
     * for the Type of one of the underlying Method's Parameters,
     * or if one of the Arguments have a specified TypeConverter class which can not be instantiated.
     */
    /*
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
     */

    private List<Argument> createArguments() throws NoSuchTypeConverterException {
        List<Argument> arguments = new ArrayList<>(method.getParameterCount());
        Parameter[] parameters = method.getParameters();
        int position = 0;

        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Type type = AnnotationUtil.getType(parameter);

            if ((type == Type.OPTIONAL) || (isBoolean(parameter.getType()))) {
                arguments.add(new Optional(parameter, i));
            }
            else if (type == Type.REQUIRED) {
                arguments.add(new Required(parameter, i));
            }
            else if (type == Type.POSITIONAL) {
                arguments.add(new Positional(parameter, i, position++));
            }
        }
        return arguments;
    }

    /**
     * Attempts to execute this Command by parsing the specified input, and invoking this Command's
     * underlying Method.
     * @param input the input received from the user.
     * @throws ParseException if one of this Command's Arguments failed to convert.
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

    float getLikeness(final String input) {
        List<String> words = Parser.split(input);
        float val = 0.0f;

        if (hasPrefix()) {

            if (1 < words.size() && words.get(0).equals(getPrefix())) {
                val += 0.5f;
            }
            if (2 < words.size() && words.get(1).equals(getName())) {
                val += 0.75f;
            }
        } else {

            if (1 < words.size() && words.get(0).equals(getName())) {
                val += 0.75f;
            }
        }

        val += signature.stream()
                .map(signature -> CollectionUtil.intersections(signature, words))
                .max(Comparator.comparingDouble(value -> value))
                .orElse(0f);
        return val;
    }


    /**
     * Lateinit signature initializer.
     */
    void setSignature(final Set<List<String>> signature) {
        if (signature != null) {
            if (this.signature == null) {
                this.signature = signature;
            } else {
                throw new IllegalStateException(
                        "Signature has already been initialized"
                );
            }
        }
    }

    /**
     * @return the Object to which this Command's underlying Method belongs to.
     */
    Object getOwner() {
        return owner;
    }

    /**
     * @return the underlying Method of this Command.
     */
    Method getMethod() {
        return method;
    }

    /**
     * @return the declaring Class of this Command's underlying Method.
     */
    Class<?> getDeclaringClass() {
        return method.getDeclaringClass();
    }

    /**
     * @return this Command's List of Arguments.
     */
    public List<Argument> getArguments() {
        return arguments;
    }

    /**
     * @return whether this Command has a prefix.
     */
    public boolean hasPrefix() {
        return (getPrefix() != null) && !(getPrefix().equals(""));
    }

    /**
     * @return this Command's prefix.
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * @return the name of this Command.
     */
    public String getName() {
        return name;
    }

    /**
     * @return the description of this Command.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return whether this Command has a description.
     */
    public boolean hasDescription() {
        return (description != null) && !(description.equals(""));
    }

    /**
     * @return a String representation of this Command.
     */
    public String toString() {
        String prefix = hasPrefix() ? getPrefix().concat(" ") : "";
        return prefix + getName() + " " +
                getArguments().stream().map(Argument::toString).collect(Collectors.joining(" "));
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
}
