package com.github.wnebyte.jcli.sample;

import com.github.wnebyte.jcli.annotation.Command;
import com.github.wnebyte.jcli.CLI;
import com.github.wnebyte.jcli.conf.Configuration;

public class SampleTest {

    public static void main(String[] args) {
        CLI cli = new CLI(new Configuration()
                .setScanClasses(FooController.class, BarController.class, SampleTest.class)
                .nullifyScanPackages()
        );

        cli.read();
    }

    @Command
    public static void dab() {

    }

    @Command
    public static void eab() {

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
