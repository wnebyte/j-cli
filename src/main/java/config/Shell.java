package config;

import annotation.Argument;
import annotation.Command;
import exception.config.ConfigException;
import exception.runtime.ParseException;
import core.AnnotationProcessor;
import core.IConsole;
import core.Positional;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static util.ReflectionUtil.isNested;
import static util.ReflectionUtil.isStatic;

/**
 * @author wnebyte.
 */
public final class Shell {

    /** annotation processor */
    private final AnnotationProcessor annotationProcessor = new AnnotationProcessor();

    /** processed commands */
    private Map<String, core.Command> commands;

    /** configuration */
    private final config.ConfigurationBuilder config;

    public Shell(config.ConfigurationBuilder config) {
        this.config = Objects.requireNonNullElseGet(config, () -> new config.ConfigurationBuilder());
        build();
    }

    private Shell() {
        throw new UnsupportedOperationException(
                "Constructor is not supported."
        );
    }

    private void build() {
        Scanner scanner = new Scanner();
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
            } catch (ConfigException e) {
                e.printStackTrace();
                return null;
            }
        }).takeWhile(Objects::nonNull).collect(Collectors.toSet());

        try {
            this.commands = annotationProcessor.process(commands);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void accept(String input) {
        try {
            match(input).call(input);
        } catch (NullPointerException e) {
            handleNoSuchCommand(input);
        } catch (ParseException ex) {
            handleParseException(ex);
        }
    }

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
     * Handles a <code>NoSuchCommand</code> event at 'runtime' in accordance with the specifications of the
     * <code>config</code>.
     * @param input the supplied userInput.
     */
    private void handleNoSuchCommand(String input) {
        IConsole console = config.getConsole();
        Consumer<String> handler = config.getNoSuchCommandHandler();
        Function<String, String> formatter = config.getNoSuchCommandOutputFormatter();

        if (handler != null) {
            handler.accept(input);
        } else if (console != null) {
            console.printerr(formatter.apply(input));
        }
    }

    /**
     * Handles a <code>ParseException</code> being thrown at 'runtime' in accordance with the specifications of the
     * <code>config</code>.
     * @param e the thrown <code>ParseException</code>.
     */
    private void handleParseException(ParseException e) {
        IConsole console = config.getConsole();
        Consumer<ParseException> handler = config.getParseExceptionHandler();
        Function<ParseException, String> formatter = config.getParseExceptionOutputFormatter();

        if (handler != null) {
            handler.accept(e);
        } else if (console != null) {
            console.printerr(formatter.apply(e));
        }
    }

    private core.Command match(String input) throws NullPointerException {
        return commands.entrySet().stream().filter(kv -> Pattern.compile(kv.getKey()).matcher(input).matches())
                .findFirst().orElseThrow(NullPointerException::new).getValue();
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