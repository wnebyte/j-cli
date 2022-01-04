package com.github.wnebyte.jcli;

import java.util.*;
import java.lang.reflect.Method;
import java.util.stream.Collectors;
import com.github.wnebyte.jarguments.Argument;
import com.github.wnebyte.jarguments.Tokens;
import com.github.wnebyte.jarguments.exception.ParseException;
import com.github.wnebyte.jarguments.factory.ArgumentFactoryBuilder;
import com.github.wnebyte.jarguments.parser.AbstractParser;
import com.github.wnebyte.jarguments.parser.Parser;
import com.github.wnebyte.jcli.annotation.Command;
import com.github.wnebyte.jcli.conf.Configuration;
import com.github.wnebyte.jcli.io.IConsole;
import com.github.wnebyte.jcli.io.IWriter;
import com.github.wnebyte.jcli.processor.*;
import com.github.wnebyte.jcli.processor.FilterImpl;
import com.github.wnebyte.jcli.exception.UnknownCommandException;
import com.github.wnebyte.jcli.util.Identifier;
import com.github.wnebyte.jcli.util.Objects;

public class CLI {

    /*
    ###########################
    #     UTILITY METHODS     #
    ###########################
    */

    static int hash(Object o) {
        return (o != null) ? o.hashCode() : 0;
    }

    static Tokens slice(Tokens tokens, BaseCommand cmd) {
        return tokens.subTokens(cmd.hasPrefix() ? 2 : 1, tokens.size());
    }

    static boolean isHelp(Tokens tokens) {
        return (tokens != null) && (tokens.size() == 1) && (tokens.get(0).equals("--help") || tokens.get(0).equals("-h"));
    }

    /*
    ###########################
    #          FIELDS         #
    ###########################
    */

    private final Configuration conf;

    private final IConsole console;

    private final IWriter out;

    private final List<BaseCommand> commands;

    private final Set<Integer> prefixes;

    private final HashMap<Integer, List<BaseCommand>> index;

    private final AbstractParser<Tokens, Collection<Argument>> parser = new Parser();

    /*
    ###########################
    #       CONSTRUCTORS      #
    ###########################
    */

    /**
     * Constructs a new instance with a default <code>Configuration</code>.
     */
    public CLI() {
        this(null);
    }

    /**
     * Constructs a new instance with the specified <code>Configuration</code>.
     * @param conf configuration to be used.
     */
    public CLI(Configuration conf) {
        this.conf = Objects.requireNonNullElseGet(conf, Configuration::new);
        this.console = conf.getConsole();
        this.out = console.writer();
        this.prefixes = new HashSet<>();
        this.index = new HashMap<>();
        this.commands = build();
    }

    /*
    ###########################
    #         METHODS         #
    ###########################
    */

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
        if (methods != null) {
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
                .filter(new FilterImpl())
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

                if (index.containsKey(key)) {
                    index.get(key).add(cmd);
                } else {
                    index.put(key, new ArrayList<BaseCommand>(){{add(cmd);}});
                }

            }
        }

        commands.sort(BaseCommand::compareTo);

        return commands;
    }

    public void accept(String input) {
        Tokens tokens = Tokens.tokenize(input);

        try {
            BaseCommand cmd = lookup(tokens);
            Object[] args = parser.initialize();
            cmd.execute(args);
        }
        catch (UnknownCommandException e) {
            out.printerr(conf.getUnknownCommandExceptionFormatter().apply(e));
        }
        catch (ParseException e) {
            out.printerr(conf.getParseExceptionFormatter().apply(e));
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

    protected BaseCommand lookup(Tokens tokens) throws UnknownCommandException {
        List<BaseCommand> c = getBucket(tokens); // will only contain one element.
        String input = tokens.join();

        for (BaseCommand cmd : c) {
            tokens = slice(tokens, cmd);
            try {
                parser.parse(tokens, cmd.getArguments());
                return cmd;
            }
            catch (ParseException e) {
                if (isHelp(tokens)) {
                    parser.reset();
                    return BaseCommand.stub((args) -> out.println(conf.getHelpFormatter().apply(cmd)));
                }
            }
        }

        throw new UnknownCommandException(
                String.format("'%s' is not recognized as an internal command.", input), input
        );

    }

    protected List<BaseCommand> getBucket(Tokens tokens) {
        if (tokens == null) {
            return Collections.emptyList();
        }
        int key = toKey(tokens);
        List<BaseCommand> c = index.get(key);
        return (c != null) ? c : Collections.emptyList();
    }

    protected int toKey(Tokens tokens) {
        int key = hash(tokens.get(0));

        if (2 <= tokens.size() && prefixes.contains(key)) {
            key = hash(tokens.get(0).concat(tokens.get(1)));
        }

        return key;
    }

    @Command(name = "--help, -h")
    protected void help() {
        for (BaseCommand cmd : commands) {
            out.println(conf.getHelpFormatter().apply(cmd));
        }
    }
}