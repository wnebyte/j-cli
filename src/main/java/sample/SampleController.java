package sample;

import annotation.Argument;
import annotation.Command;
import core.*;

import java.util.Arrays;

public class SampleController {

    private final IConsole console;

    public SampleController(IConsole console) {
        this.console = console;
    }

    @Command(
            name = "foo",
            description = "does something"
    )
    public void foo(
            @Argument(
                    name = "a",
                    type = Required.class,
                    description = "number of somethings"
            ) int a,
            @Argument(
                    name = "b",
                    type = Optional.class
            ) int b) {
        System.out.println("a: " + a + ", b: " + b);
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

    /*
    @Command(
            description = "does something else"
    )
    public void foo(
            @Argument(name = "a",
                    type = Positional.class
            ) String[] a,
            @Argument(
                    name = "b",
                    type = Optional.class
            ) int b) {
        console.println("a: " + Arrays.toString(a) + ", b: " + b);
    }

    @Command
    public void str(
            @Argument(name = "-a")
            String a,
            @Argument(name  = "-b")
            String b
    ) {
        console.println("a: " + a + ", b: " + b);
    }
     */
}