package core;

import exception.runtime.ParseException;
import util.Bundle;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

import static util.StringUtil.whitespace;

public class ConfigurationBuilder {

    private IConsole console;

    private Consumer<String> noSuchCommandHandler;

    private Function<String, String> unknownCommandOutputFormatter = new Function<>() {
        // default implementation
        @Override
        public String apply(String input) {
            return "'".concat(input).concat("'").concat(" is not recognized as an internal command.");
        }
    };

    private Consumer<ParseException> parseExceptionHandler;

    private Function<ParseException, String> parseExceptionOutputFormatter = new Function<>() {
        // default implementation
        @Override
        public String apply(ParseException e) {
            return e.getArgument().getType().getSimpleName().toUpperCase() +
                    " could not be parsed from '" + e.getRaw() + "'";
        }
    };

    private Consumer<Collection<Command>> helpHandler;

    /** The HelpOutputFormatter Function */
    private Function<Collection<core.Command>, String> helpOutputFormatter = new Function<>() {
        // default implementation
        @Override
        public String apply(Collection<Command> commands) {
            StringBuilder output = new StringBuilder();
            int max = commands.stream().mapToInt(command -> command.toString().length()).max()
                    .orElse(0) + 1;
            int i = 0;

            for (Command cmd : commands) {
                String whitespace = whitespace(max - cmd.toString().length());
                output.append(cmd).append(whitespace).append(cmd.getDescription());
                String whitespaceln = whitespace(max);

                cmd.getArguments().forEach(arg -> {
                    output.append("\n").append(whitespaceln).append(arg.getName()).append(" ")
                            .append(arg.getType().getSimpleName().toUpperCase()).append(" ")
                            .append(arg.getDescription());
                });
                if (i++ != commands.size() - 1) {
                    output.append("\n");
                }
            }
            return new String(output);
        }
    };

    private Set<Object> objects;

    private Set<String> packages = new HashSet<>() {
        {
            add(""); // defaults to all packages on the classpath
        }
    };

    private Set<Class<?>> classes;

    private Bundle bundle;

    private boolean nullifyScanPackages = false;

    private boolean nullifyHelpCommands = false;

    public ConfigurationBuilder() {
    }

    /**
     * Configure the Shell to use the specified console.
     * <p/>
     * By not specifying a console the Shell has no means of providing any output, and all the
     * configured formatter functions will be void.
     * @param console the console to be used.
     */
    public final ConfigurationBuilder setConsole(final IConsole console) {
        this.console = console;
        return this;
    }

    /**
     * Configure the Shell to use the specified formatter, to format the output when the Shell encounters
     * an UnknownCommandException.
     * <p/>
     * Will only be called if an UnknownCommandHandler has not been configured.
     * @param formatter the formatter to be used.
     */
    public final ConfigurationBuilder setUnknownCommandOutputFormatter(final Function<String, String> formatter) {
        if (formatter != null) {
            this.unknownCommandOutputFormatter = formatter;
        }
        return this;
    }

    /**
     * Configure the Shell to use the specified handler, in the event of the Shell encountering a
     * UnknownCommandException.
     * @param handler the handler to be used.
     */
    public final ConfigurationBuilder setUnknownCommandHandler(final Consumer<String> handler) {
        if (handler != null) {
            this.noSuchCommandHandler = handler;
        }
        return this;
    }

    /**
     * Configure the Shell to use the specified handler, in the event of the Shell encountering a
     * ParseException.
     * @param handler the handler to be used.
     */
    public final ConfigurationBuilder setParseExceptionHandler(final Consumer<ParseException> handler) {
        if (handler != null) {
            this.parseExceptionHandler = handler;
        }
        return this;
    }

    /**
     * Configure the Shell to use the specified formatter, to format the output for when the
     * Shell encounters a ParseException.
     * <p/>
     * Will only be used if a ParseExceptionHandler has not been configured.
     * @param formatter the formatter to be used.
     */
    public final ConfigurationBuilder setParseExceptionOutputFormatter(final Function<ParseException, String> formatter) {
        if (formatter != null) {
            this.parseExceptionOutputFormatter = formatter;
        }
        return this;
    }

