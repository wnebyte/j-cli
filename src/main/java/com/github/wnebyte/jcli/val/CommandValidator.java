package com.github.wnebyte.jcli.val;

import java.util.LinkedList;
import com.github.wnebyte.jcli.BaseCommand;
import com.github.wnebyte.jarguments.exception.ParseException;
import com.github.wnebyte.jarguments.val.ArgumentValidator;

@Deprecated
public class CommandValidator extends ArgumentValidator {

    private final BaseCommand cmd;

    public CommandValidator(BaseCommand cmd) {
        super(cmd.getArguments());
        this.cmd = cmd;
    }

    @Override
    public boolean validate(String input) {
        return valPrefixAndName(input) && super.validate(input);
    }

    @Override
    public boolean matches(String input) throws ParseException {
        return valPrefixAndName(input) && super.matches(input);
    }

    @Override
    protected LinkedList<String> split(String input) {
        LinkedList<String> c = super.split(input);
        if (cmd.hasPrefix())
            c.removeFirst();
        c.removeFirst();
        return c;
    }

    private boolean valPrefixAndName(String input) {
        LinkedList<String> c = super.split(input);
        if (cmd.hasPrefix()) {
            if (c.size() < 1 || !cmd.getPrefix().equals(c.getFirst())) {
                return false;
            } else {
                c.removeFirst();
            }
        }
        return c.size() >= 1 && cmd.getNames().contains(c.getFirst());
    }
}
