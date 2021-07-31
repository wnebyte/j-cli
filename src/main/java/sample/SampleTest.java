package sample;

import annotation.Argument;
import annotation.Command;
import core.Optional;
import core.Shell;
import core.ConfigurationBuilder;
import core.Console;

public class SampleTest {

    public static void main(String[] args) {
        Shell shell = new Shell(new ConfigurationBuilder()
                .setConsole(new Console())
                .nullifyHelpCommands()
                .nullifyScanPackages()
                .setScanObjects(new SampleTest())
        );
       // System.out.println(Arrays.toString(shell.getCommandKeys().toArray()));
    }

    @Command()
    private void foo(
            @Argument(name = "a")
                    int a,
            @Argument(name = "b")
                    int b,
            @Argument(name = "c", type = Optional.class)
                    int c
    ) {

    }

    @Command(name = "foo")
    private void foo1(
            @Argument(name = "a")
                    int a,
            @Argument(name = "b")
                    int b,
            @Argument(name = "c")
                    int c
    ) {

    }
}
