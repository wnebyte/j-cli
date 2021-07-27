import org.junit.Test;
import util.Splitter;

import java.util.Arrays;
import java.util.List;

public class SplitterTest {

    @Test
    public void test00() {
        String test = "command [1,2,3,4] [9,5,3]";
        String s = test.split("\\s")[2];
        System.out.println(s);

    }

    @Test
    public void test01() {
        String input = "foo -a \"test sentence.\"";
        String a = input.split("-a".concat("\\s").concat("\""), 2)[1];
        String b = a.split("\"", 2)[0];
        System.out.println(a);
        System.out.println(b);
    }

    @Test
    public void test02() {
        String input = "foo -a \"test sentence.\" -b 5";
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
        String input = "foo \"test first element\" \"test second element\"";
        String value = input.split("\\s")[index + 1];
        System.out.println(value);
    }

}
