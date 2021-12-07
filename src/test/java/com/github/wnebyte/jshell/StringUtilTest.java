package com.github.wnebyte.jshell;

import org.junit.Assert;
import org.junit.Test;
import com.github.wnebyte.jshell.util.StringUtil;

import java.util.Arrays;
import java.util.List;

import static com.github.wnebyte.jshell.util.StringUtil.*;

public class StringUtilTest {

    @Test
    public void test00() {
        String name = "help";
        String normal = normalize(name);
        Assert.assertEquals("help", normal);

        name = "-help";
        normal = normalize(name);
        Assert.assertEquals("-help", normal);

        name = "help,";
        normal = normalize(name);
        Assert.assertEquals("help", normal);

        name = "$$help";
        normal = normalize(name);
        Assert.assertEquals("$$help", normal);

        name = " help";
        normal = normalize(name);
        Assert.assertEquals("help", normal);

        name = "[hel]p";
        normal = normalize(name);
        Assert.assertEquals("help", normal);
    }

    @Test
    public void test07() {
        String[] array = {};
        String toString = Arrays.toString(array);
        String str = toString.substring(1, toString.length() - 1);
        System.out.println("(".concat(str).concat(")"));
    }

    @Test
    public void test08() {
        String[] array = {"hello", "there", "you"};
        String toString = Arrays.toString(array);
        System.out.println(toString);
    }

    @Test
    public void test09() {
        String str = "prefix cmd -a 5 [1,2,3] \"this is a sentence\" -b";
        List<String> elements = StringUtil.splitByWhitespace(str);
        Assert.assertEquals(str, String.join(" ", elements));
        elements = StringUtil.split(str, ' ', '"');
        Assert.assertEquals(str, String.join(" ", elements));

        str = "cmd [\"this is\",\"a sentence\"] -b -c 900 -d [1,2,3]";
        elements = StringUtil.splitByWhitespace(str);
        Assert.assertEquals(str, String.join(" ", elements));
        Assert.assertEquals("cmd", elements.get(0));
        Assert.assertEquals("[\"this is\",\"a sentence\"]", elements.get(1));

        elements = StringUtil.split(str, ' ', '"');
        Assert.assertEquals(str, String.join(" ", elements));
        Assert.assertEquals("cmd", elements.get(0));
        Assert.assertEquals("[\"this is\",\"a sentence\"]", elements.get(1));
    }

    @Test
    public void test10() {
        String string = "[element1,\"ele,ment2\",element3]";
        List<String> elements = splitByComma(string);
        List<String> expected = Arrays.asList(
                "[element1",
                "\"ele,ment2\"",
                "element3]"
        );
        Assert.assertEquals(expected, elements);

        elements = split(string, ',', '"');
        expected = Arrays.asList(
                "[element1",
                "\"ele,ment2\"",
                "element3]"
        );
        Assert.assertEquals(expected, elements);
    }

    @Test
    public void test11() {
        String array = "[\"first element\",\"[[[]\"]";
        String newArray = StringUtil.removeFirstAndLast(array, '[', ']');
        System.out.println(newArray);
    }

    @Test
    public void test12() {
        String i = "";
        List<String> list = StringUtil.splitByWhitespace(i);
        System.out.println("size: " + list.size() + Arrays.toString(list.toArray()));
    }
}
