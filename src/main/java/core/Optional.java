package core;

import exception.config.NoSuchTypeConverterException;
import exception.runtime.ParseException;
import util.Splitter;

import java.lang.reflect.Parameter;
import static util.ReflectionUtil.isBoolean;

public final class Optional extends Argument {

    public Optional(Parameter parameter, int index) throws NoSuchTypeConverterException {
        super(parameter, index);
        setRegex(isBoolean(parameter.getType()) ?
                "(\\s".concat(getName()).concat("|)") :
                        getTypeConverter().isArray() ?
                                "(\\s".concat(getName()).concat(TypeConverter.ARRAY_REGEX).concat("|)") :
                                "(\\s".concat(getName()).concat(TypeConverter.DEFAULT_REGEX).concat("|)")
                );
    }

    @Override
    protected Object initialize(final String input) throws ParseException {
        if (input.contains(getName())) {
            if (isBoolean(getType())) {
                return true;
            } else {
                String value = new Splitter().setContent(input)
                        .setDelimiter(getName())
                        .split();
                return getTypeConverter().convert(value);
            }
        }
        else {
            return getTypeConverter().defaultValue();
        }
    }

    public String toString() {
        return "(".concat(getName()).concat(getTypeConverter().isArray() ? "[]" : "").concat(")");
    }
}
