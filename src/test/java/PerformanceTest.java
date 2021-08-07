import annotation.Argument;
import annotation.Command;
import core.*;
import org.junit.Test;

import javax.swing.text.html.Option;
import java.util.Arrays;

/**
 * Memory Performance Test Class.
 * 50 Commands
 *  - 5 Arguments each
 *    - 1 Positional Argument
 *    - 1 Required Argument
 *    - 3 Optional Arguments
 */
@SuppressWarnings("unused")
public class PerformanceTest {

    public static void main(String[] args) {
        Shell shell = new Shell(new ConfigurationBuilder()
                .setConsole(new Console())
                .setScanClasses(PerformanceTest.class)
                .disableAutoComplete()
                .nullifyScanPackages()
        );
        shell.run();
    }

    @Command
    public void test00(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test01(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test02(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test03(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test04(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test05(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test06(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test07(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test08(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test09(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test10(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test11(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test12(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test13(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test14(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test15(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test16(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test17(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test18(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test19(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test20(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test21(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test22(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test23(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test24(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test25(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test26(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test27(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test28(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test29(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test31(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test32(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test33(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test34(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test35(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test36(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test37(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test38(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test39(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test40(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test41(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test42(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test43(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test44(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test45(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test46(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test47(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test48(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }

    @Command
    public void test49(
            @Argument(type = Positional.class)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Optional.class)
                    int arg2,
            @Argument(type = Optional.class)
                    int arg3,
            @Argument(type = Optional.class)
                    int arg4
    ) {
    }
}
