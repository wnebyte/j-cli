package com.github.wnebyte.jshell.sample;

import com.github.wnebyte.jshell.Configuration;
import com.github.wnebyte.jshell.Console;
import com.github.wnebyte.jshell.Shell;

public class ReadmeTest {

    public static void main(String[] args) {
        Shell shell = new Shell(new Configuration()
                .setConsole(new Console())
                .nullifyScanPackages()
                .setScanClasses(ReadmeController.class)
        );
        shell.run();
    }
}
