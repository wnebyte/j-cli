package com.github.wnebyte.jshell.sample;

import com.github.wnebyte.jshell.annotation.Argument;
import com.github.wnebyte.jshell.annotation.Command;
import com.github.wnebyte.jshell.Configuration;
import com.github.wnebyte.jshell.Console;
import com.github.wnebyte.jshell.Shell;
import com.github.wnebyte.jshell.annotation.Type;

public class SampleTest {

    public static void main(String[] args) {
        new Shell(new Configuration()
                .setConsole(new Console())
                .setScanClasses(SampleTest.class)
                .nullifyHelpCommands()
                .nullifyScanPackages()
                .nullifySuggestCommand()
        ).read();
    }

    @Command(name = "foo")
    public void foo(
            @Argument(name = "o", type = Type.OPTIONAL)
                    int o,
            @Argument(name = "r", type = Type.REQUIRED)
                    int r
    ) {

    }
}