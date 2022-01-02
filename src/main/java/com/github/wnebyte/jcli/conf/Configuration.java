package com.github.wnebyte.jcli.conf;

import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.lang.reflect.Method;
import com.github.wnebyte.jarguments.convert.TypeConverter;
import com.github.wnebyte.jarguments.convert.AbstractTypeConverterMap;
import com.github.wnebyte.jarguments.convert.TypeConverterMap;
import com.github.wnebyte.jarguments.exception.ParseException;
import com.github.wnebyte.jcli.BaseCommand;
import com.github.wnebyte.jcli.Formatter;
import com.github.wnebyte.jcli.HelpFormatter;
import com.github.wnebyte.jcli.exception.UnknownCommandException;
import com.github.wnebyte.jcli.di.DependencyContainer;
import com.github.wnebyte.jcli.di.IDependencyContainer;
import com.github.wnebyte.jcli.io.Console;
import com.github.wnebyte.jcli.io.IConsole;
import com.github.wnebyte.jcli.io.IWriter;
import com.github.wnebyte.jcli.util.Identifier;

/**
 * This class is used to specify configuration options for instances of {@link com.github.wnebyte.jcli.CLI}.
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
    public static final Formatter<ParseException> DEFAULT_PARSE_EXCEPTION_FORMATTER =
            new Formatter<ParseException>() {
        @Override
        public String apply(ParseException e) {
            return e.getMessage();
        }
    };

    /**
     * Default impl.
     */
    public static final Formatter<UnknownCommandException> DEFAULT_UNKNOWN_COMMAND_FORMATTER =
            new Formatter<UnknownCommandException>() {
        @Override
        public String apply(UnknownCommandException e) {
            return e.getMessage();
        }
    };

    /*
    ###########################
    #     INSTANCE FIELDS     #
    ###########################
    */

    /**
     * Is used by the <code>CLI</code> to print and read.
     */
    private IConsole console = new Console();

    private AbstractTypeConverterMap converters = TypeConverterMap.getInstance();

    private IDependencyContainer dependencyContainer = new DependencyContainer();

    /**
     * Is used by the <code>CLI</code> to scan for annotated Java Methods.
     */
    private Set<Object> objects = null;

    /**
     * Is used by the <code>CLI</code> to scan for annotated Java Methods.
     */
    private Set<Method> methods = null;

    /**
     * Is used by the <code>CLI</code> to scan for annotated Java Methods.
     */
    private Set<Class<?>> classes = null;

    /**
     * Is used by the <code>CLI</code> to scan for annotated Java Methods.
     */
    private Set<Identifier> identifiers = null;

    /**
     * Is used by the <code>CLI</code> to exclude classes from being scanned for annotated Java Methods.
     */
    private Set<Class<?>> excludeClasses = null;

    /**
     * Is used by the <code>CLI</code> to scan for annotated Java Methods.
     */
    private Set<String> packages = new HashSet<String>() {{
        add("");
    }};

    /**
     * Is used to indicate that the <code>CLI</code> should not build its built-in Help Command.
     */
    private boolean nullifyHelpCommand = false;

    /**
     * Is used by the <code>CLI</code> to format output.
     */
    private Formatter<BaseCommand> helpFormatter = DEFAULT_HELP_FORMATTER;

    /**
     * Is used by the <code>CLI</code> to format output.
     */
    private Formatter<ParseException> parseExceptionFormatter = DEFAULT_PARSE_EXCEPTION_FORMATTER;

    /**
     * Is used by the <code>CLI</code> to format output.
     */
    private Formatter<UnknownCommandException> unknownCommandExceptionFormatter = DEFAULT_UNKNOWN_COMMAND_FORMATTER;

    /*
    ###########################
    #       CONSTRUCTORS      #
    ###########################
    */

    public Configuration() {
        registerDependency(IConsole.class, console);
        registerDependency(IWriter.class, console.writer());
    }

    /*
    ###########################
    #      SETTER METHODS     #
    ###########################
    */

    /**
     * Specify that the <code>CLI</code> should use the specified <code>IConsole</code>, and that the specified
     * pairing should be registered with the
     * {@link IDependencyContainer} associated with this instance.
     * @param abs the base class.
     * @param console the implementation.
     * @param <T> the type of the base class.
     * @param <R> the type of the implementation.
     * @return this (for chaining).
     */
    public <T extends IConsole, R extends T> Configuration setConsole(Class<T> abs, R console) {
        if (abs != null && console != null) {
            this.console = console;
            registerDependency(abs, console);
        }
        return this;
    }

    /**
     * Specify that the <code>CLI</code>'s Help Command should use the specified <code>Formatter</code>.
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
     * Specify that the <code>CLI</code> should use the specified <code>Formatter</code> when handling a
     * thrown <code>ParseException</code>.
     * @param formatter to be used.
     * @return this (for chaining).
     */
    public Configuration setParseExceptionFormatter(Formatter<ParseException> formatter) {
        if (formatter != null) {
            this.parseExceptionFormatter = formatter;
        }
        return this;
    }

    /**
     * Specify that the <code>CLI</code> should use the specified <code>Formatter</code> when handling a
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
     * Specify that the specified pair should be registered with the
     * {@link IDependencyContainer} associated with this instance.
     * @param abs the base class.
     * @param dependency the implementation.
     * @param <T> the type of the base class.
     * @param <R> the type of the implementation.
     * @return this (for chaining).
     */
    public <T, R extends T> Configuration registerDependency(Class<T> abs, R dependency) {
        if ((abs != null) && (dependency != null)) {
            dependencyContainer.registerDependency(abs, dependency);
        }
        return this;
    }

    /**
     * Specify the the specified pair should be registered with
     * the {@link AbstractTypeConverterMap} associated with this instance.
     * @param cls the key.
     * @param converter the value.
     * @param <T> the type of the converter.
     * @return this (for chaining).
     */
    public <T> Configuration registerTypeConverter(Class<T> cls, TypeConverter<T> converter) {
        if ((cls != null) && (converter != null)) {
            converters.put(cls, converter);
        }
        return this;
    }

    /**
     * Specify the the specified pair should be registered with
     * the {@link AbstractTypeConverterMap} associated with this instance,
     * only if a mapping for the specified key is not already present.
     * @param cls the key.
     * @param converter the value.
     * @param <T> the type of the converter.
     * @return this (for chaining).
     */
    public <T> Configuration registerTypeConverterIfAbsent(Class<T> cls, TypeConverter<T> converter) {
        if ((cls != null) && (converter != null)) {
            converters.putIfAbsent(cls, converter);
        }
        return this;
    }

    /**
     * Specify that the <code>CLI</code> should use the specified <code>AbstractTypeConverterMap</code>.
     * @param converters to be used.
     * @return this (for chaining).
     */
    public Configuration setTypeConverterMap(AbstractTypeConverterMap converters) {
        if (converters != null) {
            this.converters = converters;
        }
        return this;
    }

    /**
     * Specify that the <code>CLI</code> should scan the specified <code>Object[]</code> for
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
     * Specify that the <code>CLI</code> should scan the specified <code>Method[]</code> for
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
     * Specify that the <code>CLI</code> should scan the specified <code>Class[]</code> for
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
     * Specify that the <code>CLI</code> should scan the specified <code>String[]</code> for
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
     * Specify that the <code>CLI</code> should scan the specified <code>Identifier[]</code> for
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
     * Specify that the <code>CLI</code> should exclude the classes in the specified <code>Class[]</code>
     * from being scanned for {@link com.github.wnebyte.jcli.annotation.Command} annotated Java Methods.
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
     * Specify that the <code>CLI</code> should not build its built-in Help Command.
     * @return this (for chaining).
     */
    public Configuration nullifyHelpCommand() {
        this.nullifyHelpCommand = true;
        return this;
    }

    /**
     * Specify that the <code>CLI</code> should not scan any packages.
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
        return converters;
    }

    /**
     * @return the enumeration of classes to be scanned associated with this instance.
     */
    public Set<Class<?>> getScanClasses() {
        return classes;
    }

    /**
     * @return the enumeration of objects to be scanned associated with this instance.
     */
    public Set<Object> getScanObjects() {
        return objects;
    }

    /**
     * @return the enumeration of methods to be scanned associated with this instance.
     */
    public Set<Method> getScanMethods() {
        return methods;
    }

    /**
     * @return the enumeration of packages to be scanned associated with this instance.
     */
    public Set<String> getScanPackages() {
        return packages;
    }

    /**
     * @return the enumeration of identifiers to be scanned associated with this instance.
     */
    public Set<Identifier> getScanIdentifiers() {
        return identifiers;
    }

    /**
     * @return the enumeration of classes to be excluded from being scanned associated with this instance.
     */
    public Set<Class<?>> getExcludeClasses() {
        return excludeClasses;
    }

    /**
     * @return the <code>IConsole</code> associated with this <code>Configuration</code> instance.
     */
    public IConsole getConsole() {
        return console;
    }

    /**
     * @return whether the <code>CLI</code>'s built-in Help Command should not be built.
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
     * @return the ParseException Formatter associated with this instance.
     */
    public Formatter<ParseException> getParseExceptionFormatter() {
        return parseExceptionFormatter;
    }

    /**
     * @return the UnknownCommandException Formatter associated with this instance.
     */
    public Formatter<UnknownCommandException> getUnknownCommandExceptionFormatter() {
        return unknownCommandExceptionFormatter;
    }
}