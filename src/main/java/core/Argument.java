package core;

import exception.config.NoSuchTypeConverterException;
import exception.runtime.ParseException;
import util.AnnotationUtil;
import util.StringUtil;

import java.lang.reflect.Parameter;

public abstract class Argument {

    private static final char PREFIX = '-';

    private final String name;

    private final Class<?> type;

    private final TypeConverter<?> typeConverter;

    private final String description;

    private String regex;

    protected Argument(Parameter parameter) throws NoSuchTypeConverterException {
        if (parameter == null) {
            throw new IllegalArgumentException(
                    "Parameter must not be null."
            );
        }
        String tmp = AnnotationUtil.getName(parameter);
        this.name = StringUtil.normalize(tmp, PREFIX, true);
        this.description = AnnotationUtil.getDescription(parameter);
        this.typeConverter = AnnotationUtil.getTypeConverter(parameter);
        this.type = parameter.getType();
    }

    protected Argument(Parameter parameter, String name) throws NoSuchTypeConverterException {
        if (parameter == null) {
            throw new IllegalArgumentException(
                    "Parameter must not be null."
            );
        }
        this.description = AnnotationUtil.getDescription(parameter);
        this.typeConverter = AnnotationUtil.getTypeConverter(parameter);
        this.type = parameter.getType();
        this.name = name;
    }

    protected abstract Object initialize(final String input) throws ParseException;

    protected void setRegex(String regex) {
        this.regex = regex;
    }

    protected String getRegex() {
        return regex;
    }

    public String getName() {
        return name;
    }

    public TypeConverter<?> getTypeConverter() {
        return typeConverter;
    }

    public Class<?> getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        return getName();
    }
}
