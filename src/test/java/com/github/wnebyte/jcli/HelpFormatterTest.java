package com.github.wnebyte.jcli;

import org.junit.Before;
import org.junit.Test;
import com.github.wnebyte.jcli.conf.Configuration;
import com.github.wnebyte.jcli.annotation.Command;
import com.github.wnebyte.jcli.annotation.Argument;

public class HelpFormatterTest {

    private CLI cli;

    @Before
    public void setup() {
        this.cli = new CLI(new Configuration()
                .setScanClasses(this.getClass())
                .nullifyScanPackages());
    }

    @Test
    public void test00() {
        cli.accept("--help");
    }

    @Command(name = "insert, -i", description = "this is a description")
    public int cmd00(
            @Argument(name = "name, -n", description = "a name")
            String name,
            @Argument(name = "url, -u", description = "a url")
            String url,
            @Argument(name = "comment, -c", description = "a comment")
            String comment
    ) {
        return -1;
    }
}
