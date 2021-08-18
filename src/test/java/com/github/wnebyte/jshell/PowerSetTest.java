package com.github.wnebyte.jshell;

import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings({"unused", "MismatchedQueryAndUpdateOfCollection", "StatementWithEmptyBody"})
public class PowerSetTest {

    @Test
    public void test00() {
        Set<String> arg0 = new HashSet<>(Arrays.asList(
                "arg0", "--arg0"
        ));
        Set<String> arg1 = new HashSet<>(Arrays.asList(
                "arg1", "--arg1"
        ));
        Set<String> arg2 = new HashSet<>(Arrays.asList(
                "arg2", "--arg2"
        ));
        List<Set<String>> arguments = Arrays.asList(
                arg0, arg1, arg2
        );

        Set<Set<String>> val = new HashSet<>();

        for (Set<String> arg : arguments) {

            for (String name : arg) {
                Set<String> other = filter(arguments, name);
                other.add(name);
                Set<Set<String>> ps = new HashSet<>(Sets.powerSet(other));
                ps.removeIf(s -> !(s.size() == arguments.size()));
                val.addAll(ps);
            }
        }

      //  System.out.println(Arrays.toString(val.toArray()));
    }

    @Test
    public void test01() {
        Set<String> arg0 = new HashSet<>(Arrays.asList(
                "arg0", "--arg0"
        ));
        Set<String> arg1 = new HashSet<>(Arrays.asList(
                "arg1", "--arg1"
        ));
        Set<String> arg2 = new HashSet<>(Arrays.asList(
                "arg2", "--arg2"
        ));
        List<Set<String>> arguments = Arrays.asList(
                arg0, arg1, arg2
        );
        Set<String> other = filter(arguments, "arg0");
        Assert.assertFalse(other.contains("arg0"));
    }


    private Set<String> filter(final List<Set<String>> list, final String s) {
        List<Set<String>> norm = list.stream().filter(l -> !(l.contains(s))).collect(Collectors.toList());
        Set<String> set = new HashSet<String>();
        norm.forEach(set::addAll);
        return set;
    }

    private Set<String> powerSet(final Set<String> set) {
        Type type = new Type() {
            @Override
            public String getTypeName() {
                return "List<String>";
            }
        };
        return null;
    }

}
