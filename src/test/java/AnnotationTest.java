import annotation.Argument;
import annotation.Command;
import core.ConfigurationBuilder;
import core.Shell;
import org.junit.Test;
import util.Bundle;

import java.util.Arrays;

public class AnnotationTest {

    @Test
    public void test00() {
        Shell shell = new Shell(new ConfigurationBuilder()
                .nullifyHelpCommands()
                .nullifyScanPackages()
                .setScanBundles(new Bundle(this, "foo"))
        );

        System.out.println(Arrays.toString(shell.getCommandKeys().toArray()));
    }

    @Command()
    private void foo(
            @Argument(name = "a")
            int a,
            @Argument(name = "a")
            int b
    ) {

    }
}
