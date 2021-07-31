import util.StringTypeConverter;
import exception.config.NoDefaultConstructorException;
import core.Console;
import core.IConsole;
import org.junit.Assert;
import org.junit.Test;
import sample.SampleController;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static util.ReflectionUtil.*;

public class ReflectionTest {

    @Test
    public void test00() throws NoDefaultConstructorException {
        Class<?> myClass = StringTypeConverter.class;
        Object object = newInstance(myClass);
        Assert.assertTrue(object instanceof StringTypeConverter);
    }

    @Test
    public void test01() throws NoDefaultConstructorException {
        Class<?> myClass = SampleController.class;
        IConsole console = new Console();

        Object object = newInstance(myClass, console);
        Assert.assertTrue(object instanceof SampleController);
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
}
