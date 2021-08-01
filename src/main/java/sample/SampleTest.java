package sample;

import annotation.Argument;
import annotation.Command;
import core.Optional;
import core.Shell;
import core.ConfigurationBuilder;
import core.Console;

import java.util.Arrays;

public class SampleTest {

    public static void main(String[] args) {
        Shell shell = new Shell(new ConfigurationBuilder()
                .setConsole(new Console())
                .nullifyHelpCommands()
        );
        shell.run();
    }
}
