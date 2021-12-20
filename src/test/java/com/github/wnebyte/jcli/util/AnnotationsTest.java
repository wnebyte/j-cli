package com.github.wnebyte.jcli.util;

import com.github.wnebyte.jcli.util.Identifier;
import org.junit.Test;
import org.junit.Assert;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Set;
import com.github.wnebyte.jcli.annotation.Command;
import com.github.wnebyte.jcli.annotation.*;
import com.github.wnebyte.jcli.util.Annotations;
import com.github.wnebyte.jarguments.Optional;
import com.github.wnebyte.jarguments.Positional;
import com.github.wnebyte.jarguments.Required;
import com.github.wnebyte.jarguments.convert.TypeConverter;

public class AnnotationsTest {

    @Test
    public void testCommandIsAnnotated00() throws NoSuchMethodException {
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
    public void testGetCommandNames00() throws NoSuchMethodException {
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
    public void testGetCommandDescription00() throws NoSuchMethodException {
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
        Parameter param = new Identifier(this.getClass(), "argumenttest00").getMethod().getParameters()[0];
        String[] names = Annotations.getNames(param);
        Assert.assertNotNull(names);
        Assert.assertEquals(1, names.length);
        Assert.assertTrue(names[0].equals("name") || names[0].equals("arg0"));
    }

    @Test
    public void testGetArgumentNames01() {
        Parameter param = new Identifier(this.getClass(), "argumenttest01").getMethod().getParameters()[0];
        String[] names = Annotations.getNames(param);
        Assert.assertNotNull(names);
        Assert.assertEquals(1, names.length);
        Assert.assertTrue(names[0].equals("name") || names[0].equals("arg0"));
    }

    @Test
    public void testGetArgumentNames02() {
        Parameter param = new Identifier(this.getClass(), "argumenttest02").getMethod().getParameters()[0];
        String[] names = Annotations.getNames(param);
        Assert.assertEquals(0, names.length);
        param = new Identifier(this.getClass(), "argumenttest03").getMethod().getParameters()[0];
        names = Annotations.getNames(param);
        Assert.assertNotNull(names);
        Assert.assertEquals(1, names.length);
        Assert.assertTrue(names[0].equals("name") || names[0].equals("arg0"));
    }

    @Test
    public void testGetArgumentDescription00() {
        Parameter param = new Identifier(this.getClass(), "argumenttest00").getMethod().getParameters()[0];
        String desc = Annotations.getDescription(param);
        Assert.assertEquals("desc", desc);
        param = new Identifier(this.getClass(), "argumenttest01").getMethod().getParameters()[0];
        desc = Annotations.getDescription(param);
        Assert.assertEquals("", desc);
        param = new Identifier(this.getClass(), "argumenttest03").getMethod().getParameters()[0];
        desc = Annotations.getDescription(param);
        Assert.assertEquals("", desc);
    }

    @Test
    public void testGetArgumentTypeConverter00() {
        Parameter param = new Identifier(this.getClass(), "convertertest00").getMethod().getParameters()[0];
        TypeConverter<?> converter = Annotations.getTypeConverter(param);
        Assert.assertNull(converter);
        param = new Identifier(this.getClass(), "convertertest01").getMethod().getParameters()[0];
        converter = Annotations.getTypeConverter(param);
        Assert.assertNotNull(converter);
        param = new Identifier(this.getClass(), "argumenttest03").getMethod().getParameters()[0];
        converter = Annotations.getTypeConverter(param);
        Assert.assertNull(converter);
    }

    @Test
    public void testGetArgumentSubClass00() {
        Parameter param = new Identifier(this.getClass(), "argumenttest00").getMethod().getParameters()[0];
        Class<? extends com.github.wnebyte.jarguments.Argument> sClass = Annotations.getSubClass(param);
        Assert.assertNotNull(sClass);
        Assert.assertEquals(Required.class, sClass);
        param = new Identifier(this.getClass(), "argumenttest02").getMethod().getParameters()[0];
        sClass = Annotations.getSubClass(param);
        Assert.assertNotNull(sClass);
        Assert.assertEquals(Optional.class, sClass);
        param = new Identifier(this.getClass(), "convertertest01").getMethod().getParameters()[0];
        sClass = Annotations.getSubClass(param);
        Assert.assertNotNull(sClass);
        Assert.assertEquals(Positional.class, sClass);
        param = new Identifier(this.getClass(), "subclasstest01").getMethod().getParameters()[0];
        sClass = Annotations.getSubClass(param);
        Assert.assertNotNull(sClass);
        Assert.assertEquals(Required.class, sClass);
    }

    @Test
    public void testGetArgumentDefaultValue00() {
        // annotated with @Argument and no explicit defaultValue
        Parameter param = new Identifier(this.getClass(), "argumenttest00").getMethod().getParameters()[0];
        String defaultValue = Annotations.getDefaultValue(param);
        Assert.assertNull(defaultValue);
        // not annotated with @Argument
        param = new Identifier(this.getClass(), "argumenttest03").getMethod().getParameters()[0];
        defaultValue = Annotations.getDefaultValue(param);
        Assert.assertNull(defaultValue);
        // annotated with @Argument and explicit defaultValue
        param = new Identifier(this.getClass(), "argumenttest02").getMethod().getParameters()[0];
        defaultValue = Annotations.getDefaultValue(param);
        Assert.assertNotNull(defaultValue);
        Assert.assertEquals("5", defaultValue);
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

    @Command(name = "argumenttest00")
    private void argumenttest00(
            @Argument(name = "name", description = "desc")
            int a
    ) {

    }

    @Command(name = "argumenttest01")
    private void argumenttest01(
            @Argument
            int name
    ) {

    }

    @Command(name = "argumenttest02")
    private void argumenttest02(
            @Argument(name = ",", group = Group.OPTIONAL, defaultValue = "5")
            int name
    ) {

    }

    @Command(name = "argumenttest03")
    private void argumenttest03(
            int name
    ) {

    }

    @Command(name = "convertertest00")
    private void convertertest00(
            @Argument
            int a
    ) {
    }

    @Command(name = "convertertest01")
    private void convertertest01(
            @Argument(typeConverter = IntegerTypeConverter.class, group = Group.POSITIONAL)
            int a
    ) {
    }

    @Command(name = "subclasstest01")
    private void subclasstest01(
            @Argument(group = Group.REQUIRED)
            int a
    ) {

    }

    public static class IntegerTypeConverter implements TypeConverter<Integer> {

        public IntegerTypeConverter() { }

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
