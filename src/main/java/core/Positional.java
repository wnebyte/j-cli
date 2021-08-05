package core;

import exception.config.NoSuchTypeConverterException;
import exception.runtime.ParseException;
import util.ArgumentSplitter;

import java.lang.reflect.Parameter;

/**
 * This class represents a positional Argument mapped directly from the contents of Java Method's Parameter.
 * @see annotation.Argument
 */
public final class Positional extends Argument {

    public static final String SYMBOL = "*";

    private final int position;

    /**
     * Constructs a new <code>Positional</code>, using the specified parameter, and with the specified index and
     * position.
     * @param parameter the parameter to be used when constructing a new instance.
     * @param index the index of the specified parameter.
     * @param position the position of the Argument, relative to any other positional Arguments.
     * @throws NoSuchTypeConverterException if there is no registered <code>TypeConverter</code> matching
     * the type of the specified parameter, and no <code>TypeConverter</code> is specified through the
     * parameters {@link annotation.Argument} annotation.
     */
    Positional(Parameter parameter, int index, int position) throws NoSuchTypeConverterException {
        super(parameter, index, SYMBOL);
        setRegex(getTypeConverter().isArray() ? ARRAY_REGEX : DEFAULT_REGEX);
        this.position = position;
    }

    /**
     * {@inheritDoc}
     * @param input the input in the form a value.
     * @return the initialized value as an <code>Object</code>.
     * @throws ParseException if this <code>Argument</code>'s <code>TypeConverter</code> failed to convert
     * the specified input into the desired Type.
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

    public String toString() {
        return getName().concat(getTypeConverter().isArray() ? "[]" : "");
    }
}
