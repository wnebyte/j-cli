package com.github.wnebyte.jcli;

import com.github.wnebyte.jcli.annotation.Argument;
import com.github.wnebyte.jcli.annotation.Command;
import org.junit.Test;

public class AnnotationTest {

    @Test(expected = IllegalArgumentException.class)
    public void test00() {
        CLI cli = new CLI(new Configuration()
                .nullifyScanPackages()
                .setScanIdentifiers(new Identifier(this.getClass(), "foo"))
        );

    }

    @Command()
    private void foo(
            @Argument(name = "a")
            int a,
            @Argument(name = "a")
            int b
    ) { }
}
