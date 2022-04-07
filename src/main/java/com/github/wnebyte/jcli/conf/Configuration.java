package com.github.wnebyte.jcli.conf;

import java.util.*;
import java.lang.reflect.Method;
import java.io.InputStream;
import java.io.PrintStream;
import com.github.wnebyte.jarguments.convert.TypeConverter;
import com.github.wnebyte.jarguments.convert.AbstractTypeConverterMap;
import com.github.wnebyte.jarguments.convert.TypeConverterMap;
import com.github.wnebyte.jarguments.exception.*;
import com.github.wnebyte.jcli.annotation.*;
import com.github.wnebyte.jcli.CLI;
import com.github.wnebyte.jcli.BaseCommand;
import com.github.wnebyte.jcli.Formatter;
import com.github.wnebyte.jcli.HelpFormatter;
import com.github.wnebyte.jcli.exception.UnknownCommandException;
import com.github.wnebyte.jcli.di.DependencyContainer;
import com.github.wnebyte.jcli.di.IDependencyContainer;
import com.github.wnebyte.jcli.util.Identifier;

/**
 * This class is used to specify configuration options for instances of {@link CLI}.
 */
public class Configuration {

    /*
    ###########################
    #      STATIC FIELDS      #
    ###########################
    */

    /**
     * Default impl.
     */
    public static final Formatter<BaseCommand> DEFAULT_HELP_FORMATTER =
            new HelpFormatter();

    /**
     * Default impl.
     */
    public static final Formatter<UnknownCommandException> DEFAULT_UNKNOWN_COMMAND_EXCEPTION_FORMATTER =
            new Formatter<UnknownCommandException>() {
                @Override
                public String apply(UnknownCommandException e) {
                    return e.getMessage();
                }
            };

    /**
     * Default impl.
     */
    public static final Formatter<TypeConversionException> DEFAULT_TYPE_CONVERSION_EXCEPTION_FORMATTER =
            new Formatter<TypeConversionException>() {
                @Override
                public String apply(TypeConversionException e) {
                    return e.getMessage();
                }
            };

    /**
     * Default impl.
     */
    public static final Formatter<NoSuchArgumentException> DEFAULT_NO_SUCH_ARGUMENT_EXCEPTION_FORMATTER =
            new Formatter<NoSuchArgumentException>() {
                @Override
                public String apply(NoSuchArgumentException e) {
                    return e.getMessage();
                }
            };

    /**
     * Default impl.
     */
    public static final Formatter<MissingArgumentValueException> DEFAULT_MISSING_ARGUMENT_VALUE_EXCEPTION_FORMATTER =
            new Formatter<MissingArgumentValueException>() {
                @Override
                public String apply(MissingArgumentValueException e) {
                    return e.getMessage();
                }
            };

    /**
     * Default impl.
     */
    public static final Formatter<MalformedArgumentException> DEFAULT_MALFORMED_ARGUMENT_EXCEPTION_FORMATTER =
            new Formatter<MalformedArgumentException>() {
                @Override
                public String apply(MalformedArgumentException e) {
                    return e.getMessage();
                }
            };

    /**
     * Default impl.
     */
    public static final Formatter<MissingArgumentException> DEFAULT_MISSING_ARGUMENT_EXCEPTION_FORMATTER =
            new Formatter<MissingArgumentException>() {
                @Override
                public String apply(MissingArgumentException e) {
                    return e.getMessage();
                }
            };

    /*
    ###########################
    #          FIELDS         #
    ###########################
    */

    private PrintStream out = System.out;

    private PrintStream err = System.err;

    private InputStream in = System.in;

    private AbstractTypeConverterMap typeConverters = TypeConverterMap.getInstance();

    private IDependencyContainer dependencyContainer = new DependencyContainer();

    /**
     * Is used to indicate to the <code>CLI</code> which objects to scan for annotated Java Methods.
     */
    private Set<Object> objects = null;

