package com.github.wnebyte.jcli;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Collectors;
import com.github.wnebyte.jarguments.exception.ParseException;
import com.github.wnebyte.jarguments.factory.ArgumentCollectionFactoryBuilder;
import com.github.wnebyte.jcli.annotation.Command;
import com.github.wnebyte.jcli.processor.*;
import com.github.wnebyte.jcli.filter.PostTransformationFilter;
import com.github.wnebyte.jcli.util.Identifier;
import com.github.wnebyte.jcli.util.Objects;
import com.github.wnebyte.jcli.exception.UnknownCommandException;

public class CLI {

    private final Configuration config;

    private final Set<BaseCommand> commands;

    private final IConsole console;

    public CLI() {
        this(null);
    }

    public CLI(Configuration config) {
        this.config = Objects.requireNonNullElseGet(config, Configuration::new);
        this.console = config.getConsole();
        this.commands = build();
    }

    private Set<BaseCommand> build() {
        IMethodScanner scanner = new MethodScanner();
        IInstanceTracker tracker = new InstanceTracker(config.getDependencyContainer());
        Set<Object> objects = config.getScanObjects();
        Set<Class<?>> classes = config.getScanClasses();
        Set<String> packages = config.getScanPackages();
        Set<Identifier> identifiers = config.getScanIdentifiers();
        Set<Method> methods = config.getScanMethods();

        if (objects != null) {
            scanner.scanObjects(objects);
            tracker.addAll(objects);
        }
        if (classes != null) {
            scanner.scanClasses(classes);
        }
        if (packages != null) {
            scanner.scanUrls(packages);
        }
        if (identifiers != null) {
            scanner.scanMethods(identifiers.stream().map(Identifier::getMethod).collect(Collectors.toSet()));
        }
        if (methods !=  null) {
            scanner.scanMethods(methods);
        }
        if (config.isNullifyHelpCommand()) {
            scanner.removedScannedElementIf(m -> m.getDeclaringClass() == this.getClass());
        }
        else {
            scanner.scanClass(this.getClass());
            tracker.add(this);
        }
        if (config.getExcludeClasses() != null) {
            scanner.removedScannedElementIf(m -> config.getExcludeClasses().contains(m.getDeclaringClass()));
        }

        Set<BaseCommand> commands = scanner.getScannedElements().stream()
                .map(new MethodTransformationBuilder()
                        .setInstanceTracker(tracker)
                        .setArgumentCollectionFactoryBuilder(new ArgumentCollectionFactoryBuilder()
                                .useTypeConverterMap(config.getTypeConverterMap()))
                        .build()
                )
                .filter(Objects::nonNull)
                .filter(new PostTransformationFilter())
                .collect(Collectors.toSet());
        return commands;
    }

    public void accept(String input) {
        try {
            BaseCommand cmd = getCommand(input);
            cmd.run(input);
        }
        catch (UnknownCommandException e) {
            BaseCommand cmd = commandHelp(input);
            if (cmd != null) {
                console.println(config.getHelpFormatter().apply(cmd));
            } else {
                console.printerr(config.getUnknownCommandExceptionFormatter().apply(e));
            }
        }
        catch (ParseException e) {
            console.printerr(config.getParseExceptionFormatter().apply(e));
        }
    }

    public void read() {
        while (true) {
            String input = console.read();

            if (input != null) {
                accept(input);
            } else {
                break;
            }
        }
    }

    protected BaseCommand getCommand(String input) throws UnknownCommandException {
        for (BaseCommand cmd : commands) {
            if (cmd.getPattern().matcher(input).matches()) {
                return cmd;
            }
         }
        throw new UnknownCommandException(
                "'" + input + "' is not recognized as an internal command."
        );
    }

    protected BaseCommand commandHelp(String input) {
        String[] arr = input.split("\\s");

        if (arr[arr.length - 1].equals("--help")) {
            BaseCommand cmd = null;

            if (arr.length == 2) {
                String name = arr[0];
                cmd = commands.stream().filter(c -> c.getNames().contains(name))
                        .findFirst().orElse(null);
            }
            else if (arr.length == 3) {
                String prefix = arr[0];
                String name = arr[1];
                cmd = commands.stream().filter(c -> c.getPrefix().equals(prefix) && c.getNames().contains(name))
                        .findFirst().orElse(null);
            }

            return cmd;
        }

        return null;
    }

    @Command(name = "--help, -h")
    protected void help() {
        for (BaseCommand cmd : commands) {
            console.println(config.getHelpFormatter().apply(cmd));
        }
    }
}