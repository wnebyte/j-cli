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
     * Constructs a new <code>Optional</code> instance using the specified parameter,
     * and the specified index.
     * @param parameter the Java Parameter from which this Argument is to be mapped.
     * @param index the index of the specified parameter.
     * @throws NoSuchTypeConverterException if there is no registered <code>TypeConverter</code> matching
     * the type of the specified parameter, and no <code>TypeConverter</code> is specified through the
     * parameters {@link com.github.wnebyte.jshell.annotation.Argument} com.github.wnebyte.jshell.annotation.
     */
    Optional(Parameter parameter, int index) throws NoSuchTypeConverterException {
        super(parameter, index);
        setRegex(isBoolean(parameter.getType()) ?
                        "(\\s".concat(getName()).concat("|)")
                        :
                        getTypeConverter().isArray() ?
                                "(\\s".concat(getName()).concat(ARRAY_REGEX).concat("|)") :
                                "(\\s".concat(getName()).concat(DEFAULT_REGEX).concat("|)")
                );
    }

    /**
     * {@inheritDoc}
     * @param input the input to initialize, in the form of a key-value pair separated by a whitespace
     * character, where the key is the name of this Argument, and the value is the value to be initialized.
     * @return the initialized value.
     * @throws ParseException if this Argument's <code>TypeConverter</code> failed to convert the specified input, or a
     * substring of it into the desired Type.
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
     * {@inheritDoc}
     */
    public String toString() {
       // return "(".concat(getName()).concat(getTypeConverter().isArray() ? "[]" : "").concat(")");
        return "(".concat(getName())
                .concat(getTypeConverter().isArray() ? "[...]" : "").concat(")");
    }
}