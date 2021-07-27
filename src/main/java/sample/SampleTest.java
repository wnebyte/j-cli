package sample;

import core.Command;
import core.Shell;
import core.ConfigurationBuilder;
import core.Console;

public class SampleTest {

    public static void main(String[] args) throws Exception {
        Shell shell = new Shell(new ConfigurationBuilder()
                .setConsole(new Console())
                .setNullifyHelpCommands()
        );
        shell.run();
    }
}
