import convert.StringTypeConverter;
import exception.config.NoDefaultConstructorException;
import core.Console;
import core.IConsole;
import org.junit.Assert;
import org.junit.Test;
import sample.FirstClass;
import sample.SampleController;

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

    @Test
    public void test02() {
        Class<?> first = FirstClass.class;
        Class<?> second = FirstClass.SecondClass.class;
        Class<?> third = FirstClass.SecondClass.ThirdClass.class;

        System.out.println(first.getSimpleName() + ", declaring: " + first.getDeclaringClass() + ", " +
                "enclosing: " + first.getEnclosingClass());
        System.out.println(second.getSimpleName() + ", declaring: " + second.getDeclaringClass() + ", " +
                "enclosing: " + second.getEnclosingClass());
        System.out.println(third.getSimpleName() + ", declaring: " + third.getDeclaringClass() + ", " +
                "enclosing: " + third.getEnclosingClass());
    }

}
