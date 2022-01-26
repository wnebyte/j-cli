package com.github.wnebyte.jcli.parser;

import java.util.LinkedList;
import com.github.wnebyte.jarguments.parser.ArgumentParser;
import com.github.wnebyte.jcli.BaseCommand;

@Deprecated
public class CommandParser extends ArgumentParser {

    private final BaseCommand cmd;

    public CommandParser(BaseCommand cmd) {
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