    /**
     * Configure the Shell to use the specified Handler, in the event of the Shell's help Command being matched.
     * @param handler the handler to be used.
     */
    public final ConfigurationBuilder setHelpHandler(final Consumer<Collection<Command>> handler) {
        if (handler != null) {
            this.helpHandler = handler;
        }
        return this;
    }

    /**
     * Configure the Shell to use the specified formatter Function, to format the output
     * of the Shell's help Command.
     * @param formatter the formatter Function to be used.
     */
    public final ConfigurationBuilder
    setHelpOutputFormatter(final Function<Collection<Command>, String> formatter) {
        if (formatter != null) {
            this.helpOutputFormatter = formatter;
        }
        return this;
    }

    /**
     * Configure the Shell to scan the specified packages. By default all packages are scanned.
     */
    public final ConfigurationBuilder setScanPackages(final String... packages) {
        if ((packages != null) && (packages.length != 0)) {
            this.packages = new HashSet<>(Arrays.asList(packages));
        }
        return this;
    }

    /**
     * Configure the Shell to scan the specified Objects.
     */
    public final ConfigurationBuilder setScanObjects(final Object... objects) {
        if ((objects != null) && (objects.length != 0)) {
            this.objects = new HashSet<>(Arrays.asList(objects));
        }
        return this;
    }

    /**
     * Configure the Shell to scan the specified Bundle.
     */
    public final ConfigurationBuilder setScanBundles(final Bundle bundle) {
        if (bundle != null) {
            this.bundle = bundle;
        }
        return this;
    }

    /**
     * Configure the Shell to scan the specified Classes.
     */
    public final ConfigurationBuilder setScanClasses(final Class<?>... classes) {
        if ((classes != null) && (classes.length != 0)) {
            this.classes = new HashSet<>(Arrays.asList(classes));
        }
        return this;
    }

    /**
     * Configure the Shell to not scan any packages. By default all packages are scanned.
     */
    public final ConfigurationBuilder nullifyScanPackages() {
        this.nullifyScanPackages = true;
        return this;
    }

    /**
     * Configure the Shell to not map/include its declared help Commands.
     */
    public final ConfigurationBuilder nullifyHelpCommands() {
        this.nullifyHelpCommands = true;
        return this;
    }

    /**
     * Configure a TypeConverter to be registered.
     */
    public final <T> ConfigurationBuilder
    registerTypeConverter(final Class<T> typeOf, final TypeConverter<T> typeConverter) {
        if ((typeOf != null) && (typeConverter != null)) {
            TypeConverterRepository.putIfAbsent(typeOf, typeConverter);
        }
        return this;
    }

    public IConsole getConsole() {
        return console;
    }

    public Consumer<String> getNoSuchCommandHandler() {
        return noSuchCommandHandler;
    }

    public Function<String, String> getUnknownCommandOutputFormatter() {
        return unknownCommandOutputFormatter;
    }

    public Consumer<ParseException> getParseExceptionHandler() {
        return parseExceptionHandler;
    }

    public Function<ParseException, String> getParseExceptionOutputFormatter() {
        return parseExceptionOutputFormatter;
    }

    public Consumer<Collection<Command>> getHelpHandler() {
        return helpHandler;
    }

    public Function<Collection<Command>, String> getHelpOutputFormatter() {
        return helpOutputFormatter;
    }

    public Set<String> getPackages() {
        return packages;
    }

    public Set<Object> getObjects() {
        return objects;
    }

    public Set<Class<?>> getClasses() {
        return classes;
    }

    public Bundle getBundle() { return bundle; }

    public boolean isNullifyScanPackages() {
        return nullifyScanPackages;
    }

    public boolean isNullifyHelpCommands() {
        return nullifyHelpCommands;
    }
}