    /**
     * Is used to indicate to the <code>CLI</code> which Methods to scan for annotated Java Methods.
     */
    private Set<Method> methods = null;

    /**
     * Is used to indicate to the <code>CLI</code> which classes to scan for annotated Java Methods.
     */
    private Set<Class<?>> classes = null;

    /**
     * Is used to indicate to the <code>CLI</code> which Methods to scan for annotated Java Methods.
     */
    private Set<Identifier> identifiers = null;

    /**
     * Is used to indicate to the <code>CLI</code> which classes it should exclude from being scanned for annotated Java Methods.
     */
    private Set<Class<?>> excludeClasses = null;

    /**
     * Is used to indicate to the <code>CLI</code> which packages to scan for annotated Java Methods.
     */
    private Set<String> packages = new HashSet<String>() {{
        add("");
    }};

    /**
     * Is used to indicate to the <code>CLI</code> that it should not build its built-in Help Command.
     */
    private boolean nullifyHelpCommand = false;

    /**
     * Is used by the <code>CLI</code> to format output.
     */
    private Formatter<BaseCommand> helpFormatter =
            DEFAULT_HELP_FORMATTER;

    /**
     * Is used by the <code>CLI</code> to format output.
     */
    private Formatter<UnknownCommandException> unknownCommandExceptionFormatter =
            DEFAULT_UNKNOWN_COMMAND_EXCEPTION_FORMATTER;

    /**
     * Is used by the <code>CLI</code> to format output.
     */
    private Formatter<TypeConversionException> typeConversionExceptionFormatter =
            DEFAULT_TYPE_CONVERSION_EXCEPTION_FORMATTER;

    /**
     * Is used by the <code>CLI</code> to format output.
     */
    private Formatter<NoSuchArgumentException> noSuchArgumentExceptionFormatter =
            DEFAULT_NO_SUCH_ARGUMENT_EXCEPTION_FORMATTER;

    /**
     * Is used by the <code>CLI</code> to format output.
     */
    private Formatter<MissingArgumentValueException> missingArgumentValueExceptionFormatter =
            DEFAULT_MISSING_ARGUMENT_VALUE_EXCEPTION_FORMATTER;

    /**
     * Is used by the <code>CLI</code> to format output.
     */
    private Formatter<MalformedArgumentException> malformedArgumentExceptionFormatter =
            DEFAULT_MALFORMED_ARGUMENT_EXCEPTION_FORMATTER;

    /**
     * Is used by the <code>CLI</code> to format output.
     */
    private Formatter<MissingArgumentException> missingArgumentExceptionFormatter =
            DEFAULT_MISSING_ARGUMENT_EXCEPTION_FORMATTER;

    /*
    ###########################
    #       CONSTRUCTORS      #
    ###########################
    */

    /**
     * Constructs a new instance.
     */
    public Configuration() { }

    /*
    ###########################
    #      SETTER METHODS     #
    ###########################
    */

    /**
     * Specifies that the <code>CLI</code> should use the specified <code>PrintStream</code> when
     * appending standard output.
     * @param out to be used.
     * @return this (for chaining).
     */
    public Configuration setOut(PrintStream out) {
        if (out != null) {
            this.out = out;
        }
        return this;
    }

    /**
     * Specifies that the <code>CLI</code> should use the specified <code>PrintStream</code> when
     * appending error related output.
     * @param err to be used.
     * @return this (for chaining).
     */
    public Configuration setErr(PrintStream err) {
        if (err != null) {
            this.err = err;
        }
        return this;
    }

    /**
     * Specified that the <code>CLI</code> should use the specified <code>InputStream</code> when
     * reading input from the user.
     * @param in to be used.
     * @return this (for chaining).
     */
    public Configuration setIn(InputStream in) {
        if (in != null) {
            this.in = in;
        }
        return this;
    }
    
