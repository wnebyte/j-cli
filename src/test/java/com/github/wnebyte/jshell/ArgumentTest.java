package com.github.wnebyte.jshell;

import com.github.wnebyte.jshell.annotation.Type;
import org.junit.Assert;
import org.junit.Test;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;

public class ArgumentTest {

    @Test
    public void test00() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<Required> constructor = Required.class
                .getDeclaredConstructor(Parameter.class, int.class);
        Required required = constructor
                .newInstance(getClass().getMethod("foo", String.class).getParameters()[0], 0);
        Argument duplicate = required;
        Assert.assertSame(required, duplicate);
        duplicate = constructor
                .newInstance(getClass().getMethod("bar", String.class).getParameters()[0], 0);
        Assert.assertNotSame(required, duplicate);

        Argument optional = constructor
                .newInstance(getClass().getMethod("opt", String.class).getParameters()[0], 0);
        Assert.assertNotSame(required, optional);
        Assert.assertNotEquals(required, optional);
    }

    public void foo(String s) {

    }

    public void bar(String s) {

    }

    @com.github.wnebyte.jshell.annotation.Command
    public void opt(
            @com.github.wnebyte.jshell.annotation.Argument(
                    type = Type.OPTIONAL
            )
            String s
    ) {}


    @Test
    public void test01() {
        AbstractClass first = new FirstSubClass();
        AbstractClass second = new SecondSubClass();
        System.out.println(first.hashCode() + " <-> " + second.hashCode());
        Assert.assertNotEquals(first.hashCode(), second.hashCode());
    }

    private static abstract class AbstractClass {
        @Override
        public int hashCode() {
            return 1;
        }
    }

    private static class FirstSubClass extends AbstractClass {
        @Override
        public int hashCode() {
            return 2;
        }
    }

    private static class SecondSubClass extends AbstractClass {
        @Override
        public int hashCode() {
            return 3;
        }
    }
}
