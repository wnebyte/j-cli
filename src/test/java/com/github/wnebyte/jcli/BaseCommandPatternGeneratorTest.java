package com.github.wnebyte.jcli;

import com.github.wnebyte.jarguments.factory.ArgumentCollectionFactoryBuilder;
import com.github.wnebyte.jcli.annotation.Argument;
import com.github.wnebyte.jcli.annotation.Command;
import com.github.wnebyte.jcli.annotation.Group;
import com.github.wnebyte.jcli.pattern.BaseCommandPatternGenerator;
import com.github.wnebyte.jcli.util.Identifier;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Pattern;

public class BaseCommandPatternGeneratorTest {

    @Test
    public void testPattern00() {
        BaseCommand cmd = new com.github.wnebyte.jcli.Command(
                () -> this,
                new Identifier(this.getClass(), "foo0").getMethod(),
                new ArgumentCollectionFactoryBuilder().build()
        );
        Pattern pattern = new BaseCommandPatternGenerator(cmd).generatePattern();
        Assert.assertTrue(pattern.matcher("foo0").matches());
        cmd = new com.github.wnebyte.jcli.Command(
                () -> this,
                new Identifier(this.getClass(), "foo1").getMethod(),
                new ArgumentCollectionFactoryBuilder().build()
        );
        pattern = new BaseCommandPatternGenerator(cmd).generatePattern();
        Assert.assertTrue(matches(pattern,
                "foo1 -a hello -b test",
                "foo1 -b test -a hello",
                "foo1 --a hello -b test",
                "foo1 --a hello --b test",
                "foo1 --b test -a hello",
                "foo1 --b test --a hello",
                "foo1 -b hello",
                "foo1 --b hello",
                "foo1 -a test",
                "foo1 --a test",
                "foo1",
                "foo1 -a 'hello there'",
                "foo1 -a \"hello there\"",
                "foo1 -a \"hello there 'how are you'\""
                ));
        Assert.assertFalse(matches(cmd.getPattern(),
                "foo1 -a hello -b test ",
                " foo1 -a hello -b test",
                "foo -a hello -b test",
                "foo1 -a hello ---b test",
                "foo1 -A hello -b test",
                "+foo1 -a hello -b test",
                "$foo1 -a hello -b test",
                "foo1 -ahello -b test",
                "foo1-atest"
        ));
    }

    @Test
    public void testPattern01() {
        BaseCommand cmd = new com.github.wnebyte.jcli.Command(
                () -> this,
                new Identifier(this.getClass(), "foo2").getMethod(),
                new ArgumentCollectionFactoryBuilder().build()
        );
        Pattern pattern = new BaseCommandPatternGenerator(cmd).generatePattern();
        Assert.assertTrue(matches(pattern,
                "foo2 'this is a positional argument' -b \"this is a required argument\" -c",
                "foo2 'this is a positional argument' -b \"this is a required argument\"",
                "foo2 'this is a positional argument' -c -b \"this is a required argument\""
        ));
        Assert.assertFalse(matches(pattern,
                "foo2 -c 'this is a positional argument' -b \"this is a required argument\"",
                "foo2 -b \"this is a required argument\" -c 'this is a positional argument'"
                ));
    }

    private boolean matches(Pattern pattern, String... input) {
        for (String s : input) {
            boolean matches = pattern.matcher(s).matches();
            if (!matches)
                return false;
        }
        return true;
    }

    @Command(name = "foo0")
    private void foo0() { }

    @Command(name = "foo1")
    private void foo1(
            @Argument(name = "-a,--a", group = Group.OPTIONAL)
            String a,
            @Argument(name = "-b,--b", group = Group.OPTIONAL)
            String b
    ) { }

    @Command(name = "foo2")
    private void foo2(
            @Argument(name = "-a,--a", group = Group.POSITIONAL)
            String a,
            @Argument(name = "-b,--b", group = Group.REQUIRED)
            String b,
            @Argument(name = "-c,--c")
            boolean c
    ) {

    }
}
