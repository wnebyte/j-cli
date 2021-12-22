package com.github.wnebyte.jcli.io;

/**
 * This interface represents a console and declares operations for printing and reading.
 */
public interface IConsole {

    String read();

    IWriter writer();
}