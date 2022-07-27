package com.github.wnebyte.jcli.test;

import com.github.wnebyte.jcli.CLI;
import com.github.wnebyte.jcli.Configuration;
import com.github.wnebyte.jcli.annotation.Argument;
import com.github.wnebyte.jcli.annotation.Command;

public class Test {

    public static void main(String[] args) {
        CLI shell = new CLI(new Configuration()
                .setScanClasses(Test.class)
        );
        shell.run();
    }

    @Command(description = "desc")
    public void myCommand(
            @Argument(value = "myArgument", choices = {"1", "2", "10"}, metavar = "DATA")
            int a
    ) {
        System.out.println("my command executing...");
    }
}
