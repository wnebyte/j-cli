package com.github.wnebyte.jcli;

import org.junit.Test;
import org.junit.Before;
import com.github.wnebyte.jcli.annotation.Command;
import com.github.wnebyte.jcli.annotation.Argument;

public class HelpFormatterTest {

    private CLI cli;

    @Before
    public void setup() {
        this.cli = new CLI(new Configuration()
                .setScanClasses(this.getClass())
                .disableScanPackages()
                .mapHelpCommand());
    }

    @Test
    public void test00() {
        cli.accept("--help");
    }

    @Command(value = "insert, -i", description = "this is a description")
    public int cmd00(
            @Argument(value = "name, -n", description = "a name")
            String name,
            @Argument(value = "url, -u", description = "a url")
            String url,
            @Argument(value = "comment, -c", description = "a comment")
            String comment
    ) {
        return -1;
    }
}
