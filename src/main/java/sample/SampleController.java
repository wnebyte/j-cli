package sample;

import annotation.Argument;
import annotation.Command;
import annotation.Controller;
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
           String a,
           @Argument(name = "-b", type = Positional.class)
           String[] b
   ) {
        console.println("-a: " + a + ", -b: " + Arrays.toString(b));
   }
}