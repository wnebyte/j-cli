package com.github.wnebyte.jcli.sample;

import com.github.wnebyte.jcli.CLI;
import com.github.wnebyte.jcli.Configuration;
import com.github.wnebyte.jcli.annotation.Argument;
import com.github.wnebyte.jcli.annotation.Command;
import com.github.wnebyte.jcli.annotation.Group;

public class Test {

    public static void main(String[] args) {
        CLI cli = new CLI(new Configuration()
                .setScanPackages("com.github.wnebyte.jcli")
        );
        cli.debug().forEach(cmd -> {
            System.out.println(cmd.getPattern().toString());
        });
    }

    @Command(name = "foo")
    public void foo(
            @Argument(
                    name = "-a, --a",
                    group = Group.OPTIONAL
            ) int a
    ) {

    }
}
