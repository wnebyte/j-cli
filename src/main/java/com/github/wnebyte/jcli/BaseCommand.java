package com.github.wnebyte.jcli;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import com.github.wnebyte.jarguments.Argument;
import com.github.wnebyte.jarguments.exception.ParseException;

/**
 * This class represents an abstract executable Command.
 */
public abstract class BaseCommand implements Comparable<BaseCommand> {

    /*
    ###########################
    #      STATIC METHODS     #
    ###########################
    */

    static BaseCommand stub(Consumer<Object[]> exe) {
        return new BaseCommand(null, null, null, null) {
            @Override
            void execute(Object[] args) {
                exe.accept(args);
            }
        };
    }

    /*
    ###########################
    #      STATIC FIELDS      #
    ###########################
    */

    protected static final Comparator<BaseCommand> COMPARATOR =
            Comparator.comparing(BaseCommand::getPrefix)
                    .thenComparing(cmd -> cmd.getNames().toArray(new String[0])[0]);

    /*
    ###########################
    #          FIELDS         #
    ###########################
    */

    protected final String prefix;

    protected final Set<String> names;

    protected final String desc;

    protected final List<Argument> arguments;

    /*
    ###########################
    #       CONSTRUCTORS      #
    ###########################
    */

    protected BaseCommand(
            String prefix,
            Set<String> names,
            String desc,
            List<Argument> args
    ) {
        this.prefix = prefix;
        this.names = names;
        this.desc = desc;
        this.arguments = args;
    }

    /*
    ###########################
    #          METHODS        #
    ###########################
    */

    abstract void execute(Object[] args) throws ParseException;

    public final List<Argument> getArguments() {
        return Collections.unmodifiableList(arguments);
    }

    public final String getPrefix() {
        return prefix;
    }

    public final boolean hasPrefix() {
        return (prefix != null) && !(prefix.equals(""));
    }

    public final String getDesc() {
        return desc;
    }

    public final boolean hasDesc() {
        return (desc != null) && !(desc.equals(""));
    }

    public final Set<String> getNames() {
        return Collections.unmodifiableSet(names);
    }

    @Override
    public int compareTo(BaseCommand o) {
        return COMPARATOR.compare(this, o);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null)
            return false;
        if (!(o instanceof BaseCommand))
            return false;
        BaseCommand cmd = (BaseCommand) o;
        return Objects.equals(cmd.prefix, this.prefix) &&
                Objects.equals(cmd.names, this.names) &&
                Objects.equals(cmd.desc, this.desc) &&
                Objects.equals(cmd.arguments, this.arguments);
    }

    @Override
    public int hashCode() {
        int result = 65;
        return 2 * result +
                Objects.hashCode(prefix) +
                Objects.hashCode(names) +
                Objects.hashCode(desc) +
                Objects.hashCode(arguments);
    }

    @Override
    public String toString() {
        String prefix = hasPrefix() ? getPrefix().concat(" ") : "";
        return prefix + String.join(" | ", names) + " " +
                arguments.stream().map(Argument::toString).collect(Collectors.joining(" "));
    }

    public String toPaddedString() {
        String prefix = hasPrefix() ? getPrefix().concat(" ") : "";
        return prefix + String.join(" | ", names) + " " +
                arguments.stream().map(Argument::toPaddedString).collect(Collectors.joining(" "));
    }

    public String toDescriptiveString() {
        String prefix = hasPrefix() ? getPrefix().concat(" ") : "";
        return prefix + String.join(" | ", names) + " " +
                arguments.stream().map(Argument::toDescriptiveString).collect(Collectors.joining(" "));
    }

    public String toPaddedDescriptiveString() {
        String prefix = hasPrefix() ? getPrefix().concat(" ") : "";
        return prefix + String.join(" | ", names) + " " +
                arguments.stream().map(Argument::toPaddedDescriptiveString).collect(Collectors.joining(" "));
    }
}