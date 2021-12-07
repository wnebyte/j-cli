package com.github.wnebyte.jshell.sample;

import com.github.wnebyte.jshell.Configuration;
import com.github.wnebyte.jshell.Console;
import com.github.wnebyte.jshell.Shell;

public class SampleTest {

    public static void main(String[] args) {
        new Shell(new Configuration()
                .setConsole(new Console())
                .setScanClasses(SampleController.class)
                .setExcludeClasses(SampleTest.class)
                .nullifyScanPackages()
                .nullifySuggestCommand()
        ).read();
    }
}