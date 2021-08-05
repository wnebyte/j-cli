package core;

import exception.config.NoSuchTypeConverterException;
import exception.runtime.ParseException;
import util.ArgumentSplitter;
import util.Splitter;
import java.lang.reflect.Parameter;

/**
 * This class represents a required Command-Argument mapped directly from the contents of a Java Method's Parameter.
 * @see annotation.Argument
 */
public final class Required extends Argument {

    /**
     * Constructs a new <code>Required</code> Argument, using the specified parameter, and with the specified index.
     * @param parameter the parameter to be used when constructing a new instance.
     * @param index the index of the specified parameter.
     * @throws NoSuchTypeConverterException if there is no registered <code>TypeConverter</code> matching
     * the type of the specified parameter, and no <code>TypeConverter</code> is specified through the
     * parameters {@link annotation.Argument} annotation.
     */
    Required(Parameter parameter, int index) throws NoSuchTypeConverterException {
        super(parameter, index);
        setRegex(WHITESPACE_REGEX.concat(getName()).concat(getTypeConverter().isArray() ?
                ARRAY_REGEX : DEFAULT_REGEX));
    }

    /**
     * {@inheritDoc}
     * @param input the key-value pair where the key is the name of this <code>Argument</code>, and the value
     * is the value to be initialized, separated by a whitespace character.
     * @return the initialized value as an <code>Object</code>.
     * @throws ParseException if this <code>Argument</code>'s <code>TypeConverter</code> failed to convert the specified input, or a
     * substring of it, into the desired Type.
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
     * @return a <code>String</code> representation of this Argument.
     */
    public String toString() {
        return getName().concat(getTypeConverter().isArray() ? "[]" : "");
    }
}
