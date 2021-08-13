package com.github.wnebyte.jshell;

import com.github.wnebyte.jshell.exception.config.IllegalAnnotationException;
import org.junit.Test;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AnnotationProcessorTest {

    @Test(expected = IllegalAnnotationException.class)
    public void test00() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IllegalAnnotationException {

        Constructor<Command> constructor = getConstructor(Command.class, Object.class, Method.class);
        Method method1 = getMethod("foo");
        Method method2 = getMethod("foo1");

        Command command1 = constructor.newInstance(this, method1);
        Command command2 = constructor.newInstance(this, method2);

        Set<Command> transientCommand = new HashSet<>(Arrays.asList(command1, command2));
        AnnotationProcessor annotationProcessor = getConstructor(AnnotationProcessor.class).newInstance();
        annotationProcessor.process(transientCommand);
    }

    private Method getMethod(String name, Class<?>... parameterTypes)
            throws NoSuchMethodException {
        Method method = getClass().getMethod(name, parameterTypes);
        method.setAccessible(true);
        return method;
    }

    private <T> Constructor<T> getConstructor(Class<T> classOf, Class<?>... parameterTypes)
            throws NoSuchMethodException {
        Constructor<T> constructor = classOf.getDeclaredConstructor(parameterTypes);
        constructor.setAccessible(true);
        return constructor;
    }

    @com.github.wnebyte.jshell.annotation.Command(name = "foo")
    public void foo() {

    }

    @com.github.wnebyte.jshell.annotation.Command(name = "foo")
    public void foo1() {

    }
}
