package core;

import exception.config.NoSuchTypeConverterException;
import exception.runtime.ParseException;
import util.Splitter;
import java.lang.reflect.Parameter;

public final class Required extends Argument {

    public Required(Parameter parameter, int index) throws NoSuchTypeConverterException {
        super(parameter, index);
        setRegex("\\s".concat(getName()).concat(getTypeConverter().isArray() ?
                TypeConverter.ARRAY_REGEX : TypeConverter.DEFAULT_REGEX));
    }

    @Override
    protected Object initialize(final String input) throws ParseException {
        String value = new Splitter().setContent(input)
                .setDelimiter(getName())
                .split();
        return getTypeConverter().convert(value);
    }

    public String toString() {
        return getName().concat(getTypeConverter().isArray() ? "[]" : "");
    }
}
