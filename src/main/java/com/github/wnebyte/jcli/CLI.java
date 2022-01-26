package com.github.wnebyte.jcli;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.lang.reflect.Method;
import com.github.wnebyte.jarguments.Argument;
import com.github.wnebyte.jarguments.Tokens;
import com.github.wnebyte.jarguments.exception.ParseException;
import com.github.wnebyte.jarguments.factory.ArgumentFactoryBuilder;
import com.github.wnebyte.jarguments.parser.AbstractParser;
import com.github.wnebyte.jarguments.parser.Parser;
import com.github.wnebyte.jcli.annotation.Command;
import com.github.wnebyte.jcli.conf.Configuration;
import com.github.wnebyte.jcli.exception.UnknownCommandException;
import com.github.wnebyte.jcli.io.IConsole;
import com.github.wnebyte.jcli.processor.*;
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
        return (tokens != null) && (tokens.size() == 1) &&
                (tokens.get(0).equals("--help") || tokens.get(0).equals("-h"));
    }

    /*
    ###########################
    #          FIELDS         #
    ###########################
    */

    protected final Configuration conf;

    protected final IConsole console;

    protected final List<BaseCommand> commands;

    protected final Set<Integer> prefixes;

    protected final HashMap<Integer, List<BaseCommand>> index;

    protected final AbstractParser<Tokens, Collection<Argument>> parser;

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
     * @param conf the configuration to be used.
     */
    public CLI(Configuration conf) {
        this.conf = Objects.requireNonNullElseGet(conf, Configuration::new);
        this.console = conf.getConsole();
        this.prefixes = new HashSet<>();
        this.index = new HashMap<>();
        this.parser = new Parser();
        this.commands = build(new MethodScanner(), new InstanceTracker(conf.getDependencyContainer()));
    }

    /*
    ###########################
    #         METHODS         #
    ###########################
    */

    protected List<BaseCommand> build(IMethodScanner scanner, IInstanceTracker tracker) {
        scan(scanner, tracker);
        List<BaseCommand> commands = map(scanner, tracker);
        index(commands);
        sort(commands);
        return commands;
    }

    protected void scan(IMethodScanner scanner, IInstanceTracker tracker) {
        Set<Object> objects = conf.getScanObjects();
        Set<Class<?>> classes = conf.getScanClasses();
        Set<String> packages = conf.getScanPackages();
        Set<Identifier> identifiers = conf.getScanIdentifiers();
        Set<Method> methods = conf.getScanMethods();

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
            // remove help-command from set of scanned methods
            scanner.removedScannedElementIf(m -> m.getDeclaringClass() == CLI.class);
        }
        else {
            scanner.scanClass(CLI.class);
            tracker.add(this);
        }
        if (conf.getExcludeClasses() != null) {
            scanner.removedScannedElementIf(m -> conf.getExcludeClasses().contains(m.getDeclaringClass()));
        }
    }

    protected List<BaseCommand> map(IMethodScanner scanner, IInstanceTracker tracker) {
        return scanner.getScannedElements().stream()
                .map(new MethodMapperBuilder()
                        .setInstanceTracker(tracker)
                        .setArgumentFactoryBuilder(new ArgumentFactoryBuilder()
                                .useTypeConverterMap(conf.getTypeConverterMap()))
                        .build()
                )
                .filter(Objects::nonNull)
                .filter(new FilterImpl())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    protected void index(List<BaseCommand> commands) {
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
                    index.put(key, Collections.singletonList(cmd));
                }
            }
        }
    }

    protected void sort(List<BaseCommand> commands) {
        commands.sort(BaseCommand::compareTo);
    }

    public void accept(String input) {
        Tokens tokens = Tokens.tokenize(input);

        try {
            BaseCommand cmd = lookup(tokens);
            Object[] args = parser.initialize();
            cmd.execute(args);
        }
        catch (UnknownCommandException e) {
            console.err().println(conf.getUnknownCommandExceptionFormatter().apply(e));
        }
        catch (ParseException e) {
            console.err().println(conf.getParseExceptionFormatter().apply(e));
        }
    }

    public void accept(String[] input) {
        accept(String.join(" ", input));
    }

    public Consumer<String> toConsumer() {
        return CLI.this::accept;
    }

    public void read() {
        Scanner scanner = new Scanner(console.in());

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            accept(input);
        }
    }

    public final Configuration getConfiguration() {
        return conf;
    }

    protected BaseCommand lookup(Tokens tokens) throws UnknownCommandException, ParseException {
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
                    return BaseCommand.stub((args) -> console.out().println(conf.getHelpFormatter().apply(cmd)));
                } else {
                    throw e;
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
            console.out().println(conf.getHelpFormatter().apply(cmd));
        }
    }
}