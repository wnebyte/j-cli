package com.github.wnebyte.jcli;

import org.junit.Test;
import org.junit.Before;
import com.github.wnebyte.jcli.conf.Configuration;
import com.github.wnebyte.jcli.sample.BarController;
import com.github.wnebyte.jcli.sample.FooController;
import com.github.wnebyte.jcli.sample.SampleTest;

public class CLITest {

    private CLI cli;

    public static void main(String[] args) {
        CLI cli = new CLI(new Configuration()
                .setScanClasses(FooController.class, BarController.class, SampleTest.class)
                .nullifyScanPackages()
        );
        cli.read();
    }

    @Before
    public void setup() {
        cli = new CLI(new Configuration()
                .setScanClasses(FooController.class, BarController.class, SampleTest.class)
                .nullifyScanPackages()
        );
    }

    @Test(expected = RuntimeException.class)
    public void test00() {
        cli.getConfiguration().setUnknownCommandFormatter(e -> {
            throw new RuntimeException(e.getCause());
        });
        cli.accept("blabla");
    }
}
