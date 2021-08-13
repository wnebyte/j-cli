package com.github.wnebyte.jshell;

import org.junit.Test;
import com.github.wnebyte.jshell.util.Splitter;
import com.github.wnebyte.jshell.util.StringUtil;

import java.util.Arrays;

public class SplitterTest {

    @Test
    public void test00() {
        String test = "command [1,2,3,4] [9,5,3]";
        String s = test.split("\\s")[2];
        System.out.println(s);

    }

    @Test
    public void test01() {
        String input = "foo -a \"com.github.wnebyte.jshell.test sentence.\"";
        String a = input.split("-a".concat("\\s").concat("\""), 2)[1];
        String b = a.split("\"", 2)[0];
        System.out.println(a);
        System.out.println(b);
    }

    @Test
    public void test02() {
        String input = "foo -a \"com.github.wnebyte.jshell.test sentence.\" -b 5";
        System.out.println("val: " +
                new Splitter().setContent(input).setDelimiter("-a")
                .split()
        );
    }

    @Test
    public void test03() {
        String input = "foo -a [1, 2, 3, 4] -b 5";
        System.out.println("val: " +
                new Splitter().setContent(input).setDelimiter("-a")
                        .split()
        );
    }

    @Test
    public void test04() {
        String input = "foo -a [\"hello there\", \"wsup in da\"] -b 5";
        System.out.println("val: " +
                new Splitter().setContent(input).setDelimiter("-a")
                        .split()
        );
    }

    @Test
    public void test05() {
        int index = 0;
        String input = "foo \"com.github.wnebyte.jshell.test first element\" \"com.github.wnebyte.jshell.test second element\"";
        String value = input.split("\\s")[index + 1];
        System.out.println(value);
    }

    @Test
    public void test06() {
        String input = "cmd -a \"-b hej -c 'some value' -d ['element 1','element2']\" -e val";
        System.out.println(Arrays.toString(StringUtil.splitByWhitespace(input).toArray()));
        System.out.println(Arrays.toString(StringUtil.splitByWhitespace(
                "-b hej -c 'some value' -d ['element 1','element2']"
        ).toArray()));
    }

    @Test
    public void test07() {
        String input = "-name 'first name' -age 27";
        System.out.println(Arrays.toString(StringUtil.splitByWhitespace(input).toArray()));
    }

}
