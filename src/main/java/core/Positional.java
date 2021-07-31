package core;

import exception.config.NoSuchTypeConverterException;
import exception.runtime.ParseException;

import java.lang.reflect.Parameter;

public final class Positional extends Argument {

    public static final String SYMBOL = "*";

    private final int position;

    protected Positional(Parameter parameter, int index, int position) throws NoSuchTypeConverterException {
        super(parameter, index, SYMBOL);
        setRegex(getTypeConverter().isArray() ? TypeConverter.ARRAY_REGEX : TypeConverter.STANDARD_REGEX);
        this.position = position;
    }

    /*
    @Override
    protected Object initialize(String input) throws ParseException {

        return getTypeConverter().convert(
                input.split(TypeConverter.WHITESPACE_REGEX)[(getIndex() + 1)]);

        String value = new Splitter().setContent(
                input.substring(StringUtil.indexOfNthWhiteSpace(input, getIndex()) + 1))
                .split();
        return getTypeConverter().convert(value);
    }
    */

    @Override
    protected Object initialize(String input) throws ParseException {
        return getTypeConverter().convert(input);
    }

    protected int getPosition() {
        return position;
    }

    public String toString() {
        return getName().concat(getTypeConverter().isArray() ? "[]" : "");
    }
}
