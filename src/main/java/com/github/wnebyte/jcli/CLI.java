package com.github.wnebyte.jcli;

import java.util.*;
import java.lang.reflect.Method;
import java.util.stream.Collectors;
import com.github.wnebyte.jarguments.exception.ParseException;
import com.github.wnebyte.jarguments.factory.ArgumentFactoryBuilder;
import com.github.wnebyte.jcli.annotation.Command;
import com.github.wnebyte.jcli.conf.Configuration;
import com.github.wnebyte.jcli.io.IConsole;
import com.github.wnebyte.jcli.io.IWriter;
import com.github.wnebyte.jcli.processor.*;
import com.github.wnebyte.jcli.filter.PostTransformationFilter;
import com.github.wnebyte.jcli.exception.UnknownCommandException;
import com.github.wnebyte.jcli.val.CommandValidator;
import com.github.wnebyte.jcli.util.Identifier;
import com.github.wnebyte.jcli.util.Objects;

public class CLI {

    static int hash(Object o) {
        return (o != null) ? o.hashCode() : 0;
    }

    private final Configuration conf;

    /**
     * Is used to read and write.
     */
    private final IConsole console;

    private final IWriter writer;

    private final List<BaseCommand> commands;

    private final Set<Integer> prefixes;

    private final HashMap<Integer, BaseCommand> index;

    /**
     * Constructs a new instance using a default <code>Configuration</code>.
     */
    public CLI() {
        this(new Configuration());
    }

    /**
     * Constructs a new instance using the specified <code>Configuration</code>.
     * @param conf to be used.
     */
    public CLI(Configuration conf) {
        this.conf = Objects.requireNonNullElseGet(conf, Configuration::new);
        this.console = conf.getConsole();
        this.writer = console.writer();
        this.prefixes = new HashSet<>();
        this.index = new HashMap<>();
        this.commands = build();
    }

    private List<BaseCommand> build() {
        IMethodScanner scanner = new MethodScanner();
        IInstanceTracker tracker = new InstanceTracker(conf.getDependencyContainer());
        Set<Object> objects = conf.getScanObjects();
        Set<Class<?>> classes = conf.getScanClasses();
        Set<String> packages = conf.getScanPackages();
        Set<Identifier> identifiers = conf.getScanIdentifiers();
        Set<Method> methods = conf.getScanMethods();

        // scan for commands
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
        if (conf.isNullifyHelpCommand()) {
            scanner.removedScannedElementIf(m -> m.getDeclaringClass() == this.getClass());
        }
        else {
            scanner.scanClass(this.getClass());
            tracker.add(this);
        }
        if (conf.getExcludeClasses() != null) {
            scanner.removedScannedElementIf(m -> conf.getExcludeClasses().contains(m.getDeclaringClass()));
        }

        // build commands
        List<BaseCommand> commands = scanner.getScannedElements().stream()
                .map(new MethodTransformationBuilder()
                        .setInstanceTracker(tracker)
                        .setArgumentFactoryBuilder(new ArgumentFactoryBuilder()
                                .useTypeConverterMap(conf.getTypeConverterMap()))
                        .build()
                )
                .filter(Objects::nonNull)
                .filter(new PostTransformationFilter())
                .collect(Collectors.toCollection(ArrayList::new));

        // populate index
        for (BaseCommand cmd : commands) {
            for (String name : cmd.getNames()) {
                int key;

                if (cmd.hasPrefix()) {
                    key = hash(cmd.getPrefix().concat(name));
                    prefixes.add(hash(cmd.getPrefix()));
                } else {
                    key = hash(name);
                }

                index.put(key, cmd);
            }
        }

        return commands;
    }

    public void accept(String input) {
        try {
            BaseCommand cmd = getCommand(input);
            cmd.run(input);
        }
        catch (UnknownCommandException e) {
            writer.printerr(conf.getUnknownCommandExceptionFormatter().apply(e));
        }
        catch (ParseException e) {
            writer.printerr(conf.getParseExceptionFormatter().apply(e));
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
        BaseCommand cmd = getIndexed(input);

        if (cmd != null && new CommandValidator(cmd).validate(input)) {
            return cmd;
        }
        else {
            throw new UnknownCommandException(
                    String.format("'%s' is not recognized as an internal command.", input), input
            );
        }
    }

    protected BaseCommand getIndexed(String input) {
        if (input == null) {
            return null;
        }
        String[] split = input.split("\\s", 2);
        int key = hash(split[0]);

        if (2 <= split.length && prefixes.contains(key)) {
            key = hash(split[0].concat(split[1]));
        }

        return index.get(key);
    }

    @Command(name = "--help, -h")
    protected void help() {
        for (BaseCommand cmd : commands) {
            writer.println(conf.getHelpFormatter().apply(cmd));
        }
    }
}