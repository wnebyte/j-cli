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

   @Command(description = "invokes a command, does something")
   public void foo(
           @Argument(name = "-a", type = Optional.class, description = "set the foo")
           String a,
           @Argument(name = "-b", type = Positional.class, description = "set the array contents")
           String[] b,
           @Argument(name = "-c", description = "set the something")
           boolean c
   ) {
        console.println("-a: " + a + ", -b: " + Arrays.toString(b));
   }

   @Command(name  = "test", description = "double - int - string")
   public void foo(
           @Argument(type = Positional.class)
                   double c,
           @Argument(type = Positional.class)
           int b,
           @Argument(type = Positional.class)
           String a
   ) {

   }

   @Command(name = "empty")
   private void foo() {

   }
}