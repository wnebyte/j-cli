package com.github.wnebyte.jshell;

import com.github.wnebyte.jshell.exception.runtime.ParseException;
import com.github.wnebyte.jshell.exception.config.NoDefaultConstructorException;
import org.junit.Assert;
import org.junit.Test;
import com.github.wnebyte.jshell.sample.SampleController;

import java.lang.reflect.*;
import java.util.List;

import static com.github.wnebyte.jshell.util.ReflectionUtil.*;

public class ReflectionTest {

    public static class StringTypeConverter implements TypeConverter<String> {

        @Override
        public String convert(String value) throws ParseException {
            return value;
        }

        @Override
        public String defaultValue() {
            return null;
        }

        @Override
        public boolean isArray() {
            return false;
        }
    }

    @Test
    public void test00() throws NoDefaultConstructorException {
        Class<?> myClass = StringTypeConverter.class;
        Object object = newInstance(myClass);
        Assert.assertTrue(object instanceof StringTypeConverter);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test02() throws NoSuchMethodException {
        Method method = getClass().getMethod("foo", List.class, String.class);
        method.setAccessible(true);

        Parameter parameter = method.getParameters()[0];
        Type parameterizedType = parameter.getParameterizedType();

        Parameter parameter1 = method.getParameters()[1];
        Type parameterizedType1 = parameter1.getParameterizedType();

        System.out.println("parameterizedType: " + parameterizedType);
        System.out.println("parameterizedType: " + parameterizedType1);

        Class<List<String>> clazz = (Class<List<String>>)(Class<?>) List.class;
        System.out.println(clazz);
    }

    public void foo(List<String> myList, String myString) {

    }

    @Test
    public void test03() throws NoDefaultConstructorException {
        Object rt = newInstance(this.getClass());

        for (Class<?> cls : rt.getClass().getDeclaredClasses()) {
            Object newInstance;

            if (isNested(cls)) {
                if (isStatic(cls)) {
                    newInstance = newInstance(cls);
                }
                else {
                    // Todo: would need all the outer classes as args
                    newInstance = newInstance(cls, rt);
                }
            }
            else {
                newInstance = newInstance(cls);
            }

            System.out.println(newInstance);
        }
    }

    public static class StaticNestedClass {

        public StaticNestedClass() {

        }
    }

    public class NestedClass {

        public NestedClass() {

        }
    }

    @Test
    public void test04() throws NoSuchMethodException {
        Class<?>[] args = { int.class, int.class };
        Method method = this.getClass().getDeclaredMethod("foo", args);
        System.out.println(method);
    }

    /*
    @Test(expected = NoSuchMethodException.class)
    public void test05() throws NoSuchMethodException {
        Class<?>[] args = { int.class, int.class };
        Method method = this.getClass().getMethod("foo", args);
        System.out.println(method);
    }
     */

    @com.github.wnebyte.jshell.annotation.Command
    private void foo(int a, int b) {

    }

}
