package com.github.wnebyte.jcli.io;

import java.util.List;

/**
 * This interface represents a writeable and readable console.
 */
public interface IConsole {

    String read();

    IWriter writer();

    List<String> getHistory();

    void clear();
}