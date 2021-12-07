package com.github.wnebyte.jshell;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;

public class InstanceTrackerTest {

    public InstanceTrackerTest() {

    }

    @Test
    public void test00() throws Exception {
        InstanceTracker tracker = new InstanceTracker();
        tracker.trackObject(this);
        Object object = tracker.getObject(InstanceTrackerTest.class);
        Assert.assertSame(this, object);
    }

    @Test
    public void test01() throws Exception {
        InstanceTracker tracker = new InstanceTracker();
        Object object = tracker.getObject(InstanceTrackerTest.class);
        Assert.assertNotSame(this, object);
    }

    @Test
    public void test02() {
        InstanceTracker tracker = new InstanceTracker(new HashSet<Object>(){
            {
                add(new InstanceTrackerTest());
                add(new InstanceTrackerTest());
                add(new InstanceTrackerTest());
            }
        });
        int size = tracker.size();
        Assert.assertEquals(1, size);
    }
}
