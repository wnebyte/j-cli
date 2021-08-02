package sample;

import annotation.Argument;
import annotation.Command;
import annotation.Controller;
import core.*;

import java.util.Arrays;

@Controller(name = "foo")
public class SampleController {

    private final IConsole console;

    public SampleController(IConsole console) {
        this.console = console;
    }

   @Command(description = "test")
   public void foo(
            @Argument(name = "-a", type = Optional.class)
            String[] a,
            @Argument(name = "-b", type = Optional.class)
            String b,
            @Argument(name = "-c", type = Optional.class)
            String[] c,
            @Argument(name = "-d", type = Optional.class)
            String d
   ) {
        console.println("a: " + Arrays.toString(a) + ", b: " + b + ", c: " + Arrays.toString(c) + ", d: " + d);
   }

   @Command(description = "add two integers")
   public void add(
           @Argument(type = Positional.class)
           int a,
           @Argument(type = Positional.class)
           int b
   ) {
        console.println((a + b));
   }

   @Command
   public void test(
           @Argument(type = Positional.class)
                   int a,
           @Argument(type = Positional.class)
                   int b,
           @Argument(name = "-c", type = Optional.class)
                   String c,
           @Argument(name = "-d", type = Optional.class)
                   String d
   ) {
        console.println("a: " + a + ", b: " + b + ", c: " + c + ", d: " + d);
   }

   @Command(
           name = "",
           description = ""
   )
   public void foo() {

   }
}