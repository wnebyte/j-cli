package core;

import exception.runtime.ParseException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

import static util.StringUtil.whitespace;

public class ConfigurationBuilder {

    private IConsole console;

    private Consumer<String> noSuchCommandHandler;

    private Function<String, String> unknownCommandOutputFormatter = new Function<>() {
        @Override
        public String apply(String input) {
            return "'".concat(input).concat("'").concat(" is not recognized as an internal command.");
        }
    };

    private Consumer<ParseException> parseExceptionHandler;

    private Function<ParseException, String> parseExceptionOutputFormatter = new Function<>() {
        @Override
        public String apply(ParseException e) {
            return e.getArgument().getType().getSimpleName().toUpperCase() +
                    " could not be parsed from '" + e.getRaw() + "'";
        }
    };

    private Consumer<Collection<Command>> helpHandler;

    private Function<Collection<core.Command>, String> helpOutputFormatter = new Function<>() {
        @Override
        public String apply(Collection<Command> commands) {
            StringBuilder output = new StringBuilder();
            int max = commands.stream().mapToInt(command -> command.toString().length()).max()
                    .orElse(0) + 1;
            int i = 0;

            for (Command c : commands) {
                String whitespace = whitespace(max - c.toString().length());
                output.append(c).append(whitespace).append(c.getDescription());
                String whitespaceln = whitespace(max);

                c.getArguments().forEach(arg -> {
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

    private Set<Object> controllers;

    private Set<String> packages = new HashSet<>() {
        {
            add(""); // defaults to all packages on the classpath
        }
    };

    private boolean nullifyScanPackages = false;

    private boolean nullifyHelpCommands = false;

    public ConfigurationBuilder() {
    }

    public final ConfigurationBuilder setConsole(final IConsole console) {
        this.console = console;
        return this;
    }

    public final ConfigurationBuilder setUnknownCommandOutputFormatter(final Function<String, String> formatter) {
        if (formatter != null) {
            this.unknownCommandOutputFormatter = formatter;
        }
        return this;
    }

    public final ConfigurationBuilder setUnknownCommandHandler(final Consumer<String> handler) {
        if (handler != null) {
            this.noSuchCommandHandler = handler;
        }
        return this;
    }

    public final ConfigurationBuilder setParseExceptionHandler(final Consumer<ParseException> handler) {
        if (handler != null) {
            this.parseExceptionHandler = handler;
        }
        return this;
    }

    public final ConfigurationBuilder setParseExceptionOutputFormatter(final Function<ParseException, String> formatter) {
        if (formatter != null) {
            this.parseExceptionOutputFormatter = formatter;
        }
        return this;
    }

    public final ConfigurationBuilder setHelpHandler(final Consumer<Collection<Command>> handler) {
        if (handler != null) {
            this.helpHandler = handler;
        }
        return this;
    }

    public final ConfigurationBuilder setHelpOutputFormatter(final Function<Collection<Command>, String> formatter) {
        if (formatter != null) {
            this.helpOutputFormatter = formatter;
        }
        return this;
    }

    public final ConfigurationBuilder setScanPackages(final String... packages) {
        if ((packages != null) && (packages.length != 0)) {
            this.packages = new HashSet<>(Arrays.asList(packages));
        }
        return this;
    }

    public final ConfigurationBuilder setScanObjects(final Object... objects) {
        if ((objects != null) && (objects.length != 0)) {
            this.controllers = new HashSet<>(Arrays.asList(objects));
        }
        return this;
    }

    /**
     * Specify that no packages be scanned. By default all packages are scanned.
     */
    public final ConfigurationBuilder setNullifyScanPackages() {
        this.nullifyScanPackages = true;
        return this;
    }

    public final ConfigurationBuilder setNullifyHelpCommands() {
        this.nullifyHelpCommands = true;
        return this;
    }

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

    public Set<Object> getControllers() {
        return controllers;
    }

    public boolean isNullifyScanPackages() {
        return nullifyScanPackages;
    }

    public boolean isNullifyHelpCommands() {
        return nullifyHelpCommands;
    }
}