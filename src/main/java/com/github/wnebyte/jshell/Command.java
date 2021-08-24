package com.github.wnebyte.jshell;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.github.wnebyte.jshell.annotation.Type;
import com.github.wnebyte.jshell.exception.config.IllegalAnnotationException;
import com.github.wnebyte.jshell.exception.config.NoSuchTypeConverterException;
import com.github.wnebyte.jshell.exception.runtime.ParseException;
import com.github.wnebyte.jshell.util.AnnotationUtil;
import com.github.wnebyte.jshell.util.CollectionUtil;
import com.github.wnebyte.jshell.util.ObjectUtil;
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
     * Constructs a new instance using the specified Object and Method.
     * @param owner the Object to which the specified Method belongs to,
     * or <code>null</code> if the Method is static.
     * @param method the Method that is to be mapped to a Command.
     * @throws IllegalAnnotationException if the specified Method is not annotated with <br/>
     * {@link com.github.wnebyte.jshell.annotation.Command}.
     * @throws NoSuchTypeConverterException if the {@link TypeConverterRepository} lacks an implementation
     * for the Type of one of the specified Method's Parameters, <br/>
     * or if one of the Method's Arguments have a specified TypeConverter class which can not be
     * reflectively instantiated.
     */
    Command(final Object owner, final Method method)
            throws IllegalAnnotationException, NoSuchTypeConverterException {
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
     * Constructs and returns this Command's List of Arguments.
     * @return this Command's List of Arguments.
     * @throws NoSuchTypeConverterException if the {@linkplain TypeConverterRepository} lacks an implementation
     * for the Type of one of the underlying Method's Parameters,
     * or if one of the Arguments have a specified TypeConverter class which can not be instantiated.
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
     * Attempts to execute this Command by parsing the specified input and invoking the
     * underlying Java Method.
     * @param input the input received from the user.
     * @throws ParseException if one of this Command's Arguments failed to convert into its
     * desired Type.
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
     * Returns to what degree the specified input matches this Command.<br/>
     * @param input to compare against this Command's signature.
     * @return a result in the range <code>0.0 <= result <= 2.25</code>
     */
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
     * Sets this Command's signature.
     * @param signature to be set.
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
     * @return the declaring class of this Command's underlying Method.
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
     * Returns whether this Command has a prefix.
     * @return <code>true</code> if this Command's prefix is non <code>null</code> and
     * non <code>empty</code>, otherwise <code>false</code>.
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
     * Returns whether this Command has a description.
     * @return <code>true</code> if this Command's description is non <code>null</code> and
     * non <code>empty</code>, otherwise <code>false</code>.
     */
    public boolean hasDescription() {
        return (description != null) && !(description.equals(""));
    }

    /**
     * @return a String representation of this Command.
     */
    @Override
    public String toString() {
        String prefix = hasPrefix() ? getPrefix().concat(" ") : "";
        return prefix + getName() + " " +
                getArguments().stream().map(Argument::toString).collect(Collectors.joining(" "));
    }

    /**
     * Performs an equality check on the specified Object.
     * @param o the Object to perform the equality check on.
     * @return <code>true</code> if the two Objects "equal" one another,
     * otherwise <code>false</code>.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) { return false; }
        if (o == this) { return true; }
        if (!(o instanceof Command)) { return false; }
        Command command = (Command) o;
        return ObjectUtil.equals(command.owner, this.owner) &&
                ObjectUtil.equals(command.method, this.method) &&
                ObjectUtil.equals(command.arguments, this.arguments) &&
                ObjectUtil.equals(command.prefix, this.prefix) &&
                ObjectUtil.equals(command.name, this.name) &&
                ObjectUtil.equals(command.description, this.description) &&
                ObjectUtil.equals(command.signature, this.signature);
    }

    /**
     * @return the hashCode of this Command.
     */
    @Override
    public int hashCode() {
        int result = 98;
        return result +
                ObjectUtil.hashCode(owner) +
                ObjectUtil.hashCode(method) +
                ObjectUtil.hashCode(arguments) +
                ObjectUtil.hashCode(prefix) +
                ObjectUtil.hashCode(name) +
                ObjectUtil.hashCode(description) +
                ObjectUtil.hashCode(signature);
    }
}
