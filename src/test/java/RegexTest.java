import core.TypeConverter;
import core.TypeConverterRepository;
import org.junit.Assert;
import org.junit.Test;
import util.Splitter;

import java.util.Arrays;
import java.util.regex.Pattern;

public class RegexTest {

    @Test
    public void test00() {
        String input = "foo [ aw ]";

        Pattern pattern = Pattern.compile("^foo((\\sa\\s\\[(\\s*\\S*)*\\]))$");
        System.out.println(pattern.matcher(input).matches());
    }

    @Test
    public void test01() throws Exception {
        TypeConverter<Integer[]> typeConverter = TypeConverterRepository.getTypeConverter(Integer[].class);
        Integer[] array = typeConverter.convert("[1 2 3 4 5]");
        System.out.println(Arrays.toString(array));
    }

    @Test
    public void test02() {
        String userInput = "foo a [1 2 3 4 5]";
        String delimiter = "a";

        String a = userInput.split(delimiter.concat("\\s"), 2)[1];
        String b = a.split("\\s", 2)[0];
        System.out.println("a: " + a + "\nb: " + b);
    }

    @Test
    public void test03() {
        String a = "foo -a [ 1 2 3 ] -b test";
        String split = new Splitter().setContent(a).setDelimiter("-a").isArray(true).split();
        System.out.println(split);
    }

    @Test
    public void test04() {
        // "\\[(\\s*\\S*)*\\]";
        var r = "\\[[^\\s-]*\\]";
        var p = Pattern.compile(r);
        System.out.println(p.matcher("[]").matches());
    }

    @Test
    public void test05() {
        String[] arguments = { "-arg0", "-arg1", "-arg2" };
    }

    @Test
    public void test06() {
        String regex = "([^\\s-]*|\"[^\"]*\")";
        Pattern pattern = Pattern.compile(regex);
        String input = "hello there";
        System.out.println(pattern.matcher(input).matches());
    }

    // ^foo((\s-a\s[^\s-]*(\s-b\s[^\s-]*|))|((\s-b\s[^\s-]*|)\s-a\s[^\s-]*))$
    @Test
    public void test07() {
        String regex = "^foo\\s-a\\s([^\\s-]*|\"[^\"]*\")$";
        Pattern pattern = Pattern.compile(regex);
        String input1 = "foo -a \"hello there\"";
        String input2 = "foo -a hello";
        String input3 = "foo -a hello there";
        Assert.assertTrue(pattern.matcher(input1).matches());
        Assert.assertTrue(pattern.matcher(input2).matches());
        Assert.assertFalse(pattern.matcher(input3).matches());
    }

    // ^foo(((\s-b\s(^\s-]*|"[^"]*")|)\s-a\s(^\s-]*|"[^"]*"))|(\s-a\s(^\s-]*|"[^"]*")(\s-b\s(^\s-]*|"[^"]*")|)))$
    // ^foo((\s-a\s([^\s-]*|"[^"]*")(\s-b\s([^\s-]*|"[^"]*")|))|((\s-b\s([^\s-]*|"[^"]*")|)\s-a\s([^\s-]*|"[^"]*")))$
    @Test
    public void test08() {
        String regex1 = "^foo((\\s-a\\s[^\\s-]*(\\s-b\\s[^\\s-]*|))|((\\s-b\\s[^\\s-]*|)\\s-a\\s[^\\s-]*))$";
        String regex2 = regex1.replace("[^\\s-]*", "([^\\s-]*|\"[^\"]*\")");
        System.out.println(regex2);
        Pattern pattern = Pattern.compile(regex2);
        String input1 = "foo -a 5 -b 5";
        String input2 = "foo -a \"hello there1\" -b \"hello there 2\"";
        Assert.assertTrue(pattern.matcher(input1).matches());
        Assert.assertTrue(pattern.matcher(input2).matches());
    }

    @Test
    public void test09() {
        String regex = "^array((\\s\\[([^\\s]*|\"[^\"]*\")*\\](\\s-b\\s([^\\s]*|\"[^\"]*\")|))|)$";
        Pattern pattern = Pattern.compile(regex);
        System.out.println(pattern.matcher("array").matches());
    }

    @Test
    public void test10() {
        String regex = "^cmd\\s-a\\s" + "([^\\s\"]*|\"[^\"]*\")" + "$";
        Pattern pattern = Pattern.compile(regex);

        String input = "cmd -a testy";
        System.out.println(pattern.matcher(input).matches());


    }

    // "\\[([^\\s\"]*|\"[^\"]*\")*\\]"
    @Test
    public void test11() {
        String regex = "\\[\\s?(([^\\s\"]*|\"[^\"]*\")\\s?)*\\s?\\]";
        Pattern pattern = Pattern.compile(regex);
        String test = "[ \"element 1\",\"element 2\" ]";
        test = "[ element1, element2 ]";
        System.out.println(pattern.matcher(test).matches());
        System.out.println(Arrays.toString(TypeConverter.arraySplit(test)));
    }
}
