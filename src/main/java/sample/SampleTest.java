package sample;

import core.*;

public class SampleTest {

    public static void main(String[] args) {
        Shell shell = new Shell(new ConfigurationBuilder()
                .setConsole(new ExtendedConsole())
        );
        shell.run();
    }
}
