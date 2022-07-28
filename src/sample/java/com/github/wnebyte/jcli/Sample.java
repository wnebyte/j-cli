package com.github.wnebyte.jcli;

public class Sample {

    public static void main(String[] args) {
        CLI cli = new CLI(new Configuration()
                .setScanClasses(BarController.class, FooController.class)
        );
        cli.run();
    }
}
