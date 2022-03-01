package com.github.wnebyte.jcli.sample;

import com.github.wnebyte.jcli.CLI;
import com.github.wnebyte.jcli.conf.Configuration;

public class InputSampleTest {

    public static void main(String[] args) {
        CLI cli = new CLI(new Configuration()
                .nullifyScanPackages()
                .setScanClasses(InputController.class)
        );
        cli.getConfiguration().getConsole().out().println("rdy");
        cli.read();
    }
}
