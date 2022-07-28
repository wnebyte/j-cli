package com.github.wnebyte.jcli;

import com.github.wnebyte.jcli.annotation.Argument;
import com.github.wnebyte.jcli.annotation.Command;

@SuppressWarnings("unused")
public class VisualVMTest {

    public static void main(String[] args) {
        // 0.85 MB heap used
        CLI cli = new CLI(new Configuration()
                .setScanClasses(VisualVMTest.class)
        );
        cli.run();
    }

    @Command
    public int test00(
            @Argument(required = true)
                    int arg0,
            @Argument(required = true)
                    int arg1,
            @Argument("arg2")
                    int arg2,
            @Argument("arg3")
                    int arg3,
            @Argument("arg4")
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test01(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test02(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test03(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test04(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test05(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test06(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test07(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test08(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test09(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test10(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test11(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test12(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test13(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test14(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test15(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test16(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test17(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test18(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test19(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test20(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test21(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test22(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test23(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test24(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test25(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test26(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test27(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test28(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test29(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test31(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test32(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test33(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test34(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test35(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test36(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test37(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test38(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test39(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test40(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test41(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test42(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test43(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test44(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test45(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test46(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test47(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test48(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }

    @Command
    public int test49(
            @Argument(required = true)
            int arg0,
            @Argument(required = true)
            int arg1,
            @Argument("arg2")
            int arg2,
            @Argument("arg3")
            int arg3,
            @Argument("arg4")
            int arg4
    ) {
        return 1;
    }
}
