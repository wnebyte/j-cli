package com.github.wnebyte.jshell;

import com.github.wnebyte.jshell.exception.config.NoSuchTypeConverterException;
import com.github.wnebyte.jshell.exception.runtime.ParseException;
import com.github.wnebyte.jshell.util.ArgumentSplitter;

import java.lang.reflect.Parameter;

/**
 * This class represents a required Command-Argument mapped directly from the contents of a Java Method's Parameter.
 * @see com.github.wnebyte.jshell.annotation.Argument
 */
public final class Required extends Argument {

    /**
     * Constructs a new instance using the specified parameter and the specified index.
     * @param parameter the parameter to map this instance from.
     * @param index the index of the specified parameter.
     * @throws NoSuchTypeConverterException if there is no registered TypeConverter matching
     * the type of the specified parameter, and no TypeConverter has been specified through the
     * parameters <br/>{@link com.github.wnebyte.jshell.annotation.Argument} annotation.
     */
    Required(final Parameter parameter, final int index) throws NoSuchTypeConverterException {
        super(parameter, index);
        setRegex(WHITESPACE_REGEX.concat(getName()).concat(getTypeConverter().isArray() ?
                ARRAY_REGEX : DEFAULT_REGEX));
    }

    /**
     * {@inheritDoc}
     * @param input key-value pair where the key is the name of this Argument, and the value
     * is the value to be initialized, separated by a whitespace character.
     * @return the initialized Object.
     * @throws ParseException if this Argument's TypeConverter failed to convert the specified input
     * into the desired Group.
     */
    @Override
    Object initialize(final String input) throws ParseException {
        String value = new ArgumentSplitter()
                .setName(getName())
                .setValue(input)
                .split()
                .normalize(getTypeConverter().isArray())
                .get();
        return getTypeConverter().convert(value);
    }

    /**
     * @return a String representation of this Argument.
     */
    @Override
    public String toString() {
        return getName().concat(getTypeConverter().isArray() ? "[...]" : "");
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
        if (!(o instanceof Required)) { return false; }
        Required required = (Required) o;
        return super.equals(required);
    }

    /**
     * @return the hashCode of this Argument.
     */
    @Override
    public int hashCode() {
        int result = 45;
        return result +
                super.hashCode();
    }
}
