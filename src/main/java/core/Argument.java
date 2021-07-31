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

    private final int index;

    // Todo: is effectively final
    private String regex;

    /**
     * Primary Constructor.
     */
    Argument(Parameter parameter, int index) throws NoSuchTypeConverterException {
        if (parameter == null) {
            throw new IllegalArgumentException(
                    "Parameter must not be null."
            );
        }
        this.name = StringUtil.normalizeName(AnnotationUtil.getName(parameter));
        this.description = AnnotationUtil.getDescription(parameter);
        this.typeConverter = AnnotationUtil.getTypeConverter(parameter);
        this.type = parameter.getType();
        this.index = index;
    }

    /**
     * Used by Positional.class subclass.
     */
    protected Argument(Parameter parameter, int index, String name) throws NoSuchTypeConverterException {
        if (parameter == null) {
            throw new IllegalArgumentException(
                    "Parameter must not be null."
            );
        }
        this.name = name;
        this.description = AnnotationUtil.getDescription(parameter);
        this.typeConverter = AnnotationUtil.getTypeConverter(parameter);
        this.type = parameter.getType();
        this.index = index;
    }

    abstract Object initialize(final String input) throws ParseException;

    protected void setRegex(String regex) {
        this.regex = regex;
    }

    protected String getRegex() {
        return regex;
    }

    protected int getIndex() {
        return index;
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
