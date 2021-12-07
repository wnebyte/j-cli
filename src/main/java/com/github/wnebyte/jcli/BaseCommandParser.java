package com.github.wnebyte.jcli;

import java.util.LinkedList;
import com.github.wnebyte.jarguments.ArgumentCollectionParser;

public class BaseCommandParser extends ArgumentCollectionParser {

    private final BaseCommand cmd;

    public BaseCommandParser(BaseCommand cmd) {
        super(cmd.getArguments());
        this.cmd = cmd;
    }

    @Override
    protected LinkedList<String> split(String input) {
        LinkedList<String> c = super.split(input);
        if (cmd.hasPrefix())
            c.removeFirst();
        c.removeFirst();
        return c;
    }
}