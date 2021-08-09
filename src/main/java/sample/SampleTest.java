package sample;

import annotation.Argument;
import core.*;

public class SampleTest {

    public static void main(String[] args) {
        Shell shell = new Shell(new ConfigurationBuilder()
                .setConsole(new Console())
                .setScanClasses(SampleTest.class)
                .nullifyScanPackages()
        );
        shell.run();
    }

    @annotation.Command
    public void test00(
            @annotation.Argument(type = Positional.class)
                    int arg0,
            @annotation.Argument()
                    int arg1,
            @annotation.Argument(type = Optional.class)
                    int arg2,
            @annotation.Argument(type = Optional.class)
                    int arg3,
            @annotation.Argument(type = Optional.class)
                    int arg4
    ) {
    }
}
