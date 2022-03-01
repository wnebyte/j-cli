package com.github.wnebyte.jcli;

import org.junit.Test;
import org.junit.Before;
import com.github.wnebyte.jcli.conf.Configuration;
import com.github.wnebyte.jcli.sample.BarController;
import com.github.wnebyte.jcli.sample.FooController;
import com.github.wnebyte.jcli.sample.SampleTest;
import com.github.wnebyte.jcli.exception.UnknownCommandException;

public class CLITest {

    private CLI cli;

    public static void main(String[] args) {
        CLI cli = new CLI(new Configuration()
                .setScanClasses(
                        FooController.class,
                        BarController.class,
                        SampleTest.class
                )
                .nullifyScanPackages()
        );
        cli.read();
    }

    @Before
    public void setup() {
        cli = new CLI(new Configuration()
                .setScanClasses(
                        FooController.class,
                        BarController.class,
                        SampleTest.class
                )
                .nullifyScanPackages()
                .setUnknownCommandFormatter(e -> {
                    throw e;
                })
                .setMalformedArgumentExceptionFormatter((e) -> {
                    throw new RuntimeException(e.getCause());
                })
                .setMissingArgumentExceptionFormatter((e) -> {
                    throw new RuntimeException(e.getCause());
                })
                .setTypeConversionExceptionFormatter((e) -> {
                    throw new RuntimeException(e.getCause());
                })
                .setMissingArgumentValueExceptionFormatter((e) -> {
                    throw new RuntimeException(e.getCause());
                })
                .setNoSuchArgumentExceptionFormatter((e) -> {
                    throw new RuntimeException(e.getCause());
                })
        );
    }

    @Test(expected = UnknownCommandException.class)
    public void test00() {
        cli.accept("blabla");
    }

    @Test(expected = RuntimeException.class)
    public void test01() {
        String[] input = { "foo", "aab", "test" };
        cli.accept(input);
    }

    @Test
    public void test02() {
        String[] input = { "foo", "aab" };
        cli.accept(input);
    }
}
