package com.github.wnebyte.jshell.sample;

import com.github.wnebyte.jshell.annotation.Command;
import com.github.wnebyte.jshell.annotation.Argument;
import com.github.wnebyte.jshell.annotation.Controller;
import com.github.wnebyte.jshell.annotation.Type;
import com.github.wnebyte.jshell.IConsole;
import java.util.Arrays;

public class SampleController {

    private final IConsole console;

    public SampleController(IConsole console) {
        this.console = console;
    }

   @Command(description = "invokes a command, does something")
   public void foo(
           @Argument(name = "-a", type = Type.OPTIONAL, description = "set the foo")
           String a,
           @Argument(name = "-b", type = Type.POSITIONAL, description = "set the array contents")
           String[] b,
           @Argument(name = "-c", description = "set the something")
           boolean c
   ) {
        console.println("-a: " + a + ", -b: " + Arrays.toString(b));
   }

   @Command(name  = "foo", description = "double - int - string")
   public void foo(
           @Argument(type = Type.POSITIONAL)
                   double c,
           @Argument(type = Type.POSITIONAL)
           int b,
           @Argument(type = Type.POSITIONAL)
           String a
   ) {

   }

   @Command
   public void test(
           @Argument(
                   name = "bar",
                   description = "arg"
           )
           String bar
   ) {

   }

   @Command(
           name = "empty"
   )
   private void foo() {

   }

   @Command(description = "clear the console")
   public void clear() {
        console.clear();
   }
}