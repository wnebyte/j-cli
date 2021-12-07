package com.github.wnebyte.jshell;

import com.github.wnebyte.jshell.annotation.Argument;
import com.github.wnebyte.jshell.annotation.Command;
import com.github.wnebyte.jshell.annotation.Type;

/**
 * Memory Performance Test Class.
 * 50 Commands
 *  - 5 Arguments each
 *    - 1 Positional Argument
 *    - 1 Required Argument
 *    - 3 Optional Arguments
 */
/*
@SuppressWarnings({"unused", "EmptyMethod"})
public class PerformanceTest {

    @Command
    public void test00(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test01(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test02(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test03(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test04(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test05(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test06(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test07(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test08(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test09(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test10(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test11(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test12(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test13(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test14(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test15(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test16(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test17(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test18(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test19(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test20(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test21(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test22(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test23(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test24(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test25(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test26(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test27(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test28(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test29(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test31(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test32(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test33(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test34(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test35(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test36(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test37(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test38(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test39(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test40(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test41(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test42(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test43(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test44(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test45(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test46(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test47(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test48(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }

    @Command
    public void test49(
            @Argument(type = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(type = Group.OPTIONAL)
                    int arg2,
            @Argument(type = Group.OPTIONAL)
                    int arg3,
            @Argument(type = Group.OPTIONAL)
                    int arg4
    ) {
    }
}
 */
