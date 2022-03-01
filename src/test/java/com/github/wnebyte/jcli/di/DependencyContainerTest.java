package com.github.wnebyte.jcli.di;

import com.github.wnebyte.jcli.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;

public class DependencyContainerTest {

    @Test
    public void testInjectFields() throws ReflectiveOperationException {
        IDependencyContainer dependencyContainer = new DependencyContainer();
        dependencyContainer.registerDependency(String.class, "hello");
        ClassA cls = new ClassA();
        dependencyContainer.injectFields(cls);
        Assert.assertEquals("hello", cls.a);
    }

    @Test
    public void testInjectConstructor() throws ReflectiveOperationException {
        IDependencyContainer dependencyContainer = new DependencyContainer();
        dependencyContainer.registerDependency(String.class, "hello");
        Object cls = dependencyContainer.newConstructorInjection(ClassB.class);
        Assert.assertEquals("hello", ((ClassB) cls).a);
        ClassC clsC = (ClassC) dependencyContainer.newConstructorInjection(ClassC.class);
        Assert.assertEquals("hello", clsC.a);
    }

    private static class ClassA {

        @Resource
        private String a;
    }

    private static class ClassB {

        private String a;

        @Resource
        public ClassB() {
            this.a = "hello";
        }

        public ClassB(String s) {

        }
    }

    private static class ClassC {

        private String a;

        @Resource
        public ClassC(String a) {
            this.a = a;
        }
    }
}
