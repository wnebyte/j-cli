package core;

import exception.runtime.ParseException;
import util.Bundle;
import util.StringUtil;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * This class is used to specify configuration options for an instance of the {@link Shell} class.
 */
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

    private Consumer<Command> helpHandler;

    /**
     * Default implementation for the HelpOutputFormatter <code>Function</code>.
     */
    private Function<Command, String> helpOutputFormatter = new Function<Command, String>() {
        @Override
        public String apply(Command command) {
            StringBuilder output = new StringBuilder();
            output.append("Usage: ").append(command.toString())
                    .append(command.hasDescription() ? "\nDesc: ".concat(command.getDescription()) : "");

            if (!command.getArguments().isEmpty()) {
                int maxFirst  = maxFirst(command.getArguments());
                int maxSecond = maxSecond(command.getArguments());
                output.append("\n\n").append("Arguments: ").append("\n");

                for (Argument argument : command.getArguments()) {
                    String toString = argument.toString();
                    String whitespace = StringUtil
                            .genWhitespace((maxFirst + 1) - toString.length());
                    String ln = new StringBuilder().append(" ").append(toString).append(whitespace)
                            .append(argument.getDescription()).toString();
                    output.append(ln);
                    whitespace = StringUtil
                            .genWhitespace((maxSecond + 1) - ln.length());
                    output.append(whitespace).append("<").append(argument.getType().getSimpleName()).append(">")
                            .append("\n");
                }
            } else {
                output.append("\n");
            }

            return output.toString();
        };

        private int maxFirst(final List<Argument> arguments) {
            return arguments.stream().map(Argument::toString)
                    .max(Comparator.comparingInt(String::length)).orElse("").length();
        }

        private int maxSecond(final List<Argument> arguments) {
            int max = maxFirst(arguments);

            return arguments.stream().map(new Function<Argument, String>() {
                @Override
                public String apply(Argument argument) {
                    String toString = argument.toString();
                    String whitespace = StringUtil.genWhitespace((max + 1) - toString.length());
                    StringBuilder builder = new StringBuilder();
                    builder.append(" ")
                            .append(toString)
                            .append(whitespace)
                            .append(argument.getDescription());
                    return builder.toString();
                }
            }).max(Comparator.comparingInt(String::length)).orElse("").length();
        }
    };

    private Set<Object> objects;

    private final Set<String> packages = new HashSet<>() {
        {
            add(""); // defaults to all packages on the classpath
        }
    };

    private Set<Class<?>> classes;

    private Bundle bundle;

    private boolean nullifyScanPackages = false;

    private boolean nullifyHelpCommands = false;

    private boolean autoComplete = true;

    public ConfigurationBuilder() {
    }

    /**
     * Configure the Shell to use the specified IConsole.<br/>
     * @param console the console to be used by the Shell to print and read.
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
    public final ConfigurationBuilder setHelpHandler(final Consumer<Command> handler) {
        if (handler != null) {
            this.helpHandler = handler;
        }
        return this;
    }
    /*
    public final ConfigurationBuilder setHelpHandler(final Consumer<Collection<Command>> handler) {
        if (handler != null) {
            this.helpHandler = handler;
        }
        return this;
    }
     */

    /**
     * Configure the Shell to use the specified formatter, to format the output
     * for the Shell's built in help Command.
     * @param formatter the formatter to be used.
     */
    public final ConfigurationBuilder setHelpOutputFormatter(final Function<Command, String> formatter) {
        if (formatter != null) {
            this.helpOutputFormatter = formatter;
        }
        return this;
    }

    /**
     * Configure the Shell to scan the declared classes of the specified Objects for Command annotated Java Methods.
     * @param objects the Objects to be scanned for, and used to invoke any declared Commands.
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
     * Configure the Shell to scan the specified Classes for Command annotated Java Methods.
     * @param classes the Classes to be scanned.
     */
    public final ConfigurationBuilder setScanClasses(final Class<?>... classes) {
        if ((classes != null) && (classes.length != 0)) {
            this.classes = new HashSet<>(Arrays.asList(classes));
        }
        return this;
    }

    /**
     * Configure the Shell to not scan all the packages on the class-path.
     * <br/>
     * By default all packages are scanned.
     */
    public final ConfigurationBuilder nullifyScanPackages() {
        this.nullifyScanPackages = true;
        return this;
    }

    /**
     * Configure the Shell to not include it's built in help Commands.
     */
    public final ConfigurationBuilder nullifyHelpCommands() {
        this.nullifyHelpCommands = true;
        return this;
    }

    public final ConfigurationBuilder disableAutoComplete() {
        this.autoComplete = false;
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

    public Consumer<Command> getHelpHandler() {
        return helpHandler;
    }

    public Function<Command, String> getHelpOutputFormatter() {
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

    public boolean isAutoComplete() {
        return autoComplete;
    }
}