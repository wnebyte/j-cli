package com.github.wnebyte.jcli.io;

import java.io.InputStream;
import java.io.PrintStream;

/**
 * This class represents a concrete console that implements methods for printing to the standard output stream.
 */
public class Console implements IConsole {

    @Override
    public PrintStream out() {
        return System.out;
    }

    @Override
    public PrintStream err() {
        return System.err;
    }

    @Override
    public InputStream in() {
        return System.in;
    }
}