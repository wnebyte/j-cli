package com.github.wnebyte.jcli;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import com.github.wnebyte.jarguments.Argument;
import com.github.wnebyte.jarguments.exception.ParseException;

public abstract class BaseCommand {

    protected Pattern pattern;

    protected String prefix;

    protected Set<String> names;

    protected String description;

    protected List<Argument> arguments;

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

    protected abstract void run(String input) throws ParseException;

    public List<Argument> getArguments() {
        return arguments;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean hasPrefix() {
        return false;
    }

    public String getDescription() {
        return description;
    }

    public boolean hasDescription() {
        return false;
    }

    public Set<String> getNames() {
        return names;
    }
}
