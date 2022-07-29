package com.github.wnebyte.jcli.util;

import org.junit.Assert;
import org.junit.Test;
import com.github.wnebyte.jarguments.util.Sets;
import static com.github.wnebyte.jcli.util.Reflections.canInstantiate;

public class ReflectionsTest {

    @Test
    public void canInstantiateTest00() {
        Class<?> cls = MyClass.class;
        boolean b = canInstantiate(cls, Sets.of(String.class, int.class, double.class));
        Assert.assertTrue(b);
        b = canInstantiate(cls, Sets.of(String.class, int.class));
        Assert.assertTrue(b);
        b = canInstantiate(cls, Sets.of(String.class));
        Assert.assertFalse(b);
        b = canInstantiate(cls, Sets.of(int.class));
        Assert.assertFalse(b);
        b = canInstantiate(cls, Sets.newSet());
        Assert.assertFalse(b);
    }

    private static class MyClass {

        public MyClass(String s, int i) {

        }
    }
}
