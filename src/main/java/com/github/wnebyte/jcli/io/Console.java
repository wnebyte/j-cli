package com.github.wnebyte.jcli.io;

import java.util.Scanner;
import static java.lang.System.in;

/**
 * This class represents a concrete console that implements methods for printing to the standard output stream.
 */
public class Console implements IConsole {

    private final Scanner scanner;

    private final IWriter writer;

    public Console() {
        this.scanner = new Scanner(in);
        this.writer = new Writer();
    }

    @Override
    public String read() {
        if (scanner.hasNextLine()) {
            String in = scanner.nextLine();
            return in;
        } else {
            return null;
        }
    }

    @Override
    public IWriter writer() {
        return writer;
    }
}