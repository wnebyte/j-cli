import annotation.Command;
import org.junit.Assert;
import org.junit.Test;
import util.Bundle;
import util.Scanner;

import java.util.Arrays;
import java.util.Set;

public class ScannerTest {

    @Test
    public void test00() {
        Scanner scanner = new Scanner();
        scanner.scanClasses(Set.of(ScannerTest.class));
        Assert.assertEquals(3, scanner.getScanned().size());
        scanner = new Scanner();
        scanner.scanClasses(Set.of());
        Assert.assertEquals(0, scanner.getScanned().size());
        scanner = new Scanner();
        scanner.scanClasses(null);
        Assert.assertEquals(0, scanner.getScanned().size());
    }

    @Test
    public void test01() {
        Scanner scanner = new Scanner();
        scanner.scanBundle(new Bundle(this, "foo", "foo1"));
        Assert.assertEquals(2, scanner.getScanned().size());
        scanner = new Scanner();
        scanner.scanBundle(new Bundle(this, (String[]) null));
        Assert.assertEquals(0, scanner.getScanned().size());
    }

    @Command()
    public void foo() {}

    @Command()
    public void foo1() {}

    @Command()
    public void foo2() {}

}
