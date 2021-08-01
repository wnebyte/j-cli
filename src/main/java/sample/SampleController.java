package sample;

import annotation.*;
import annotation.Argument;
import annotation.Command;
import core.*;

import java.util.Arrays;

public class SampleController {

    private final IConsole console;

    public SampleController(IConsole console) {
        this.console = console;
    }


    @Command
    public void foo(
            @Argument(name = "-a", type = Optional.class)
            int a,
            @Argument(name = "-b", type = Optional.class)
            int b,
            @Argument(name = "-c", type = Optional.class)
            int c) {

    }

    @Command(name = "foo")
    public void funct(
            @Argument(name = "-a")
            int a
    ) {

    }

    /*
    @Command(
            name = "foo",
            description = "does something"
    )
    public void foo(
            @Argument(
                    name = "-a",
                    type = Required.class,
                    description = "number of somethings"
            ) int a,
            @Argument(
                    name = "-b",
                    type = Optional.class
            ) int b) {
        console.println("a: " + a + ", b: " + b);
    }

    @Command
    public void array(
            @Argument(
                    name = "-a",
                    type = Positional.class
            )
            String[] a,
            @Argument(
                    name = "-b",
                    type = Optional.class
            )
            String b
    ) {
        console.println("a: " + Arrays.toString(a) + ", b: " + b);
    }

    @Command
    public void test(
            @Argument(
                    name = "-b",
                    type = Positional.class
            )
                    int[] b,
            @Argument(
                    name = "-a"
            )
            int a,
            @Argument(
                    name = "-c"
            )
            String c,
            @Argument(
                    name = "-d",
                    type = Optional.class
            )
            boolean d) {
        console.println("-a: " + a + ", -b: " + Arrays.toString(b) + ", -c: " + c + ", -d: " + d);
    }
     */
}