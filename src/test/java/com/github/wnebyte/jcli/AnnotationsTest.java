package com.github.wnebyte.jcli;

import com.github.wnebyte.jcli.annotation.Controller;
import com.github.wnebyte.jcli.util.Annotations;
import com.github.wnebyte.jcli.annotation.Command;
import org.junit.Assert;
import org.junit.Test;
import java.lang.reflect.Method;
import java.util.Set;

public class AnnotationsTest {

    @Test
    public void testCommandIsAnnotated() throws NoSuchMethodException {
        Method method = new Identifier(this.getClass(), "foo").getMethod();
        boolean isAnnotated = Annotations.isAnnotated(method);
        Assert.assertTrue(isAnnotated);
        method = new Identifier(this.getClass(), "bar").getMethod();
        isAnnotated = Annotations.isAnnotated(method);
        Assert.assertTrue(isAnnotated);
        method = this.getClass().getDeclaredMethod("empty");
        isAnnotated = Annotations.isAnnotated(method);
        Assert.assertFalse(isAnnotated);
    }

    @Test
    public void testGetCommandNames() throws NoSuchMethodException {
        Method method = new Identifier(this.getClass(), "foo").getMethod();
        Set<String> names = Annotations.getNames(method);
        Assert.assertNotNull(names);
        Assert.assertEquals(1, names.size());
        Assert.assertEquals("foo", names.toArray()[0]);
        method = this.getClass().getDeclaredMethod("empty");
        names = Annotations.getNames(method);
        Assert.assertNull(names);
    }

    @Test
    public void testGetCommandDescription() throws NoSuchMethodException {
        Method method = new Identifier(this.getClass(), "bar").getMethod();
        String desc = Annotations.getDescription(method);
        Assert.assertNotNull(desc);
        Assert.assertEquals("desc", desc);
        method = new Identifier(this.getClass(), "foo").getMethod();
        desc = Annotations.getDescription(method);
        Assert.assertNotNull(desc);
        Assert.assertEquals("", desc);
        method = this.getClass().getDeclaredMethod("empty");
        desc = Annotations.getDescription(method);
        Assert.assertNull(desc);
    }

    @Test
    public void testControllerIsAnnotated() {
        Class<?> cls = TestController.class;
        boolean isAnnotated = Annotations.isAnnotated(cls);
        Assert.assertTrue(isAnnotated);
        cls = this.getClass();
        isAnnotated = Annotations.isAnnotated(cls);
        Assert.assertFalse(isAnnotated);
    }

    @Test
    public void testControllerName() {
        Class<?> cls = TestController.class;
        String name = Annotations.getName(cls);
        Assert.assertNotNull(name);
    }

    @Command
    public void foo() {

    }

    @Command(description = "desc")
    public void bar() {

    }

    private void empty() {

    }

    @Controller
    private static class TestController {

    }
}