    /**
     * Specifies that the <code>CLI</code> should use the specified <code>Formatter</code> when its built-in Help
     * Command is invoked.
     * @param formatter to be used.
     * @return this (for chaining).
     */
    public Configuration setHelpFormatter(Formatter<BaseCommand> formatter) {
        if (formatter != null) {
            this.helpFormatter = formatter;
        }
        return this;
    }

    /**
     * Specifies that the <code>CLI</code> should use the specified <code>Formatter</code> when catching a
     * thrown <code>UnknownCommandException</code>.
     * @param formatter to be used.
     * @return this (for chaining).
     */
    public Configuration setUnknownCommandFormatter(Formatter<UnknownCommandException> formatter) {
        if (formatter != null) {
            this.unknownCommandExceptionFormatter = formatter;
        }
        return this;
    }

    /**
     * Specifies that the <code>CLI</code> should use the specified <code>Formatter</code> when catching a
     * thrown <code>TypeConversionException</code>.
     * @param formatter to be used.
     * @return this (for chaining).
     */
    public Configuration setTypeConversionExceptionFormatter(Formatter<TypeConversionException> formatter) {
        if (formatter != null) {
            this.typeConversionExceptionFormatter = formatter;
        }
        return this;
    }

    /**
     * Specifies that the <code>CLI</code> should use the specified <code>Formatter</code> when catching a
     * thrown <code>NoSuchArgumentException</code>.
     * @param formatter to be used.
     * @return this (for chaining).
     */
    public Configuration setNoSuchArgumentExceptionFormatter(Formatter<NoSuchArgumentException> formatter) {
        if (formatter != null) {
            this.noSuchArgumentExceptionFormatter = formatter;
        }
        return this;
    }

    /**
     * Specifies that the <code>CLI</code> should use the specified <code>Formatter</code> when catching a
     * thrown <code>MissingArgumentValueException</code>.
     * @param formatter to be used.
     * @return this (for chaining).
     */
    public Configuration setMissingArgumentValueExceptionFormatter(Formatter<MissingArgumentValueException> formatter) {
        if (formatter != null) {
            this.missingArgumentValueExceptionFormatter = formatter;
        }
        return this;
    }

    /**
     * Specifies that the <code>CLI</code> should use the specified <code>Formatter</code> when catching a
     * thrown <code>MalformedArgumentException</code>.
     * @param formatter to be used.
     * @return this (for chaining).
     */
    public Configuration setMalformedArgumentExceptionFormatter(Formatter<MalformedArgumentException> formatter) {
        if (formatter != null) {
            this.malformedArgumentExceptionFormatter = formatter;
        }
        return this;
    }

    /**
     * Specifies that the <code>CLI</code> should use the specified <code>Formatter</code> when catching a
     * thrown <code>MissingArgumentException</code>.
     * @param formatter to be used.
     * @return this (for chaining).
     */
    public Configuration setMissingArgumentExceptionFormatter(Formatter<MissingArgumentException> formatter) {
        if (formatter != null) {
            this.missingArgumentExceptionFormatter = formatter;
        }
        return this;
    }

    /**
     * Specifies that the specified pair should be registered with the
     * {@link IDependencyContainer} associated with this instance.
     * @param base the base class.
     * @param impl the implementing Object.
     * @param <T> the type of the base class.
     * @param <R> the type of the implementing Object.
     * @return this (for chaining).
     */
    public <T, R extends T> Configuration registerDependency(Class<T> base, R impl) {
        if ((base != null) && (impl != null)) {
            dependencyContainer.registerDependency(base, impl);
        }
        return this;
    }

    /**
     * Specifies the the specified <code>Class</code> and <code>TypeConverter</code> should be registered as an entry
     * with the {@link AbstractTypeConverterMap} associated with this instance.
     * @param cls a Class.
     * @param typeConverter a TypeConverter.
     * @param <T> the type of the Class and TypeConverter.
     * @return this (for chaining).
     */
    public <T> Configuration registerTypeConverter(Class<T> cls, TypeConverter<T> typeConverter) {
        if ((cls != null) && (typeConverter != null)) {
            typeConverters.put(cls, typeConverter);
        }
        return this;
    }

