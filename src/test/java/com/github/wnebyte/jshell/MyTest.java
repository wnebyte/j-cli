package com.github.wnebyte.jshell;

import org.junit.Test;
import com.github.wnebyte.jshell.util.StringUtil;

import java.util.*;

public class MyTest {

    @Test
    public void test00() {
        List<String> signature = Arrays.asList(
                "command-name", "*", "-a", "-b"
        );

        String input = "command-name posVal -a \"com.github.wnebyte.jshell.test sentence\"";
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
        String input = "command-name posVal -a \"com.github.wnebyte.jshell.test sentence\"";
        Set<List<String>> signatures = new HashSet<List<String>>() {
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
