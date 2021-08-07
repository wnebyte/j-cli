import org.junit.Test;
import util.CollectionUtil;
import util.StringUtil;

import java.util.*;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

public class MyTest {

    @Test
    public void test00() {
        List<String> signature = Arrays.asList(
                "command-name", "*", "-a", "-b"
        );

        String input = "command-name posVal -a \"test sentence\"";
        List<String> components = StringUtil.splitByWhitespace(input);
        System.out.println(Arrays.toString(components.toArray()));

        float value = 0f;
        for (String component : components) {
            if (signature.contains(component)) {
                value++;
            }
        }
        float f = value / components.size();
        System.out.printf("%.0f/%d = %.2f%n", value, signature.size(), f);
    }

    @Test
    public void test01() {
        String input = "command-name posVal -a \"test sentence\"";
        Set<List<String>> signatures = new HashSet<>() {
            {
                add(Arrays.asList("command-name", "*", "-a", "-b"));
                add(Arrays.asList("command-name", "*", "-a", "-b", "-c"));
            }
        };
        List<String> components = StringUtil.splitByWhitespace(input);
        System.out.println(Arrays.toString(components.toArray()));

        float max = signatures.stream().map(signature -> likeness(signature, components))
                .max(Comparator.comparingDouble(value -> value))
                .orElse(0f);

        System.out.printf("%.2f%n", max);
    }

    private <T> float likeness(final Collection<T> signature, final Collection<T> components) {
        float value = 0f;
        if ((signature == null) || (components == null)) {
            return value;
        }
        for (T component : components) {
            if (signature.contains(component)) {
                value++;
            }
        }
       return value / components.size();
    }
}
