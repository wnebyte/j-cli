package com.github.wnebyte.jcli.pattern;

import org.junit.Test;
import com.github.wnebyte.jcli.annotation.Argument;
import com.github.wnebyte.jcli.annotation.Command;
import com.github.wnebyte.jarguments.factory.ArgumentFactoryBuilder;
import com.github.wnebyte.jcli.BaseCommand;
import com.github.wnebyte.jcli.AbstractTestClass;
import com.github.wnebyte.jcli.annotation.Group;
import com.github.wnebyte.jcli.util.Identifier;
import org.junit.Assert;
import java.util.regex.Pattern;

public class BaseCommandPatternGeneratorTest extends AbstractTestClass {

    @Test
    public void testPattern00() {
        BaseCommand cmd = new com.github.wnebyte.jcli.Command(
                () -> this,
                new Identifier(this.getClass(), "foo0").getMethod(),
                new ArgumentFactoryBuilder().build()
        );
        Pattern pattern = new BaseCommandPatternGenerator(cmd).getPattern();
        Assert.assertTrue(pattern.matcher("foo0").matches());
        cmd = new com.github.wnebyte.jcli.Command(
                () -> this,
                new Identifier(this.getClass(), "foo1").getMethod(),
                new ArgumentFactoryBuilder().build()
        );
        pattern = new BaseCommandPatternGenerator(cmd).getPattern();
        Assert.assertTrue(super.allMatch(pattern,
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
        Assert.assertTrue(super.noneMatch(pattern,
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
        BaseCommand cmd = newInstance(this, "foo2");
        Pattern pattern = new BaseCommandPatternGenerator(cmd).getPattern();
        Assert.assertTrue(super.allMatch(pattern,
                "foo2 'this is a positional argument' -b \"this is a required argument\" -c",
                "foo2 'this is a positional argument' -b \"this is a required argument\"",
                "foo2 'this is a positional argument' -c -b \"this is a required argument\""
        ));
        Assert.assertTrue(super.allMatch(pattern,
                "foo2 -c 'this is a positional argument' -b \"this is a required argument\"",
                "foo2 -b \"this is a required argument\" -c 'this is a positional argument'"
                ));
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
