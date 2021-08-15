package com.github.wnebyte.jshell.sample;

import com.github.wnebyte.jshell.*;
import com.github.wnebyte.jshell.annotation.Argument;
import com.github.wnebyte.jshell.annotation.Command;

public class SampleTest {

    public static void main(String[] args) {
        Shell shell = new Shell(new Configuration()
                .setConsole(new Console())
        );
        shell.run();
    }

    @Command
    public void init(
            @Argument(name = "-a")
            String a,
            @Argument(name = "-b")
                    String b
    ) {
        System.out.println("init -a: " + a);
    }
}
