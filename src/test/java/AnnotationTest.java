import annotation.Argument;
import annotation.Command;
import core.ConfigurationBuilder;
import core.Shell;
import exception.config.IllegalAnnotationException;
import org.junit.Test;
import util.Bundle;


public class AnnotationTest {

    @Test(expected = IllegalAnnotationException.class)
    public void test00() {
        Shell shell = new Shell(new ConfigurationBuilder()
                .nullifyHelpCommands()
                .nullifyScanPackages()
                .setScanBundles(new Bundle(this, "foo"))
        );
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
