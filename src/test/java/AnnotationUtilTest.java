import annotation.Argument;
import annotation.Command;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static util.AnnotationUtil.*;

public class AnnotationUtilTest {

    @Test
    public void test00() throws NoSuchMethodException {
        Parameter param = getParameter("foo", 0, int.class);
        String argName = getName(param);
        Assert.assertEquals("arg0", argName);

        param = getParameter("test", 0, int.class);
        argName = getName(param);
        Assert.assertEquals("arg0", argName);
    }

    @Command
    public void foo(
            @Argument()
            int a
    ) {

    }

    @Command
    public void test(
            int testName
    ) {

    }

    private Parameter getParameter(String methodName, int index, Class<?>... parameterTypes) throws NoSuchMethodException {
        Method method = getClass().getMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.getParameters()[index];
    }
}
