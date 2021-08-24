package com.github.wnebyte.jshell.sample;

import com.github.wnebyte.jshell.*;
import com.github.wnebyte.jshell.annotation.Argument;
import com.github.wnebyte.jshell.annotation.Command;

import java.util.function.Function;

public class SampleTest {

    public static void main(String[] args) {
        new Shell(new Configuration()
                .setConsole(new Console())
                .setScanClasses(SampleController.class)
                .nullifyScanPackages()
        ).run();
    }

}
