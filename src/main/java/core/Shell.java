package core;

import exception.config.ConfigException;
import exception.config.IllegalAnnotationException;
import exception.runtime.ParseException;
import exception.runtime.UnknownCommandException;
import util.CollectionUtil;
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

    // lateinit
    private Map<Pattern, Command> commands = new HashMap<>();

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
        AnnotationProcessor annotationProcessor = new AnnotationProcessor();
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
            scanner.scanClasses(Set.of(Shell.class));
        }

        Set<Command> commands = scanner.getScanned().stream().map(method -> {
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
                return new Command(object, method);
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
    private void handleUnknownCommandException(final String input) {
        IConsole console = config.getConsole();
        Consumer<String> handler = config.getNoSuchCommandHandler();
        Function<String, String> formatter = config.getUnknownCommandOutputFormatter();

        if (handler != null) {
            handler.accept(input);
        }
        else if (console != null) {
            console.printerr(formatter.apply(input));

            if (config.isAutoComplete()) {
                Command command = getBestGuess(input);

                if (command != null) {
                    console.println(config.getHelpOutputFormatter().apply(command));
                }
            }
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


    private Command match(final String input) throws UnknownCommandException {
        for (Map.Entry<Pattern, Command> kv : commands.entrySet()) {
            if (kv.getKey().matcher(input).matches()) {
                return kv.getValue();
            }
        }
        throw new UnknownCommandException();
    }

    /*
    private Command match(final String input) throws UnknownCommandException {
        for (Map.Entry<String, Command> kv : commands.entrySet()) {
            if (Pattern.compile(kv.getKey()).matcher(input).matches()) {
                return kv.getValue();
            }
        }
        throw new UnknownCommandException();
    }
     */

    private Command getBestGuess(final String input) {
        float max = 0f;
        Command cmd = null;

        for (Command command : commands.values()) {
            float tmp = command.getLikeness(input);
            if (max < tmp) {
                max = tmp;
                cmd = command;
            }
        }

        return cmd;
    }

    @annotation.Command(
            name = "--help", description = "lists all commands"
    )
    private void list(
            @annotation.Argument(
                    name = "-name", type = Optional.class, description = "name of command"
            )
            String name,
            @annotation.Argument
                    (name = "-prefix", type = Optional.class, description = "prefix of command"
                    )
            String prefix,
            @annotation.Argument(
                    name = "-args", type = Optional.class, description = "args of command"
            )
            String[] args
    ) {
        IConsole console = config.getConsole();
        Function<Command, String> formatter = config.getHelpOutputFormatter();

        if (console != null) {
            Collection<Command> commands = this.commands.values();

            if (name != null) {
                commands = commands.stream()
                        .filter(command -> command.getName().equals(name)).collect(Collectors.toList());
            }
            if (prefix != null) {
                commands = commands.stream()
                        .filter(command -> command.getPrefix().equals(prefix)).collect(Collectors.toList());
            }
            if ((args != null) && (args.length != 0)) {
                List<String> argsAsList = Arrays.asList(args);
                commands = commands.stream()
                        .filter(command -> CollectionUtil.intersection(argsAsList, command.getArguments()
                                .stream().map(Argument::getName).collect(Collectors.toList()))
                                .size() == args.length).collect(Collectors.toList());
            }
            commands.forEach(command -> console.println(formatter.apply(command)));
        }
    }
}