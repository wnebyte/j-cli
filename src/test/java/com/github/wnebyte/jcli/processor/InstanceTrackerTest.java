package com.github.wnebyte.jcli.processor;

import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;
import com.github.wnebyte.jcli.di.DependencyContainer;

public class InstanceTrackerTest {

    public InstanceTrackerTest() { }

    @Test
    public void test00() throws ReflectiveOperationException {
        InstanceTracker tracker = new InstanceTrackerImpl(new DependencyContainer());
        tracker.add(this);
        Object object = tracker.get(this.getClass());
        Assert.assertSame(this, object);
    }

    @Test
    public void test01() throws ReflectiveOperationException {
        InstanceTracker tracker = new InstanceTrackerImpl(new DependencyContainer());
        Object object = tracker.get(this.getClass());
        Assert.assertNotSame(this, object);
    }

    @Test
    public void test02() {
        InstanceTracker tracker = new InstanceTrackerImpl(new DependencyContainer());
        tracker.addAll(Arrays.asList(
                new InstanceTrackerTest(),
                new InstanceTrackerTest(),
                new InstanceTrackerTest()
        ));
        int size = tracker.size();
        Assert.assertEquals(1, size);
    }
}
