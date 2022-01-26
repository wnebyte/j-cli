package com.github.wnebyte.jcli.io;

import java.io.InputStream;
import java.io.PrintStream;

/**
 * This interface represents a console and declares operations for printing and reading.
 */
public interface IConsole {

    PrintStream out();

    PrintStream err();

    InputStream in();
}