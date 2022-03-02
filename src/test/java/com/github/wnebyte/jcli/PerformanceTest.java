package com.github.wnebyte.jcli;

import com.github.wnebyte.jcli.annotation.Argument;
import com.github.wnebyte.jcli.annotation.Command;
import com.github.wnebyte.jcli.annotation.Group;
import com.github.wnebyte.jcli.conf.Configuration;

@SuppressWarnings("unused")
public class PerformanceTest {

    public static void main(String[] args) {
        // 0.85 MB used heap
        CLI cli = new CLI(new Configuration()
                .nullifyScanPackages()
                .setScanClasses(PerformanceTest.class)
        );
        cli.read();
    }

    @Command
    public int test00(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test01(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test02(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test03(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test04(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test05(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test06(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test07(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test08(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test09(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test10(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test11(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test12(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test13(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test14(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test15(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test16(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test17(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test18(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test19(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test20(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test21(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test22(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test23(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test24(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test25(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test26(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test27(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test28(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test29(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test31(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test32(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test33(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test34(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test35(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test36(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test37(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test38(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test39(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test40(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test41(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test42(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test43(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test44(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test45(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test46(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test47(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test48(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }

    @Command
    public int test49(
            @Argument(group = Group.POSITIONAL)
                    int arg0,
            @Argument()
                    int arg1,
            @Argument(group = Group.OPTIONAL)
                    int arg2,
            @Argument(group = Group.OPTIONAL)
                    int arg3,
            @Argument(group = Group.OPTIONAL)
                    int arg4
    ) {
        return 1;
    }
}
