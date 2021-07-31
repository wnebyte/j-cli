import org.junit.Assert;
import org.junit.Test;
import util.Splitter;
import util.StringUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static util.StringUtil.*;

public class StringUtilTest {

    @Test
    public void test00() {
        String name = "help";
        String normal = normalize(name, '-', true);
        Assert.assertEquals("-help", normal);

        name = "-help";
        normal = normalize(name, '-', true);
        Assert.assertEquals("-help", normal);

        name = "--help";
        normal = normalize(name, '-', true);
        Assert.assertEquals("--help", normal);

        name = "help";
        normal = normalize(name, '-', false);
        Assert.assertEquals("help", normal);

        name = "-help";
        normal = normalize(name, '-', false);
        Assert.assertEquals("-help", normal);

        name = "--help";
        normal = normalize(name, '-', false);
        Assert.assertEquals("--help", normal);
    }

    @Test
    public void test03() {
        String input = "foo \"test sentence\" 5";
        int val = numberOfWhitespaces(input);
        Assert.assertEquals(2, val);

        input = "test \"text ";
        val = numberOfWhitespaces(input);
        Assert.assertEquals(2, val);
    }

    @Test
    public void test04() {
        String input = "foo \"test sentence 5 \"hello there";
        int val = numberOfWhitespaces(input);
        Assert.assertEquals(2, val);

    }

    @Test
    public void test05() {
        String input = "foo \"first element\" \"second element\"";
        int val = indexOfNthWhiteSpace(input, 0);
        System.out.println(val);
        System.out.println(input.charAt(val + 1));
    }

    @Test
    public void test06() {
        String input = "foo \"first element\" \"second element\"";
        int startIndex = indexOfNthWhiteSpace(input, 1);
        String substring = input.substring(startIndex + 1);
        String value = new Splitter().setContent(substring).split();
        System.out.println(value);
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
        List<String> elements = StringUtil.split(str);
        Assert.assertEquals(str, String.join(" ", elements));

        str = "cmd [\"this is\",\"a sentence\"] -b -c 900 -d [1,2,3]";
        elements = StringUtil.split(str);
        Assert.assertEquals(str, String.join(" ", elements));
        Assert.assertEquals("cmd", elements.get(0));
        Assert.assertEquals("[\"this is\",\"a sentence\"]", elements.get(1));
    }
}
