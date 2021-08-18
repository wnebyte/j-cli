package com.github.wnebyte.jshell;

import com.github.wnebyte.jshell.exception.config.NoSuchTypeConverterException;
import com.github.wnebyte.jshell.exception.runtime.ParseException;
import com.github.wnebyte.jshell.util.AnnotationUtil;
import com.github.wnebyte.jshell.util.StringUtil;
import java.lang.reflect.Parameter;
import java.util.Comparator;

/**
 * This abstract class represents a Command Argument mapped directly from a Java Method's
 * Parameter.
 * @see com.github.wnebyte.jshell.annotation.Argument
 */
/*
Class implementation comments
 */
public abstract class Argument {

    // static
    protected static final String WHITESPACE_REGEX = "\\s";

    protected static final String DEFAULT_REGEX = WHITESPACE_REGEX + "([^\\s\"]*|\"[^\"]*\")";

    protected static final String ARRAY_REGEX = WHITESPACE_REGEX + "\\[([^\\s\"]*|\"[^\"]*\")*\\]";

    private static final Comparator<Argument> COMPARATOR = new Comparator<Argument>() {
        @Override
        public int compare(Argument o1, Argument o2) {
            if (o1 == null) {
                if (o2 == null) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                if (o2 == null) {
                    return -1;
                }
            }
            if (o1 instanceof Positional) {
                if (o2 instanceof Positional) {
                   // return 0;
                    return o1.getIndex() - o2.getIndex();
                } else { // o2 is non-positional
                    return -1;
                }
                // o1 != Positional
            } else {
                if (o2 instanceof Positional) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    };

    /**
     * @return Comparator.
     */
    static Comparator<Argument> getComparator() {
        return COMPARATOR;
    }

    /*
    instance variables
    1. public
    2. protected
    3. private
     */

    private final String name;

    private final String description;

    private final Class<?> type;

    private final TypeConverter<?> typeConverter;

    private final int index;

    private String regex; // lateinit

    // constructors
    /**
     * Constructs a new instance using the specified parameter, and with the specified index.
     * @param parameter the parameter to be used when constructing the new instance.
     * @param index the index of the specified parameter.
     * @throws NoSuchTypeConverterException if there is no registered TypeConverter matching
     * the type of the specified parameter, and no TypeConverter has been specified through the
     * parameters {@link com.github.wnebyte.jshell.annotation.Argument} annotation.
     */
    protected Argument(Parameter parameter, int index) throws NoSuchTypeConverterException {
        if (parameter == null) {
            throw new IllegalArgumentException(
                    "Parameter must not be null."
            );
        }
        this.name = StringUtil.normalize(AnnotationUtil.getName(parameter));
        this.description = AnnotationUtil.getDescription(parameter);
        this.typeConverter = AnnotationUtil.getTypeConverter(parameter);
        this.type = parameter.getType();
        this.index = index;
    }

    /**
     * Constructs a new instance using the specified parameter, and with the specified index
     * and name.
     * @param parameter the parameter to be used when constructing the new instance.
     * @param index the index of the specified parameter.
     * @param name the name of the new instance.
     * @throws NoSuchTypeConverterException if there is no registered TypeConverter matching
     * the type of the specified parameter, and no TypeConverter has been specified through the
     * parameters {@link com.github.wnebyte.jshell.annotation.Argument} annotation.
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

    /**
     * Initializes this Argument by using its registered TypeConverter.
     * @param input the input to initialize.
     * @return the initialized value.
     * @throws ParseException if this Argument's TypeConverter fails to convert the specified input, or a
     * substring of it into the desired Type.
     */
    abstract Object initialize(final String input) throws ParseException;

    /**
     * Sets the regular expression for this Argument.
     * @param regex the regex to set for this Argument.
     */
    // lateinit for subclasses
    protected void setRegex(final String regex) {
        if (regex != null) {
            if (this.regex == null) {
                this.regex = regex;
            } else {
                throw new IllegalStateException(
                        "Regex has already been initialized"
                );
            }
        }
    }

    /**
     * @return the regular expression for this Argument.
     */
    String getRegex() {
        return regex;
    }

    /**
     * @return the index of this Argument.
     */
    int getIndex() {
        return index;
    }

    /**
     * @return the TypeConverter for this Argument.
     */
    protected TypeConverter<?> getTypeConverter() {
        return typeConverter;
    }

    /**
     * @return the type of this Argument.
     */
    public Class<?> getType() {
        return type;
    }

    /**
     * @return the name of this Argument.
     */
    public String getName() {
        return name;
    }

    /**
     * @return the description for this Argument.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return whether this Argument has a description.
     */
    public boolean hasDescription() {
        return (description != null) && !(description.equals(""));
    }

    /**
     * @return a String representation of this Argument.
     */
    public String toString() {
        return getName();
    }
}