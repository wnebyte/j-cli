package com.github.wnebyte.jshell;

import com.github.wnebyte.jshell.exception.config.NoSuchTypeConverterException;
import com.github.wnebyte.jshell.exception.runtime.ParseException;
import com.github.wnebyte.jshell.util.ArgumentSplitter;

import java.lang.reflect.Parameter;

/**
 * This class represents a positional Argument mapped directly from the contents of Java Method's Parameter.
 * @see com.github.wnebyte.jshell.annotation.Argument
 */
public final class Positional extends Argument {

    public static final String SYMBOL = "*";

    private final int position;

    /**
     * Constructs a new instance using the specified parameter, index, and position.
     * @param parameter the parameter to map this instance from.
     * @param index the index of the specified parameter.
     * @param position the position relative to any other positional Arguments.
     * @throws NoSuchTypeConverterException if there is no registered TypeConverter matching
     * the type of the specified parameter, and no TypeConverter has been specified through the
     * parameters <br/>{@link com.github.wnebyte.jshell.annotation.Argument} annotation.
     */
    Positional(Parameter parameter, int index, int position) throws NoSuchTypeConverterException {
        super(parameter, index, SYMBOL);
        setRegex(getTypeConverter().isArray() ? ARRAY_REGEX : DEFAULT_REGEX);
        this.position = position;
    }

    /**
     * {@inheritDoc}
     * @param input value.
     * @return the initialized Object.
     * @throws ParseException if this Argument's TypeConverter failed to convert
     * the specified input into the desired Group.
     */
    @Override
    Object initialize(final String input) throws ParseException {
        String value = new ArgumentSplitter()
                .setValue(input)
                .normalize(getTypeConverter().isArray())
                .get();
        return getTypeConverter().convert(value);
    }

    int getPosition() {
        return position;
    }

    /**
     * @return a String representation of this Argument.
     */
    public String toString() {
        return "*".concat(getTypeConverter().isArray() ? "[...]" : "");
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
        if (!(o instanceof Positional)) { return false; }
        Positional positional = (Positional) o;
        return positional.position == this.position &&
                super.equals(positional);
    }

    /**
     * @return the hashCode of this Argument.
     */
    @Override
    public int hashCode() {
        int result = 66;
        return position +
                result +
                super.hashCode();
    }
}
