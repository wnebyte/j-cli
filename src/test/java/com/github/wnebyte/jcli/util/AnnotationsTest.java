package com.github.wnebyte.jcli.util;

import java.util.Set;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import org.junit.Test;
import org.junit.Assert;
import com.github.wnebyte.jcli.annotation.Command;
import com.github.wnebyte.jcli.annotation.*;
import com.github.wnebyte.jarguments.adapter.TypeAdapter;

public class AnnotationsTest {

    @Test
    public void testCommandIsAnnotated00() throws NoSuchMethodException {
        Method method = new CommandIdentifier(this.getClass(), "foo").getMethod();
        boolean isAnnotated = Annotations.isAnnotated(method);
        Assert.assertTrue(isAnnotated);
        method = new CommandIdentifier(this.getClass(), "bar").getMethod();
        isAnnotated = Annotations.isAnnotated(method);
        Assert.assertTrue(isAnnotated);
        method = this.getClass().getDeclaredMethod("empty");
        isAnnotated = Annotations.isAnnotated(method);
        Assert.assertFalse(isAnnotated);
    }

    @Test
    public void testGetCommandNames00() throws NoSuchMethodException {
        Method method = new CommandIdentifier(this.getClass(), "foo").getMethod();
        Set<String> names = Annotations.getNames(method);
        Assert.assertNotNull(names);
        Assert.assertEquals(1, names.size());
        Assert.assertEquals("foo", names.toArray()[0]);
        method = this.getClass().getDeclaredMethod("empty");
        names = Annotations.getNames(method);
        Assert.assertNull(names);
    }

    @Test
    public void testGetCommandDescription00() throws NoSuchMethodException {
        Method method = new CommandIdentifier(this.getClass(), "bar").getMethod();
        String desc = Annotations.getDescription(method);
        Assert.assertNotNull(desc);
        Assert.assertEquals("desc", desc);
        method = new CommandIdentifier(this.getClass(), "foo").getMethod();
        desc = Annotations.getDescription(method);
        Assert.assertNotNull(desc);
        Assert.assertEquals("", desc);
        method = this.getClass().getDeclaredMethod("empty");
        desc = Annotations.getDescription(method);
        Assert.assertNull(desc);
    }

    @Test
    public void testControllerIsAnnotated00() {
        Class<?> cls = TestController.class;
        boolean isAnnotated = Annotations.isAnnotated(cls);
        Assert.assertTrue(isAnnotated);
        cls = this.getClass();
        isAnnotated = Annotations.isAnnotated(cls);
        Assert.assertFalse(isAnnotated);
    }

    @Test
    public void testGetControllerName00() {
        Class<?> cls = TestController.class;
        String name = Annotations.getName(cls);
        Assert.assertNotNull(name);
        cls = AnnotationLessController.class;
        name = Annotations.getName(cls);
        Assert.assertNull(name);
    }

    @Test
    public void testGetControllerScope00() {
        Class<?> cls = TestController.class;
        Scope scope = Annotations.getScope(cls);
        Assert.assertEquals(Scope.SINGLETON, scope);
        Assert.assertTrue(Annotations.isSingleton(cls));
        Assert.assertFalse(Annotations.isTransient(cls));
    }

    @Test
    public void testGetControllerScope01() {
        Class<?> cls = AnnotationLessController.class;
        Scope scope = Annotations.getScope(cls);
        Assert.assertNull(scope);
        Assert.assertFalse(Annotations.isSingleton(cls));
        Assert.assertFalse(Annotations.isTransient(cls));
    }

    @Test
    public void testGetArgumentNames00() {
        Parameter param = new CommandIdentifier(this.getClass(), "argumenttest00").getMethod().getParameters()[0];
        String names = Annotations.getNamesAsString(param);
        Assert.assertNotNull(names);
        Assert.assertEquals("name", names);
    }

    @Test
    public void testGetArgumentNames01() {
        Parameter param = new CommandIdentifier(this.getClass(), "argumenttest01").getMethod().getParameters()[0];
        String names = Annotations.getNamesAsString(param);
        Assert.assertNull(names);
    }

    @Test
    public void testGetArgumentNames02and03() {
        Parameter param = new CommandIdentifier(this.getClass(), "argumenttest02").getMethod().getParameters()[0];
        String names = Annotations.getNamesAsString(param);
        Assert.assertNotNull(names);
        param = new CommandIdentifier(this.getClass(), "argumenttest03").getMethod().getParameters()[0];
        names = Annotations.getNamesAsString(param);
        Assert.assertNull(names);
    }

    @Test
    public void testGetArgumentDescription00() {
        Parameter param = new CommandIdentifier(this.getClass(), "argumenttest00").getMethod().getParameters()[0];
        String desc = Annotations.getDescription(param);
        Assert.assertEquals("desc", desc);
        param = new CommandIdentifier(this.getClass(), "argumenttest01").getMethod().getParameters()[0];
        desc = Annotations.getDescription(param);
        Assert.assertEquals("", desc);
        param = new CommandIdentifier(this.getClass(), "argumenttest03").getMethod().getParameters()[0];
        desc = Annotations.getDescription(param);
        Assert.assertEquals("", desc);
    }

    @Test
    public void testGetArgumentDefaultValue00() {
        // annotated with @Argument and no explicit defaultValue
        Parameter param = new CommandIdentifier(this.getClass(), "argumenttest00").getMethod().getParameters()[0];
        String defaultValue = Annotations.getDefaultValue(param);
        Assert.assertNull(defaultValue);
        // not annotated with @Argument
        param = new CommandIdentifier(this.getClass(), "argumenttest03").getMethod().getParameters()[0];
        defaultValue = Annotations.getDefaultValue(param);
        Assert.assertNull(defaultValue);
        // annotated with @Argument and explicit defaultValue
        param = new CommandIdentifier(this.getClass(), "argumenttest02").getMethod().getParameters()[0];
        defaultValue = Annotations.getDefaultValue(param);
        Assert.assertNotNull(defaultValue);
        Assert.assertEquals("5", defaultValue);
    }

    @Test
    public void testGetChoices00() {
        Parameter param = new CommandIdentifier(this.getClass(), "choicestest00").getMethod().getParameters()[0];
        String[] choices = Annotations.getChoices(param);
        Assert.assertNull(choices);
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

    private static class AnnotationLessController {

    }

    @Command(value = "argumenttest00")
    private void argumenttest00(
            @Argument(value = "name", description = "desc")
            int a
    ) {

    }

    @Command(value = "argumenttest01")
    private void argumenttest01(
            @Argument
            int name
    ) {

    }

    @Command(value = "argumenttest02")
    private void argumenttest02(
            @Argument(value = ",", defaultValue = "5")
            int name
    ) {

    }

    @Command(value = "argumenttest03")
    private void argumenttest03(
            int name
    ) {

    }

    @Command(value = "convertertest00")
    private void convertertest00(
            @Argument
            int a
    ) {
    }


    @Command(value = "subclasstest01")
    private void subclasstest01(
            @Argument(value = "a", required = true)
            int a
    ) {

    }

    @Command
    public void choicestest00(
            @Argument(value = "a")
            int a
    ) {

    }

    public static class IntegerTypeAdapter implements TypeAdapter<Integer> {

        public IntegerTypeAdapter() { }

        @Override
        public Integer convert(String s) {
            return null;
        }

        @Override
        public Integer defaultValue() {
            return null;
        }
    }
}
