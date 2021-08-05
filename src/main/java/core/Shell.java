package core;

import annotation.Argument;
import annotation.Command;
import exception.config.ConfigException;
import exception.config.IllegalAnnotationException;
import exception.runtime.ParseException;
import exception.runtime.UnknownCommandException;
import util.InstanceTracker;
import util.Scanner;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static util.ReflectionUtil.isNested;
import static util.ReflectionUtil.isStatic;

public final class Shell {

    /** Annotation processor */
    private final AnnotationProcessor annotationProcessor = new AnnotationProcessor();

    /** Commands */
    private Map<String, core.Command> commands;

    private Map<Pattern, core.Command> testCommands;

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
     * Builds the Shell in accordance with the specified Configuration.
     */
    private void build() {
        Scanner scanner = new Scanner();
        InstanceTracker tracker = (config.getObjects() != null) ?
                new InstanceTracker(config.getObjects()) :
                new InstanceTracker();

        if (config.getConsole() != null) {
            tracker.setInjectable(config.getConsole());
        }
        if (config.getObjects() != null) {
            scanner.scanObjects(config.getObjects());
        }
        if (config.getClasses() != null) {
            scanner.scanClasses(config.getClasses());
        }
        if (!(config.isNullifyScanPackages()) && (config.getPackages() != null)) {
            scanner.scanURLS(config.getPackages());
        }
        if (config.getBundle() != null) {
            scanner.scanBundle(config.getBundle());
            tracker.addObject(config.getBundle().getOwner());
        }
        if (config.isNullifyHelpCommands()) {
            scanner.removeIf(method -> method.getDeclaringClass() == this.getClass());
        } else {
            tracker.addObject(this);
        }

        Set<core.Command> commands = scanner.getScanned().stream().map(method -> {
            try {
                Class<?> cls = method.getDeclaringClass();
                if ((isNested(cls)) && !(isStatic(cls))) {
                    throw new ConfigException(
                            "\n\tA Command may not be declared within a nested class that is non static."
                    );
                }
                Object object = isStatic(method) ?
                        null :
                        tracker.addClass(cls);
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
     * @param input the input to match against a known <code>Command</code>.
     */
    public final void accept(String input) {
        try {
            match(input).execute(input);
        }
        catch (UnknownCommandException e) {
            handleUnknownCommandException(input);
        }
        catch (ParseException e) {
            handleParseException(e);
        }
    }

    /**
     * Attempts to read from the specified {@link IConsole} until it returns <code>null</code> or "exit",
     * and calls {@link Shell#accept(String)}, in a continuous loop.
     * @throws IllegalStateException if no IConsole implementation has been specified.
     */
    public final void run() {
        IConsole console = config.getConsole();
        boolean cont = true;

        if (console == null) {
            throw new IllegalStateException(
                    "No console has been been specified."
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
     * Handles an {@link UnknownCommandException} in accordance with the specified Configuration.
     * @param input the input received from the user.
     */
    private void handleUnknownCommandException(String input) {
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
     * Handles a {@link ParseException} in accordance with the specified Configuration.
     * @param e the thrown ParseException.
     */
    private void handleParseException(ParseException e) {
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
     * Attempts to match the specified input with a known Command.
     * @param input the input received from the user.
     * @return The Command associated with the specified input, if one exists,
     * otherwise throws an UnknownCommandException.
     */
    private core.Command match(String input) throws UnknownCommandException {
        return commands.entrySet().stream().filter(kv -> Pattern.compile(kv.getKey()).matcher(input).matches())
                .findFirst().orElseThrow(UnknownCommandException::new)
                .getValue();
    }

    private core.Command matchTest(final String input) throws UnknownCommandException {
        for (Map.Entry<Pattern, core.Command> kv : testCommands.entrySet()) {
            if (kv.getKey().matcher(input).matches()) {
                return kv.getValue();
            }
        }
        throw new UnknownCommandException(
                "UnknownCommand"
        );
    }

    public final Set<String> getCommandKeys() {
        return commands.keySet();
    }

    @Command(name = "--help", description = "help command")
    private void help(
            @Argument(name = "-n", type = Optional.class)
            String name
    ) {
        IConsole console = config.getConsole();
        Consumer<core.Command> handler = config.getHelpHandler();
        Function<core.Command, String> formatter = config.getHelpOutputFormatter();

        // if a handler has been set
        if (handler != null) {
            commands.values().stream()
                    .filter(command -> (command.getDeclaringClass() != this.getClass()) &&
                            (name == null || command.getName().equals(name) || command.getPrefix().equals(name)))
                    .forEach(handler);
        }
        // else if a console has been set
        else if (console != null) {
            commands.values().stream()
                    .filter(command -> (command.getDeclaringClass() != this.getClass()) &&
                            (name == null || command.getName().equals(name) || command.getPrefix().equals(name)))
                    .forEach(command -> console.println(formatter.apply(command)));
        }
    }

    @Command(name = "-h")
    private void h(
            @Argument(name = "-n", type = Optional.class)
            String name
    ) {
        help(name);
    }
}