    /**
     * Specifies the the specified <code>Class</code> and <code>TypeConverter</code> should be registered as an entry
     * with the {@link AbstractTypeConverterMap} associated with this instance,
     * only if a mapping for the specified Class is not already present.
     * @param cls a Class.
     * @param typeConverter a TypeConverter.
     * @param <T> the type of the Class and TypeConverter.
     * @return this (for chaining).
     */
    public <T> Configuration registerTypeConverterIfAbsent(Class<T> cls, TypeConverter<T> typeConverter) {
        if ((cls != null) && (typeConverter != null)) {
            typeConverters.putIfAbsent(cls, typeConverter);
        }
        return this;
    }

    /**
     * Specifies that the <code>CLI</code> should use the specified <code>AbstractTypeConverterMap</code>.
     * @param typeConverters to be used.
     * @return this (for chaining).
     */
    public Configuration setTypeConverterMap(AbstractTypeConverterMap typeConverters) {
        if (typeConverters != null) {
            this.typeConverters = typeConverters;
        }
        return this;
    }

    /**
     * Specifies that the <code>CLI</code> should scan the specified <code>Object[]</code> for
     * {@link com.github.wnebyte.jcli.annotation.Command} annotated Java Methods.
     * @param objects to be scanned.
     * @return this (for chaining).
     */
    public Configuration setScanObjects(Object... objects) {
        if (objects != null) {
            this.objects = new HashSet<>(Arrays.asList(objects));
        }
        return this;
    }

    /**
     * Specifies that the <code>CLI</code> should scan the specified <code>Method[]</code> for
     * {@link com.github.wnebyte.jcli.annotation.Command} annotated Java Methods.
     * @param methods to be scanned.
     * @return this (for chaining).
     */
    public Configuration setScanMethods(Method... methods) {
        if (methods != null) {
            this.methods = new HashSet<>(Arrays.asList(methods));
        }
        return this;
    }

    /**
     * Specifies that the <code>CLI</code> should scan the specified <code>Class[]</code> for
     * {@link com.github.wnebyte.jcli.annotation.Command} annotated Java Methods.
     * @param classes to be scanned.
     * @return this (for chaining).
     */
    public Configuration setScanClasses(Class<?>... classes) {
        if (classes != null) {
            this.classes = new HashSet<>(Arrays.asList(classes));
        }
        return this;
    }

    /**
     * Specifies that the <code>CLI</code> should scan the specified <code>String[]</code> for
     * {@link com.github.wnebyte.jcli.annotation.Command} annotated Java Methods.
     * @param packages to be scanned.
     * @return this (for chaining).
     * @see org.reflections.util.ClasspathHelper#forPackage(String, ClassLoader...)
     */
    public Configuration setScanPackages(String... packages) {
        if (packages != null) {
            this.packages = new HashSet<>(Arrays.asList(packages));
        }
        return this;
    }

    /**
     * Specifies that the <code>CLI</code> should scan the specified <code>Identifier[]</code> for
     * {@link com.github.wnebyte.jcli.annotation.Command} annotated Java Methods.
     * @param identifiers to be scanned.
     * @return this (for chaining).
     */
    public Configuration setScanIdentifiers(Identifier... identifiers) {
        if (identifiers != null) {
            this.identifiers = new HashSet<>(Arrays.asList(identifiers));
        }
        return this;
    }

    /**
     * Specifies that the <code>CLI</code> should exclude the classes contained in the specified <code>Class[]</code>
     * from being scanned for {@link Command} annotated Java Methods.
     * @param classes to be excluded from scanning.
     * @return this (for chaining).
     */
    public Configuration setExcludeClasses(Class<?>... classes) {
        if (classes != null) {
            this.excludeClasses = new HashSet<>(Arrays.asList(classes));
        }
        return this;
    }

