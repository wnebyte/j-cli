import org.junit.Assert;
import org.junit.Test;
import util.InstanceTracker;

import java.util.HashSet;

public class InstanceTrackerTest {

    public InstanceTrackerTest() {

    }

    @Test
    public void test00() throws Exception {
        InstanceTracker tracker = new InstanceTracker();
        tracker.addObject(this);
        Object object = tracker.addClass(InstanceTrackerTest.class);
        Assert.assertSame(this, object);
    }

    @Test
    public void test01() throws Exception {
        InstanceTracker tracker = new InstanceTracker();
        Object object = tracker.addClass(InstanceTrackerTest.class);
        Assert.assertNotSame(this, object);
    }

    @Test
    public void test02() {
        InstanceTracker tracker = new InstanceTracker(new HashSet<>(){
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
