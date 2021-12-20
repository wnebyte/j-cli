package com.github.wnebyte.jcli.parser;

import com.github.wnebyte.jcli.BaseCommand;
import com.github.wnebyte.jcli.BaseTestClass;
import com.github.wnebyte.jcli.annotation.Group;
import org.junit.Assert;
import org.junit.Test;

import com.github.wnebyte.jcli.annotation.Command;
import com.github.wnebyte.jcli.annotation.Argument;
import com.github.wnebyte.jarguments.exception.ParseException;

public class CommandParserTest extends BaseTestClass {

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
                new boolean[]{
                        true, true, true
                },
                new boolean[]{
                        true, true, false
                },
                new boolean[]{
                        true, false, false
                },
                new boolean[]{
                        true, true, true
                },
                new boolean[]{
                        false, true, true
                },
                new boolean[]{
                        false, false, true
                },
                new boolean[]{
                        true, true, true
                },
                new boolean[]{
                        true, true, true
                },
                new boolean[]{
                        false, true, true
                },
                new boolean[]{
                        false, true, false
                },
                new boolean[]{
                        false, false, false
                },
        };
        Assert.assertTrue(allMatch(cmd, input));
        for (int i = 0; i < input.length; i++) {
            String s = input[i];
            Object[] args = new CommandParser(cmd).parse(s);

            for (int j = 0; j < values[i].length; j++) {
                boolean val = values[i][j];
                Assert.assertEquals(val, args[j]);
            }
        }
    }

    @Test
    public void test01() throws ParseException {
        BaseCommand cmd = newInstance(this, "foo1");
        CommandParser parser = new CommandParser(cmd);
        String[] input = {
                "foo1 -a 5 100",
                "foo1 100 -a 5"
        };
        int[][] values = {
                {
                        5, 100
                },
                {
                        5, 100
                }
        };
        Assert.assertTrue(allMatch(cmd, input));
        for (int i = 0; i < input.length; i++) {
            String s = input[i];
            Object[] args = parser.parse(s);
            for (int j = 0; j < values[i].length; j++) {
                int value = values[i][j];
                Assert.assertEquals(value, args[j]);
            }
        }
    }

    /*
    two OPTIONAL,
    one POSITIONAL
    */
    @Test
    public void test02() throws ParseException {
        BaseCommand cmd = newInstance(this, "foo2");
        CommandParser parser = new CommandParser(cmd);
        String[] input = {
                "foo2 -a 5 -b 10 100",
                "foo2 -b 10 -a 5 100",
                "foo2 100 -a 5 -b 10",
                "foo2 100 -b 10 -a 5",
                "foo2 -a 5 100",
                "foo2 -b 10 100",
                "foo2 100 -a 5",
                "foo2 100 -b 10",
                "foo2 100"
        };
        int[][] values = {
                {
                        5, 10, 100
                },
                {
                        5, 10, 100
                },
                {
                        5, 10, 100
                },
                {
                        5, 10, 100
                },
                {
                        5, 0, 100
                },
                {
                        0, 10, 100
                },
                {
                        5, 0, 100
                },
                {
                        0, 10, 100
                },
                {
                        0, 0, 100
                },
        };
        Assert.assertTrue(allMatch(cmd, input));
        for (int i = 0; i < input.length; i++) {
            String s = input[i];
            Object[] args = parser.parse(s);
            for (int j = 0; j < values[i].length; j++) {
                int value = values[i][j];
                Assert.assertEquals(value, args[j]);
            }
        }
    }

    /*
    one OPTIONAL,
    one FLAG,
    two POSITIONAL
    */
    @Test
    public void test03() throws ParseException {
        BaseCommand cmd = newInstance(this, "foo3");
        CommandParser parser = new CommandParser(cmd);
        String[] input = {
                "foo3 -a 5 -b 100 50",
                "foo3 -b -a 5 100 50",
                "foo3 100 50 -a 5 -b",
                "foo3 50 100 -a 5 -b",
                "foo3 -a -b 50 100",
                "foo3 -a 50 -b 100 50",
                "foo3 50 100 -b -a 10",
                "foo3 50 100",
                "foo3 50 -a 10 100 -b",
                "foo3 50 -a 10 100",
                "foo3 100 -b 50"
        };
        Assert.assertTrue(super.allMatch(cmd, input));
        Object[][] values = {
                {
                        5, true, 100, 50
                }
        };
        Object[] args = parser.parse(input[0]);
        Assert.assertEquals(values[0][0], args[0]);
        Assert.assertEquals(values[0][1], args[1]);
        Assert.assertEquals(values[0][2], args[2]);
        Assert.assertEquals(values[0][3], args[3]);
    }

    /*
    one OPTIONAL,
    one FLAG,
    two POSITIONAL
    */
    @Test
    public void test04() throws ParseException {
        BaseCommand cmd = newInstance(this, "foo4");
        CommandParser parser = new CommandParser(cmd);
        String[] input = {
                "foo4 -a 5 -b 100 'hello world'", // 1st
                "foo4 -b -a 5 100 'hello world'",
                "foo4 100 'hello world' -a 5 -b",
                "foo4 -a 5 100 -b 'hello world'",
                "foo4 -a 50 -b 100 'hello world'", // 5th
                "foo4 100 -b -a 10 'hello world'",
                "foo4 100 'hello world'",
                "foo4 100 -a 10 'hello world' -b",
                "foo4 100 -a 10 'hello world'",
                "foo4 100 -b 'hello world'" // 10th
        };
        Object[][] values = {
                {
                        5, true, 100, "hello world" // 1st
                },
                {
                        5, true, 100, "hello world"
                },
                {
                        5, true, 100, "hello world"
                },
                {
                        5, true, 100, "hello world"
                },
                {
                        50, true, 100, "hello world" // 5 th
                },
                {
                        10, true, 100, "hello world"
                },
                {
                        0, false, 100, "hello world"
                },
                {
                        10, true, 100, "hello world"
                },
                {
                        10, false, 100, "hello world"
                },
                {
                        0, true, 100, "hello world" // 10 th
                },
        };
        Assert.assertTrue(allMatch(cmd, input));
        for (int i = 0; i < input.length; i++) {
            String s = input[i];
            Object[] args = parser.parse(s);
            for (int j = 0; j < values[i].length; j++) {
                Object value = values[i][j];
                Assert.assertEquals(value, args[j]);
            }
        }
    }

    @Test(expected = ParseException.class)
    public void testParseException04() throws ParseException {
        BaseCommand cmd = newInstance(this, "foo4");
        CommandParser parser = new CommandParser(cmd);
        String input = "foo4 -a 5 -b 'hello world' 100";
        Assert.assertTrue(allMatch(cmd, input));
        parser.parse(input);
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

    @Command(name = "foo1")
    public void foo1(
            @Argument(name = "-a", group = Group.REQUIRED)
            int a,
            @Argument(group = Group.POSITIONAL)
            int b
    ) {}

    @Command(name = "foo2")
    public void foo2(
            @Argument(name = "-a", group = Group.OPTIONAL)
            int a,
            @Argument(name = "-b", group = Group.OPTIONAL)
            int b,
            @Argument(group = Group.POSITIONAL)
            int c
    ) {}

    @Command(name = "foo3")
    public void foo3(
            @Argument(name = "-a", group = Group.OPTIONAL)
            int a,
            @Argument(name = "-b", group = Group.FLAG)
            boolean b,
            @Argument(group = Group.POSITIONAL)
            int c,
            @Argument(group = Group.POSITIONAL)
            int d
    ) {}

    @Command(name = "foo4")
    public void foo4(
            @Argument(name = "-a", group = Group.OPTIONAL)
            int a,
            @Argument(name = "-b", group = Group.FLAG)
            boolean b,
            @Argument(group = Group.POSITIONAL)
            int c,
            @Argument(group = Group.POSITIONAL)
            String d
    ) {}
}