    /**
     * Specifies that the <code>CLI</code> should not build its built-in Help Command.
     * @return this (for chaining).
     */
    public Configuration nullifyHelpCommand() {
        this.nullifyHelpCommand = true;
        return this;
    }

    /**
     * Specifies that the <code>CLI</code> should not scan any packages for any {@link Command} annotated Java Methods.
     * @return this (for chaining).
     */
    public Configuration nullifyScanPackages() {
        this.packages = null;
        return this;
    }

    /*
    ###########################
    #      GETTER METHODS     #
    ###########################
    */

    public PrintStream out() {
        return out;
    }

    public PrintStream err() {
        return err;
    }

    public InputStream in() {
        return in;
    }

    /**
     * @return the <code>IDependencyContainer</code> associated with this instance.
     */
    public IDependencyContainer getDependencyContainer() {
        return dependencyContainer;
    }

    /**
     * @return the <code>AbstractTypeConverterMap</code> associated with this instance.
     */
    public AbstractTypeConverterMap getTypeConverterMap() {
        return typeConverters;
    }

    /**
     * @return the <code>Set</code> of classes to be scanned for annotated Java Methods associated with this instance.
     */
    public Set<Class<?>> getScanClasses() {
        return classes;
    }

    /**
     * @return the <code>Set</code> of objects to be scanned for annotated Java Methods associated with this instance.
     */
    public Set<Object> getScanObjects() {
        return objects;
    }

    /**
     * @return the <code>Set</code> of methods to be scanned for annotated Java Methods associated with this instance.
     */
    public Set<Method> getScanMethods() {
        return methods;
    }

    /**
     * @return the <code>Set</code> of packages to be scanned for annotated Java Methods associated with this instance.
     */
    public Set<String> getScanPackages() {
        return packages;
    }

    /**
     * @return the <code>Set</code> of identifiers to be scanned for annotated Java Methods associated with this instance.
     */
    public Set<Identifier> getScanIdentifiers() {
        return identifiers;
    }

    /**
     * @return the <code>Set</code> of classes to be excluded from being scanned for annotated Java Methods associated with this instance.
     */
    public Set<Class<?>> getExcludeClasses() {
        return excludeClasses;
    }

    /**
     * @return whether the <code>CLI</code> should not build its built-in Help Command.
     */
    public boolean isNullifyHelpCommand() {
        return nullifyHelpCommand;
    }

    /**
     * @return the Help Formatter associated with this instance.
     */
    public Formatter<BaseCommand> getHelpFormatter() {
        return helpFormatter;
    }

    /**
     * @return the UnknownCommandException Formatter associated with this instance.
     */
    public Formatter<UnknownCommandException> getUnknownCommandExceptionFormatter() {
        return unknownCommandExceptionFormatter;
    }

    /**
     * @return the TypeConversionException Formatter associated with this instance.
     */
    public Formatter<TypeConversionException> getTypeConversionExceptionFormatter() {
        return typeConversionExceptionFormatter;
    }

    /**
     * @return the NoSuchArgumentException Formatter associated with this instance.
     */
    public Formatter<NoSuchArgumentException> getNoSuchArgumentExceptionFormatter() {
        return this.noSuchArgumentExceptionFormatter;
    }

    /**
     * @return the MissingArgumentValueException Formatter associated with this instance.
     */
    public Formatter<MissingArgumentValueException> getMissingArgumentValueExceptionFormatter() {
        return this.missingArgumentValueExceptionFormatter;
    }

    /**
     * @return the MalformedArgumentException Formatter associated with this instance.
     */
    public Formatter<MalformedArgumentException> getMalformedArgumentExceptionFormatter() {
        return this.malformedArgumentExceptionFormatter;
    }

    /**
     * @return the MissingArgumentException Formatter associated with this instance.
     */
    public Formatter<MissingArgumentException> getMissingArgumentExceptionFormatter() {
        return this.missingArgumentExceptionFormatter;
    }

}