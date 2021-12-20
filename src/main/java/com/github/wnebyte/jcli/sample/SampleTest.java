package com.github.wnebyte.jcli.sample;

import com.github.wnebyte.jcli.annotation.*;
import com.github.wnebyte.jcli.CLI;
import com.github.wnebyte.jcli.conf.Configuration;
import com.github.wnebyte.jcli.io.IConsole;

@Controller(value = Scope.SINGLETON)
public class SampleTest {

    @Inject
    private IConsole console;

    public static void main(String[] args) {
        CLI cli = new CLI(new Configuration()
                .nullifyScanPackages()
                .setScanClasses(SampleTest.class)
        );
        cli.read();
    }

    @Command(name = "foo")
    public void foo(
            @Argument(name = "-o", group = Group.OPTIONAL)
            int o,
            @Argument(name = "-r", group = Group.REQUIRED)
            int r
    ) {

    }

    @Command(name = "bar")
    public void bar(
            @Argument(group = Group.POSITIONAL)
            String s,
            @Argument(name = "-b")
            boolean b,
            @Argument(name = "-c", group = Group.REQUIRED)
            int c
    ) {}
}