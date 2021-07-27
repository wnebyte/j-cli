package core;

import exception.config.NoSuchTypeConverterException;
import exception.runtime.ParseException;
import util.Splitter;
import util.StringUtil;

import java.lang.reflect.Parameter;

public final class Positional extends Argument {

    public static final String SYMBOL = "*";

    private final int index;

    protected Positional(Parameter parameter, int index) throws NoSuchTypeConverterException {
        super(parameter, SYMBOL);
        setRegex(getTypeConverter().isArray() ? TypeConverter.ARRAY_REGEX : TypeConverter.STANDARD_REGEX);
        this.index = index;
    }

    @Override
    protected Object initialize(String input) throws ParseException {
        /*
        return getTypeConverter().convert(
                input.split(TypeConverter.WHITESPACE_REGEX)[(getIndex() + 1)]);
         */
        String value = new Splitter().setContent(
                input.substring(StringUtil.indexOfNthWhiteSpace(input, getIndex()) + 1))
                .split();
        return getTypeConverter().convert(value);
    }

    protected int getIndex() {
        return index;
    }

    public String toString() {
        return getName().concat(getTypeConverter().isArray() ? "[]" : "");
    }
}
