package com.github.wnebyte.jcli;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import com.github.wnebyte.jarguments.converter.TypeConverter;
import com.github.wnebyte.jarguments.converter.AbstractTypeConverterMap;
import com.github.wnebyte.jarguments.converter.TypeConverterMap;
import com.github.wnebyte.jarguments.exception.ParseException;
import com.github.wnebyte.jcli.di.DependencyContainer;
import com.github.wnebyte.jcli.di.IDependencyContainer;
import com.github.wnebyte.jcli.exception.UnknownCommandException;
import com.github.wnebyte.jshell.Console;
import com.github.wnebyte.jshell.IConsole;

public class Configuration {

    /*
    ###########################
    #     STATIC FIELDS       #
    ###########################
    */

    private static final Formatter<BaseCommand> DEFAULT_HELP_FORMATTER =
            new Formatter<BaseCommand>() {
        @Override
        public String apply(BaseCommand cmd) {
            return cmd.toString();
        }
    };

    private static final Formatter<ParseException> DEFAULT_PARSE_EXCEPTION_FORMATTER =
            new Formatter<ParseException>() {
        @Override
        public String apply(ParseException e) {
            return e.getMessage();
        }
    };

    private static final Formatter<UnknownCommandException> DEFAULT_UNKNOWN_COMMAND_FORMATTER =
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

    private IConsole console = new Console();

    private AbstractTypeConverterMap converters = TypeConverterMap.getInstance();

    private IDependencyContainer dependencyContainer = new DependencyContainer();

    private Set<Object> objects = null;

    private Set<Method> methods = null;

    private Set<Class<?>> classes = null;

    private Set<Identifier> identifiers = null;

    private Set<Class<?>> excludeClasses = null;

    private Set<String> packages = new HashSet<String>() {{
        add("");
    }};

    private boolean nullifyHelpCommand = false;

    private Formatter<BaseCommand> helpFormatter = DEFAULT_HELP_FORMATTER;

    private Formatter<ParseException> parseExceptionFormatter = DEFAULT_PARSE_EXCEPTION_FORMATTER;

    private Formatter<UnknownCommandException> unknownCommandExceptionFormatter = DEFAULT_UNKNOWN_COMMAND_FORMATTER;

    /*
    ###########################
    #       CONSTRUCTORS      #
    ###########################
    */

    public Configuration() {
        registerDependency(IConsole.class, console);
    }

    /*
    ###########################
    #      SETTER METHODS     #
    ###########################
    */

    public Configuration setConsole(IConsole console) {
        if (console != null) {
            this.console = console;
        }
        return this;
    }

    public Configuration setHelpFormatter(Formatter<BaseCommand> formatter) {
        if (formatter != null) {
            this.helpFormatter = formatter;
        }
        return this;
    }

    public Configuration setParseExceptionFormatter(Formatter<ParseException> formatter) {
        if (formatter != null) {
            this.parseExceptionFormatter = formatter;
        }
        return this;
    }

    public Configuration setUnknownCommandFormatter(Formatter<UnknownCommandException> formatter) {
        if (formatter != null) {
            this.unknownCommandExceptionFormatter = formatter;
        }
        return this;
    }

    public <T, R extends T> Configuration registerDependency(Class<T> abs, R dependency) {
        if ((abs != null) && (dependency != null)) {
            dependencyContainer.registerDependency(abs, dependency);
        }
        return this;
    }

    public <T> Configuration registerTypeConverter(Class<T> cls, TypeConverter<T> converter) {
        if ((cls != null) && (converter != null)) {
            converters.put(cls, converter);
        }
        return this;
    }

    public <T> Configuration registerTypeConverterIfAbsent(Class<T> cls, TypeConverter<T> converter) {
        if ((cls != null) && (converter != null)) {
            converters.putIfAbsent(cls, converter);
        }
        return this;
    }

    public Configuration setTypeConverterMap(AbstractTypeConverterMap converters) {
        if (converters != null) {
            this.converters = converters;
        }
        return this;
    }

    public Configuration setScanObjects(Object... objects) {
        if (objects != null) {
            this.objects = new HashSet<>(Arrays.asList(objects));
        }
        return this;
    }

    public Configuration setScanMethods(Method... methods) {
        if (methods != null) {
            this.methods = new HashSet<>(Arrays.asList(methods));
        }
        return this;
    }

    public Configuration setScanClasses(Class<?>... classes) {
        if (classes != null) {
            this.classes = new HashSet<>(Arrays.asList(classes));
        }
        return this;
    }

    public Configuration setScanPackages(String... packages) {
        if (packages != null) {
            this.packages = new HashSet<>(Arrays.asList(packages));
        }
        return this;
    }

    public Configuration setScanIdentifiers(Identifier... identifiers) {
        if (identifiers != null) {
            this.identifiers = new HashSet<>(Arrays.asList(identifiers));
        }
        return this;
    }

    public Configuration setExcludeClasses(Class<?>... classes) {
        if (classes != null) {
            this.excludeClasses = new HashSet<>(Arrays.asList(classes));
        }
        return this;
    }

    public Configuration nullifyHelpCommand() {
        this.nullifyHelpCommand = true;
        return this;
    }

    public Configuration nullifyScanPackages() {
        this.packages = null;
        return this;
    }

    /*
    ###########################
    #      GETTER METHODS     #
    ###########################
    */

    public IDependencyContainer getDependencyContainer() {
        return dependencyContainer;
    }

    public AbstractTypeConverterMap getTypeConverterMap() {
        return converters;
    }

    public Set<Class<?>> getScanClasses() {
        return classes;
    }

    public Set<Object> getScanObjects() {
        return objects;
    }

    public Set<Method> getScanMethods() {
        return methods;
    }

    public Set<String> getScanPackages() {
        return packages;
    }

    public Set<Identifier> getScanIdentifiers() {
        return identifiers;
    }

    public Set<Class<?>> getExcludeClasses() {
        return excludeClasses;
    }

    public IConsole getConsole() {
        return console;
    }

    public boolean isNullifyHelpCommand() {
        return nullifyHelpCommand;
    }

    public Formatter<BaseCommand> getHelpFormatter() {
        return helpFormatter;
    }

    public Formatter<ParseException> getParseExceptionFormatter() {
        return parseExceptionFormatter;
    }

    public Formatter<UnknownCommandException> getUnknownCommandExceptionFormatter() {
        return unknownCommandExceptionFormatter;
    }
}