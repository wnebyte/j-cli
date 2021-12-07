package com.github.wnebyte.jcli;

import java.lang.reflect.Method;
import java.util.Set;
import com.github.wnebyte.jcli.processor.IMethodScanner;
import com.github.wnebyte.jcli.processor.MethodScanner;
import com.github.wnebyte.jcli.util.Objects;

public abstract class AbstractCLI {

    /*
    ###########################
    #         FIELDS          #
    ###########################
    */

    protected final Configuration config;

    protected final Set<BaseCommand> commands;

    /*
    ###########################
    #       CONSTRUCTORS      #
    ###########################
    */

    public AbstractCLI() {
        this(new Configuration());
    }

    public AbstractCLI(Configuration config) {
        this.config = Objects.requireNonNullElseGet(config, Configuration::new);
        IMethodScanner scanner = new MethodScanner();
        IInstanceTracker tracker = new InstanceTracker(config.getDependencyContainer());
        scan(scanner, tracker);
        this.commands = build(scanner.getScannedElements(), tracker);
    }

    /*
    ###########################
    #          CONFIG         #
    ###########################
    */

    protected abstract void scan(IMethodScanner scanner, IInstanceTracker tracker);

    protected abstract Set<BaseCommand> build(Set<Method> scannedElements, IInstanceTracker tracker);

    /*
    ###########################
    #         RUNTIME         #
    ###########################
    */

    protected abstract BaseCommand getCommand(String input);

    protected abstract void accept(String input);

    protected abstract void read();
}
