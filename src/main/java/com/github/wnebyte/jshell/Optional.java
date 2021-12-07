package com.github.wnebyte.jshell;

import com.github.wnebyte.jshell.exception.config.NoSuchTypeConverterException;
import com.github.wnebyte.jshell.exception.runtime.ParseException;
import com.github.wnebyte.jshell.util.ArgumentSplitter;
import java.lang.reflect.Parameter;
import static com.github.wnebyte.jshell.util.ReflectionUtil.isBoolean;

/**
 * This class represents an optional Command-Argument mapped from a Java Method's Parameter.
 * @see com.github.wnebyte.jshell.annotation.Argument
 */
public final class Optional extends Argument {

    /**
     * Constructs a new instance using the specified parameter and index.
     * @param parameter the parameter to map this instance from.
     * @param index the index of the specified parameter.
     * @throws NoSuchTypeConverterException if there is no registered TypeConverter matching
     * the type of the specified parameter, and no TypeConverter has been specified through the
     * parameters <br/>{@link com.github.wnebyte.jshell.annotation.Argument} annotation.
     */
    Optional(final Parameter parameter, final int index) throws NoSuchTypeConverterException {
        super(parameter, index);
        setRegex(isBoolean(parameter.getType()) ?
                        "(\\s".concat(getName()).concat("|)")
                        :
                        getTypeConverter().isArray() ?
                                "(\\s".concat(getName()).concat(ARRAY_REGEX).concat("|)") :
                                "(\\s".concat(getName()).concat(DEFAULT_REGEX).concat("|)"));
    }

    /**
     * {@inheritDoc}
     * @param input the input to initialize, in the form of a key-value pair separated by a whitespace
     * character, where the key is the name of this Argument and the value is the value to be initialized,
     * or just the name of this Argument.
     * @return the initialized value.
     * @throws ParseException if this Argument's TypeConverter failed to convert the specified input
     * into the desired Group.
     */
    Object initialize(final String input) throws ParseException {
        if (input.contains(getName())) {
            if (isBoolean(getType())) {
                return true;
            } else {
                String value = new ArgumentSplitter()
                        .setName(getName())
                        .setValue(input)
                        .split()
                        .normalize(getTypeConverter().isArray())
                        .get();
                return getTypeConverter().convert(value);
            }
        }
        else {
            return getTypeConverter().defaultValue();
        }
    }

    /**
     * @return a String representation of this Argument.
     */
    public String toString() {
        return "(".concat(getName())
                .concat(getTypeConverter().isArray() ? "[...]" : "").concat(")");
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
        if (!(o instanceof Optional)) { return false; }
        return super.equals(o);
    }

    /**
     * @return the hashCode of this Argument.
     */
    @Override
    public int hashCode() {
        int result = 87;
        return result +
                super.hashCode();
    }
}