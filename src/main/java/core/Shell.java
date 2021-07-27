package core;

import annotation.Argument;
import annotation.Command;
import exception.config.ConfigException;
import exception.config.IllegalAnnotationException;
import exception.runtime.ParseException;
import exception.runtime.UnknownCommandException;
import struct.BiImmutableMap;
import util.InstanceTracker;
import util.Scanner;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static util.ReflectionUtil.isNested;
import static util.ReflectionUtil.isStatic;

public class Shell {

    /** Annotation processor */
    private final AnnotationProcessor annotationProcessor = new AnnotationProcessor();

    /** Commands */
    private Map<String, core.Command> commands;

    /** Configuration object */
    protected final ConfigurationBuilder config;

    /**
     * This class's primary constructor.
     * @param config the <code>ConfigurationBuilder</code> object to be used when building the shell.
     */
    public Shell(ConfigurationBuilder config) {
        this.config = Objects.requireNonNullElseGet(config, ConfigurationBuilder::new);
        build();
    }

    /**
     * Unsupported constructor.
     */
    private Shell() {
        throw new UnsupportedOperationException(
                "Constructor is not supported."
        );
    }

    /**
     * Builds the shell in accordance with the properties of the specified <code>ConfigurationBuilder</code>.
     */
    private void build() {
        util.Scanner scanner = new Scanner();
        InstanceTracker tracker = (config.getControllers() != null) ?
                new InstanceTracker(config.getControllers()) :
                new InstanceTracker();

        if (config.getConsole() != null) {
            tracker.setInjectable(config.getConsole());
        }
        if (config.getControllers() != null) {
            scanner.scan(config.getControllers());
        }
        if (!(config.isNullifyScanPackages()) && (config.getPackages() != null)) {
            scanner.scan(config.getPackages());
        }
        if (config.isNullifyHelpCommands()) {
            scanner.removeIf(method -> method.getDeclaringClass() == this.getClass());
        } else {
            tracker.add(this);
        }

        Set<core.Command> commands = scanner.getScanned().stream().map(method -> {
            try {
                if (isNested(method.getDeclaringClass())) {
                    throw new ConfigException(
                            "\n\tA Command may not be declared within a nested class."
                    );
                }
                Object object = isStatic(method) ? null : tracker.add(method.getDeclaringClass());
                return new core.Command(object, method);
            }
            catch (ConfigException e) {
                e.printStackTrace();
                return null;
            }
        }).takeWhile(Objects::nonNull).collect(Collectors.toSet());

        try {
            this.commands = annotationProcessor.process(commands);
        }
        catch (IllegalAnnotationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Accepts input from the user.
     * @param input the input to match against a registered <code>Command</code>.
     */
    public final void accept(String input) {
        try {
            match(input).invoke(input);
        }
        catch (UnknownCommandException e) {
            handleUnknownCommandException(input);
        }
        catch (ParseException e) {
            handleParseException(e);
        }
    }

    /**
     * Calls {@link Shell#accept(String)} in a continuous loop until the shell reads 'exit'.<br/>
     * @throws IllegalStateException if no {@linkplain core.IConsole} implementation has been specified via
     * this object's <code>ConfigurationBuilder</code>.
     */
    public final void run() {
        IConsole console = config.getConsole();
        boolean cont = true;

        if (console == null) {
            throw new IllegalStateException(
                    "no console has been been specified."
            );
        }
        while (cont) {
            String in = console.read();
            if (cont=((in != null) && !(in.equals("exit")))) {
                accept(in);
            }
        }
    }

    public final Consumer<String> toConsumer() {
        return this::accept;
    }

    /**
     * Handles a <code>UnknownCommandException</code> at "runtime" in accordance with the settings specified by this
     * class's <code>ConfigurationBuilder</code> object.
     * @param input the input received from the user.
     */
    protected void handleUnknownCommandException(String input) {
        IConsole console = config.getConsole();
        Consumer<String> handler = config.getNoSuchCommandHandler();
        Function<String, String> formatter = config.getUnknownCommandOutputFormatter();

        if (handler != null) {
            handler.accept(input);
        }
        else if (console != null) {
            console.printerr(formatter.apply(input));
        }
    }

    /**
     * Handles a <code>ParseException</code> at "runtime" in accordance with the settings specified by this
     * class's <code>ConfigurationBuilder</code> object.
     * @param e the thrown <code>ParseException</code>.
     */
    protected void handleParseException(ParseException e) {
        IConsole console = config.getConsole();
        Consumer<ParseException> handler = config.getParseExceptionHandler();
        Function<ParseException, String> formatter = config.getParseExceptionOutputFormatter();

        if (handler != null) {
            handler.accept(e);
        }
        else if (console != null) {
            console.printerr(formatter.apply(e));
        }
    }

    /**
     * Attempts to match the specified input with a known <code>Command</code>.
     * @param input the input received from the user.
     * @return The <code>Command</code> associated with the specified input, if one exists,
     * otherwise throws an <code>UnknownCommandException</code>.
     */
    protected core.Command match(String input) throws UnknownCommandException {
        return commands.entrySet().stream().filter(kv -> Pattern.compile(kv.getKey()).matcher(input).matches())
                .findFirst().orElseThrow(UnknownCommandException::new)
                .getValue();
    }

    @Command(name = "--help")
    private void help() {
        IConsole console = config.getConsole();
        Consumer<Collection<core.Command>> handler = config.getHelpHandler();
        Function<Collection<core.Command>, String> formatter = config.getHelpOutputFormatter();

        if (handler != null) {
            handler.accept(commands.values().stream()
                    .filter(c -> c.getDeclaringClass() != this.getClass()).collect(Collectors.toList()));
        }
        else if (console != null) {
            console.println(formatter.apply(commands.values().stream()
                    .filter(c -> c.getDeclaringClass() != this.getClass()).collect(Collectors.toList())));
        }
    }

    @Command(name = "--help")
    private void help(@Argument(name = "n", type = Positional.class) String n) {
        IConsole console = config.getConsole();
        Consumer<Collection<core.Command>> handler = config.getHelpHandler();
        Function<Collection<core.Command>, String> formatter = config.getHelpOutputFormatter();

        if (handler != null) {
            handler.accept(commands.values().stream().filter(c -> c.getName().replace("-", "").equals(n))
                    .collect(Collectors.toList()));
        }
        else if (console != null) {
            console.println(
                    formatter.apply(commands.values().stream().filter(c -> c.getName().replace("-", "").equals(n))
                            .collect(Collectors.toList()))
            );
        }
    }

    @Command(name = "-h")
    private void h() {
        help();
    }

    @Command(name = "-h")
    private void h(@Argument(name = "n", type = Positional.class) String n) {
        help(n);
    }
}