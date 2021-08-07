import org.junit.Assert;
import org.junit.Test;
import util.ArgumentSplitter;
import util.Splitter;
import util.StringUtil;

import java.util.Arrays;
import java.util.List;

public class ParseTest {

    /**
     * Tests that an input String can be split correctly when input equals: <br/>
     * <code>"prefix command -a \"[ ]\" [\"element 1\",\"element 2\"]"</code>.
     */
    @Test
    public void test00() {
        final String array = "[\"element 1\",\"element 2\"]";
        final String input = "prefix command -a \"[ ]\" ".concat(array);
        final String split1 = new Splitter().setContent("-a \"[ ]\"").setDelimiter("-a").split();
        final String split2 = new Splitter().setContent(array).split();
        List<String> splitInput = StringUtil.splitByWhitespace(input);
        List<String> expected = Arrays.asList(
                "prefix", "command", "-a", "\"[ ]\"", "[\"element 1\",\"element 2\"]"
        );
        Assert.assertEquals(split1, "[ ]");
        Assert.assertEquals(split2, "\"element 1\",\"element 2\"");
        Assert.assertEquals(expected, splitInput);
    }

    /**
     * <code>"command -a \"[ [\" [\"element 1\",\"element 2\"]"</code>
     */
    @Test
    public void test01() {
        final String input = "command -a \"[ [\" [\"element 1\",\"element 2\"]";
        final List<String> splitInput = StringUtil.splitByWhitespace(input);
        Assert.assertEquals(4, splitInput.size());
    }

    @Test
    public void test02() {
        final String input = "command -a \"[ [\" [\"[ [\",\"[ [\"]";
        final List<String> splitInput = StringUtil.splitByWhitespace(input);
        Assert.assertEquals(4, splitInput.size());
    }

    @Test
    public void test03() {
        final String input = "-a \"this is a [[[] sentence\"";
        final String split = new ArgumentSplitter().setName("-a").setValue(input).split().get();
        final String test = new Splitter().setContent(input).setDelimiter("-a").split();
        System.out.println(split);
        System.out.println(test);
    }

    @Test
    public void test04() {
        final String input = "-a [[,]]]";
        final String split = new Splitter().setContent(input).setDelimiter("-a").split();
        final String test = new ArgumentSplitter().setName("-a").setValue(input).split().get();
        System.out.println(split);
        System.out.println(test);
    }

    // Todo: only normalize first and last index?
    @Test
    public void test05() {
        String input = "cmd -a \"test\" -person \"-a 5 -b \"hej\" [\"element 1\",\"element 2\"]\"";
        String value = new ArgumentSplitter()
                .setName("-person")
                .setValue("-person \"-a 5 -b \"hej\" [\"element 1\",\"element 2\"]\"")
                .split()
                .normalize(false)
                .get();
        System.out.println(value);
    }
}
