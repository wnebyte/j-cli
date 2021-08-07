package core;

import exception.config.NoSuchTypeConverterException;
import exception.runtime.ParseException;
import util.AnnotationUtil;
import util.StringUtil;
import java.lang.reflect.Parameter;
import java.util.Comparator;

/**
 * This abstract class represents a Command-Argument mapped directly from a Java Method's
 * Parameter.
 * @see annotation.Argument
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
     * Constructs a new <code>Argument</code>, using the specified parameter, and with the specified index.
     * @param parameter the parameter to be used when constructing a new instance.
     * @param index the index of the specified parameter.
     * @throws NoSuchTypeConverterException if there is no registered <code>TypeConverter</code> matching
     * the type of the specified parameter, and no <code>TypeConverter</code> is specified through the
     * parameters {@link annotation.Argument} annotation.
     */
    protected Argument(Parameter parameter, int index) throws NoSuchTypeConverterException {
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
     * Constructs a new <code>Argument</code>, using the specified parameter, and with the specified index
     * and name.
     * @param parameter the parameter to be used when constructing a new <code>Argument</code>.
     * @param index the index of the specified parameter.
     * @param name the name to give the <code>Argument</code>.
     * @throws NoSuchTypeConverterException if there is no registered <code>TypeConverter</code> matching
     * the type of the specified parameter, and no <code>TypeConverter</code> is specified through the
     * parameters {@link annotation.Argument} annotation.
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
     * Is called to initialize this <code>Argument</code>.
     * @param input the input to initialize.
     * @return the initialized value.
     * @throws ParseException if this <code>Argument</code>'s <code>TypeConverter</code> failed to convert the specified input, or a
     * substring of it, into the desired Type.
     */
    abstract Object initialize(final String input) throws ParseException;

    /**
     * Sets the regex of the <code>Argument</code>.
     * @param regex the regex to set.
     */
    protected void setRegex(String regex) {
        this.regex = regex;
    }

    /**
     * @return the regex of the <code>Argument</code>.
     */
    String getRegex() {
        return regex;
    }

    /**
     * @return the <code>TypeConverter</code> associated with this <code>Argument</code>.
     */
    protected TypeConverter<?> getTypeConverter() {
        return typeConverter;
    }

    /**
     * @return the index of the <code>Argument</code>.
     */
    int getIndex() {
        return index;
    }

    /**
     * @return the name of this <code>Argument</code>.
     */
    public String getName() {
        return name;
    }

    /**
     * @return the description of the <code>Argument</code>.
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
     * @return the type of the <code>Argument</code>.
     */
    public Class<?> getType() {
        return type;
    }

    /**
     * @return a String representation of the <code>Argument</code>.
     */
    public String toString() {
        return getName();
    }

    /**
     * @return a Comparator.
     */
    static Comparator<Argument> getComparator() {
        return COMPARATOR;
    }
}