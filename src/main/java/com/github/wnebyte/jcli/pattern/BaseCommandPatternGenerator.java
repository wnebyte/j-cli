package com.github.wnebyte.jcli.pattern;

import java.util.regex.Pattern;
import com.github.wnebyte.jarguments.pattern.ArgumentPatternGenerator;
import com.github.wnebyte.jcli.BaseCommand;

public class BaseCommandPatternGenerator extends ArgumentPatternGenerator {

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
    public String getRegex() {
        StringBuilder builder = new StringBuilder();
        builder.append("^")
                .append(cmd.hasPrefix() ? cmd.getPrefix().concat("\\s") : "")
                .append("(")
                .append(String.join("|", cmd.getNames()))
                .append(")")
                .append(super.getRegex())
                .append("$");
        return builder.toString();
    }

    @Override
    public Pattern getPattern() {
        String regex = getRegex();
        return Pattern.compile(regex);
    }
}