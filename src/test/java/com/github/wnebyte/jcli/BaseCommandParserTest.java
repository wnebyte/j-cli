package com.github.wnebyte.jcli;

import org.junit.Assert;
import org.junit.Test;
import java.util.regex.Pattern;
import com.github.wnebyte.jcli.annotation.Command;
import com.github.wnebyte.jcli.annotation.Argument;
import com.github.wnebyte.jcli.parser.BaseCommandParser;
import com.github.wnebyte.jcli.pattern.BaseCommandPatternGenerator;
import com.github.wnebyte.jcli.util.Identifier;
import com.github.wnebyte.jarguments.exception.ParseException;
import com.github.wnebyte.jarguments.factory.ArgumentCollectionFactoryBuilder;

public class BaseCommandParserTest {

    @Test
    public void testOnlyBooleanArgs() throws ParseException {
        BaseCommand cmd = newInstance(this, "foo0");
        String[] input = {
                "foo0 -a -b -c",
                "foo0 -a -b",
                "foo0 -a",
                "foo0 -c -b -a",
                "foo0 -c -b",
                "foo0 -c",
                "foo0 -b -a -c",
                "foo0 -b -c -a",
                "foo0 -b -c",
                "foo0 -b",
                "foo0"
        };
        boolean[][] values = {
                new boolean[] {
                        true, true, true
                },
                new boolean[] {
                        true, true, false
                },
                new boolean[] {
                        true, false, false
                },
                new boolean[] {
                        true, true, true
                },
                new boolean[] {
                        false, true, true
                },
                new boolean[] {
                        false, false, true
                },
                new boolean[] {
                        true, true, true
                },
                new boolean[] {
                        true, true, true
                },
                new boolean[] {
                        false, true, true
                },
                new boolean[] {
                        false, true, false
                },
                new boolean[] {
                        false, false, false
                },
        };
        Pattern pattern = new BaseCommandPatternGenerator(cmd).generatePattern();
        for (int i = 0; i < input.length; i++) {
            String s = input[i];
            Assert.assertTrue(matches(pattern, s));
            Object[] args = new BaseCommandParser(cmd).parse(s);

            for (int j = 0; j < values[i].length; j++) {
                boolean val = values[i][j];
                Assert.assertEquals(val, args[j]);
            }
        }
    }

    @Command(name = "foo0")
    public void foo0(
            @Argument(name = "-a")
            boolean a,
            @Argument(name = "-b")
            boolean b,
            @Argument(name = "-c")
            boolean c
    ) { }

    private static BaseCommand newInstance(Object object, String cmdName) {
        return new com.github.wnebyte.jcli.Command(
                () -> object,
                new Identifier(object.getClass(), cmdName).getMethod(),
                new ArgumentCollectionFactoryBuilder().build()
        );
    }

    private static boolean matches(Pattern pattern, String... input) {
        for (String s : input) {
            boolean matches = pattern.matcher(s).matches();
            if (!matches) {
                System.err.printf(
                        "Input: '%s' does not match the given pattern.%n", s
                );
                return false;
            }
        }
        return true;
    }
}
