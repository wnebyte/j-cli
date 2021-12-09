package com.github.wnebyte.jcli.sample;

import com.github.wnebyte.jcli.CLI;
import com.github.wnebyte.jcli.Configuration;
import com.github.wnebyte.jcli.annotation.Argument;
import com.github.wnebyte.jcli.annotation.Command;
import com.github.wnebyte.jcli.annotation.Group;

public class Test {

    public static void main(String[] args) {
        CLI cli = new CLI(new Configuration()
                .nullifyScanPackages()
                .setScanClasses(Test.class)
        );
        cli.read();
    }

    @Command(description = "this is a description")
    private void foo(
            @Argument(group = Group.POSITIONAL, description = "array")
            String c,
            @Argument(name = "bar, -bar")
            String a,
            @Argument(group = Group.OPTIONAL, description = "adds some numbers")
            int b
    ) {

    }
}
