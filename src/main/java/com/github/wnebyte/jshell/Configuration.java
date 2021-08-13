package com.github.wnebyte.jshell;

import com.github.wnebyte.jshell.exception.runtime.ParseException;
import com.github.wnebyte.jshell.util.Bundle;
import com.github.wnebyte.jshell.util.StringUtil;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * This class is used to specify configuration options for an instance of the {@link Shell} class.
 */
public class Configuration {

    private IConsole console;

    private Consumer<String> unknownCommandHandler;

    private Function<String, String> unknownCommandOutputFormatter = new Function<String, String>() {
        // default implementation
        @Override
        public String apply(String input) {
            return "'".concat(input).concat("'").concat(" is not recognized as an internal command.");
        }
    };

    private Consumer<ParseException> parseExceptionHandler;

    private Function<ParseException, String> parseExceptionOutputFormatter =
            new Function<ParseException, String>() {
        // default implementation
        @Override
        public String apply(ParseException e) {
            return "could not convert '" + e.getValue() + "' into <"
                    + e.getArgument().getType().getSimpleName() + ">";
        }
    };

    private Consumer<Command> helpHandler;

    private Function<Command, String> helpOutputFormatter = new Function<Command, String>() {
        // default implementation
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
                            .generateWhitespaces((maxFirst + 1) - toString.length());
                    String ln = new StringBuilder().append(" ").append(toString).append(whitespace)
                            .append(argument.getDescription()).toString();
                    output.append(ln);
                    whitespace = StringUtil
                            .generateWhitespaces((maxSecond + 1) - ln.length());
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
                    String whitespace = StringUtil.generateWhitespaces((max + 1) - toString.length());
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

    private final Set<String> packages = new HashSet<String>() {
        {
            add(""); // defaults to all packages on the classpath
        }
    };

    private Set<Class<?>> classes;

    private Bundle bundle;

    private boolean nullifyScanPackages = false;

    private boolean nullifyHelpCommands = false;

    private boolean suggestCommand = true;

    public Configuration() {
    }

    /**
     * <p>Configure the Shell to use the specified Console.</p>
     * Does not have to be set, but the Shell will not be able to output text and/or handle certain events,
     * it is recommended to set all the handlers if a Console is not specified.
     * @param console the console to be used by the Shell to read and output text.
     */
    public final Configuration setConsole(final IConsole console) {
        this.console = console;
        return this;
    }

    /**
     * Configure the Shell to use the specified formatter when an UnknownCommandException is thrown,
     * and setConsole() has been set, and the associated handler has not been set.
     * @param formatter the formatter to be used.
     */
    public final Configuration setUnknownCommandOutputFormatter(final Function<String, String> formatter) {
        if (formatter != null) {
            this.unknownCommandOutputFormatter = formatter;
        }
        return this;
    }

    /**
     * Configure the Shell to use the specified handler when an UnknownCommandException is thrown.
     * @param handler the handler to be used.
     */
    public final Configuration setUnknownCommandHandler(final Consumer<String> handler) {
        if (handler != null) {
            this.unknownCommandHandler = handler;
        }
        return this;
    }

    /**
     * Configure the Shell to use the specified handler when a ParseException is thrown.
     * @param handler the handler to be used.
     */
    public final Configuration setParseExceptionHandler(final Consumer<ParseException> handler) {
        if (handler != null) {
            this.parseExceptionHandler = handler;
        }
        return this;
    }

    /**
     * Configure the Shell to use the specified formatter when a ParseException is thrown,
     * and setConsole() has been set, and the associated handler has not been set.
     * @param formatter the formatter to be used.
     */
    public final Configuration setParseExceptionOutputFormatter(final Function<ParseException, String> formatter) {
        if (formatter != null) {
            this.parseExceptionOutputFormatter = formatter;
        }
        return this;
    }


    /**
     * Configure the Shell to use the specified handler when the Shell's Help Command has been successfully matched.
     * @param handler the handler to be used.
     */
    public final Configuration setHelpHandler(final Consumer<Command> handler) {
        if (handler != null) {
            this.helpHandler = handler;
        }
        return this;
    }

     /**
     * Configure the Shell to use the specified formatter when the Shell's Help Command has been successfully matched,
     * and setConsole() has been set, and the associated handler has not been set.
     * @param formatter the formatter to be used.
     */
    public final Configuration setHelpOutputFormatter(final Function<Command, String> formatter) {
        if (formatter != null) {
            this.helpOutputFormatter = formatter;
        }
        return this;
    }

    /**
     * <p>Configure the Shell to scan the class of the specified Objects for
     * {@linkplain com.github.wnebyte.jshell.annotation.Command} annotated Java Methods.</p>
     * The specified Objects will also be used when the Shell invokes any underlying annotated
     * Java Methods.
     * @param objects the Objects whose classes are to be scanned.
     */
    public final Configuration setScanObjects(final Object... objects) {
        if ((objects != null) && (objects.length != 0)) {
            this.objects = new HashSet<>(Arrays.asList(objects));
        }
        return this;
    }

    /**
     * Configure the Shell to scan the specified Bundle.
     */
    public final Configuration setScanBundles(final Bundle bundle) {
        if (bundle != null) {
            this.bundle = bundle;
        }
        return this;
    }

    /**
     * Configure the Shell to scan the specified classes for
     * {@linkplain com.github.wnebyte.jshell.annotation.Command} annotated Java Methods.
     * @param classes the classes to be scanned.
     */
    public final Configuration setScanClasses(final Class<?>... classes) {
        if ((classes != null) && (classes.length != 0)) {
            this.classes = new HashSet<>(Arrays.asList(classes));
        }
        return this;
    }

    /**
     * Configure the Shell to abstain from scanning the class-path for
     * {@linkplain com.github.wnebyte.jshell.annotation.Command} annotated Java Methods.
     */
    public final Configuration nullifyScanPackages() {
        this.nullifyScanPackages = true;
        return this;
    }

    /**
     * Configure the Shell to not include it's Help Command.
     */
    public final Configuration nullifyHelpCommands() {
        this.nullifyHelpCommands = true;
        return this;
    }

    /**
     * Configure the Shell to not suggest a Command when handling an UnknownCommandException.
     */
    public final Configuration nullifySuggestCommand() {
        this.suggestCommand = false;
        return this;
    }

    /**
     * Configure a TypeConverter to be registered.
     */
    public final <T> Configuration
    registerTypeConverter(final Class<T> typeOf, final TypeConverter<T> typeConverter) {
        if ((typeOf != null) && (typeConverter != null)) {
            TypeConverterRepository.putIfAbsent(typeOf, typeConverter);
        }
        return this;
    }

    /**
     * @return the Console to be used by the Shell.
     */
    public IConsole getConsole() {
        return console;
    }

    /**
     * @return the handler to be used by the Shell when an UnknownCommandException is thrown.
     */
    public Consumer<String> getUnknownCommandHandler() {
        return unknownCommandHandler;
    }

    /**
     * @return the formatter to be used by the Shell when an UnknownCommandException is thrown.
     */
    public Function<String, String> getUnknownCommandOutputFormatter() {
        return unknownCommandOutputFormatter;
    }

    /**
     * @return the handler to be used by the Shell when a ParseException is thrown.
     */
    public Consumer<ParseException> getParseExceptionHandler() {
        return parseExceptionHandler;
    }

    /**
     * @return the formatter to be used by the Shell when a ParseException is thrown.
     */
    public Function<ParseException, String> getParseExceptionOutputFormatter() {
        return parseExceptionOutputFormatter;
    }

    /**
     * @return the handler to be used by the Shell's Help Command.
     */
    public Consumer<Command> getHelpHandler() {
        return helpHandler;
    }

    /**
     * @return the formatter to be used by the Shell's Help Command.
     */
    public Function<Command, String> getHelpOutputFormatter() {
        return helpOutputFormatter;
    }

    /**
     * @return the Set of packages that are to be scanned.
     */
    public Set<String> getPackages() {
        return packages;
    }

    /**
     * @return the Set of Objects that are to be scanned.
     */
    public Set<Object> getObjects() {
        return objects;
    }

    /**
     * @return the Set of classes that are to be scanned.
     */
    public Set<Class<?>> getClasses() {
        return classes;
    }

    /**
     * @return the Bundle that is to be scanned.
     */
    public Bundle getBundle() { return bundle; }

    /**
     * @return <code>true</code> if the Shell is configured to abstain from scanning the class-path,
     * otherwise <code>false</code>.
     */
    public boolean isNullifyScanPackages() {
        return nullifyScanPackages;
    }


    /**
     * @return <code>true</code> if the Shell is configured to include it's Help Command,
     * otherwise <code>false</code>.
     */
    public boolean isNullifyHelpCommands() {
        return nullifyHelpCommands;
    }

    /**
     * @return <code>true</code> if the Shell is configured to suggest a Command when handling
     * an UnknownCommandException, otherwise <code>false</code>.
     */
    public boolean isSuggestCommand() {
        return suggestCommand;
    }
}