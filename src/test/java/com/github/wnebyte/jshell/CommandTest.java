package com.github.wnebyte.jshell;

import org.junit.Assert;
import org.junit.Test;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CommandTest {

    @Test
    public void test00()
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<Command> constructor = Command.class
                .getDeclaredConstructor(Object.class, Method.class);
        Command firstCommand = constructor
                .newInstance(this, getClass().getMethod("foo"));
        Command duplicate = firstCommand;
        Assert.assertSame(firstCommand, duplicate);

        Command secondCommand = constructor
                .newInstance(this, getClass().getMethod("bar", String.class));
        duplicate = secondCommand;
        Assert.assertNotEquals(firstCommand, secondCommand);
        Assert.assertSame(secondCommand, duplicate);
    }

    @com.github.wnebyte.jshell.annotation.Command
    public void foo() {}

    @com.github.wnebyte.jshell.annotation.Command
    public void bar(String s) {}


}
