package com.github.wnebyte.jcli.io;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import static java.lang.System.in;

public class Console implements IConsole {

    private final Scanner scanner;

    private final IWriter writer;

    private final List<String> history;

    public Console() {
        this.scanner = new Scanner(in);
        this.writer = new Writer();
        this.history = new LinkedList<>();
    }

    @Override
    public String read() {
        if (scanner.hasNextLine()) {
            String in = scanner.nextLine();
            history.add(in);
            return in;
        } else {
            return null;
        }
    }

    @Override
    public IWriter writer() {
        return writer;
    }

    @Override
    public List<String> getHistory() {
        return new LinkedList<>(history);
    }

    @Override
    public void clear() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n");
    }
}