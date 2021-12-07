package com.github.wnebyte.jcli;

import java.util.regex.Pattern;

import com.github.wnebyte.jarguments.ArgumentCollectionPatternGenerator;

public class BaseCommandPatternGenerator extends ArgumentCollectionPatternGenerator {

    private final BaseCommand cmd;

    public BaseCommandPatternGenerator(BaseCommand cmd) {
        super(cmd.getArguments());
        super.setRmlws(false);
        super.setInclSol(false);
        super.setInclEol(false);
        this.cmd = cmd;
    }

    @Override
    public void setRmlws(boolean value) {
        throw new UnsupportedOperationException(
                ""
        );
    }

    @Override
    public void setInclSol(boolean value) {
        throw new UnsupportedOperationException(
                ""
        );
    }

    @Override
    public void setInclEol(boolean value) {
        throw new UnsupportedOperationException(
                ""
        );
    }

    @Override
    public String generateRegex() {
        StringBuilder builder = new StringBuilder();
        builder.append("^")
                .append(cmd.hasPrefix() ? cmd.getPrefix().concat("\\s") : "")
                .append("(")
                .append(String.join("|", cmd.getNames()))
                .append(")")
                .append(super.generateRegex())
                .append("$");
        return builder.toString();
    }

    @Override
    public Pattern generatePattern() {
        String regex = generateRegex();
        return Pattern.compile(regex);
    }
}