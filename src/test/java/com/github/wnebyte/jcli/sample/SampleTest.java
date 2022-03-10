package com.github.wnebyte.jcli.sample;

import com.github.wnebyte.jcli.CLI;
import com.github.wnebyte.jcli.annotation.Argument;
import com.github.wnebyte.jcli.annotation.Command;
import com.github.wnebyte.jcli.conf.Configuration;

public class SampleTest {

    public static void main(String[] args) {
        CLI cli = new CLI(new Configuration()
                .setScanClasses(FooController.class, BarController.class, SampleTest.class)
                .nullifyScanPackages()
        );
        cli.getConfiguration().out().println("--begin");
        cli.read();
    }

    @Command(description = "this is a description")
    public static void dab(int a) {

    }

    @Command(description = "another description")
    public static void eab(
            @Argument(name = "looongname", description = "argument description")
            String a,
            @Argument(description = "argument description")
            String b,
            String c
    ) {

    }

    @Command
    public static void aab() {

    }

    @Command
    public static void cab() {

    }

    @Command
    public static void bab() {

    }
}
