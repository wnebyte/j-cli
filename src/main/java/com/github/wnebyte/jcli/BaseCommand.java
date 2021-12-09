package com.github.wnebyte.jcli;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import com.github.wnebyte.jarguments.Argument;
import com.github.wnebyte.jarguments.exception.ParseException;
import com.github.wnebyte.jcli.pattern.BaseCommandPatternGenerator;

public abstract class BaseCommand {

    protected final Pattern pattern;

    protected final String prefix;

    protected final Set<String> names;

    protected final String description;

    protected final List<Argument> arguments;

    protected BaseCommand(
            String prefix,
            Set<String> names,
            String description,
            List<Argument> args
    ) {
        this.prefix = prefix;
        this.names = names;
        this.description = description;
        this.arguments = args;
        this.pattern = new BaseCommandPatternGenerator(this)
                .generatePattern();
    }

    abstract void run(String input) throws ParseException;

    Pattern getPattern() {
        return pattern;
    }

    public List<Argument> getArguments() {
        return arguments;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean hasPrefix() {
        return (prefix != null) && !(prefix.equals(""));
    }

    public String getDescription() {
        return description;
    }

    public boolean hasDescription() {
        return (description != null) && !(description.equals(""));
    }

    public Set<String> getNames() {
        return names;
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
        return Objects.equals(cmd.pattern, this.pattern) &&
                Objects.equals(cmd.prefix, this.prefix) &&
                Objects.equals(cmd.names, this.names) &&
                Objects.equals(cmd.description, this.description) &&
                Objects.equals(cmd.arguments, this.arguments);
    }

    @Override
    public int hashCode() {
        int result = 65;
        return 2 * result +
                Objects.hashCode(pattern) +
                Objects.hashCode(prefix) +
                Objects.hashCode(names) +
                Objects.hashCode(description) +
                Objects.hashCode(arguments);
    }

    @Override
    public String toString() {
        String prefix = hasPrefix() ? getPrefix().concat(" ") : "";
        return prefix + String.join(" | ", getNames()) + " " +
                getArguments().stream().map(Argument::toString).collect(Collectors.joining(" "));
    }

    public String toPaddedString() {
        String prefix = hasPrefix() ? getPrefix().concat(" ") : "";
        return prefix + String.join(" | ", getNames()) + " " +
                getArguments().stream().map(Argument::toPaddedString).collect(Collectors.joining(" "));
    }

    public String toDescriptiveString() {
        String prefix = hasPrefix() ? getPrefix().concat(" ") : "";
        return prefix + String.join(" | ", getNames()) + " " +
                getArguments().stream().map(Argument::toDescriptiveString).collect(Collectors.joining(" "));
    }

    public String toPaddedDescriptiveString() {
        String prefix = hasPrefix() ? getPrefix().concat(" ") : "";
        return prefix + String.join(" | ", getNames()) + " " +
                getArguments().stream().map(Argument::toPaddedDescriptiveString).collect(Collectors.joining(" "));
    }
}