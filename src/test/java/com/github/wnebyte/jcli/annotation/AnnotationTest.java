package com.github.wnebyte.jcli.annotation;

import org.junit.Test;
import com.github.wnebyte.jcli.CLI;
import com.github.wnebyte.jcli.Configuration;
import com.github.wnebyte.jcli.util.CommandIdentifier;
import com.github.wnebyte.jcli.exception.IllegalAnnotationException;

public class AnnotationTest {

    @Test(expected = IllegalAnnotationException.class)
    public void testDuplicateArgumentNames00() {
        CLI cli = new CLI(new Configuration()
                .disableScanPackages()
                .mapHelpCommand()
                .setScanCommandIdentifiers(new CommandIdentifier(this.getClass(), "argdup00"))
        );
    }

    @Test(expected = IllegalAnnotationException.class)
    public void testDuplicateArgumentNames01() {
        CLI cli = new CLI(new Configuration()
                .disableScanPackages()
                .mapHelpCommand()
                .setScanCommandIdentifiers(new CommandIdentifier(this.getClass(), "argdup01"))
        );
    }

    @Test(expected = IllegalAnnotationException.class)
    public void testDuplicateArgumentNames02() {
        CLI cli = new CLI(new Configuration()
                .disableScanPackages()
                .mapHelpCommand()
                .setScanCommandIdentifiers(new CommandIdentifier(this.getClass(), "argdup02"))
        );
    }

    @Test(expected = IllegalAnnotationException.class)
    public void testDuplicateCommandNames00() throws NoSuchMethodException {
        CLI cli = new CLI(new Configuration()
                .disableScanPackages()
                .mapHelpCommand()
                .setScanMethods(
                        this.getClass().getDeclaredMethod("cmddup00"),
                        this.getClass().getDeclaredMethod("cmddup00", int.class)
                )
        );
    }

    @Test(expected = IllegalAnnotationException.class)
    public void testDuplicateCommandNames01() throws NoSuchMethodException {
        CLI cli = new CLI(new Configuration()
                .disableScanPackages()
                .mapHelpCommand()
                .setScanMethods(
                        this.getClass().getDeclaredMethod("cmddup01"),
                        this.getClass().getDeclaredMethod("cmddup01mirror")
                )
        );
    }

    @Test(expected = IllegalAnnotationException.class)
    public void testDuplicateCommandNames02() throws NoSuchMethodException {
        CLI cli = new CLI(new Configuration()
                .disableScanPackages()
                .mapHelpCommand()
                .setScanMethods(
                        this.getClass().getDeclaredMethod("cmddup02"),
                        this.getClass().getDeclaredMethod("cmddup02", int.class)
                )
        );
    }

    @Test(expected = IllegalAnnotationException.class)
    public void testDuplicateHelpCommandNames00() {
        CLI cli = new CLI(new Configuration()
                .disableScanPackages()
                .setScanCommandIdentifiers(new CommandIdentifier(this.getClass(), "--help"))
        );
    }

    @Test(expected = IllegalAnnotationException.class)
    public void testCommandNameNormalization00() throws NoSuchMethodException {
        CLI cli = new CLI(new Configuration()
                .disableScanPackages()
                .mapHelpCommand()
                .setScanMethods(this.getClass().getDeclaredMethod("cmdnorm01"))
        );
    }

    @Test(expected = IllegalAnnotationException.class)
    public void testCommandNameNormalization01() throws NoSuchMethodException {
        CLI cli = new CLI(new Configuration()
                .disableScanPackages()
                .mapHelpCommand()
                .setScanMethods(this.getClass().getDeclaredMethod("cmdnorm02"))
        );
    }

    @Test(expected = IllegalAnnotationException.class)
    public void testArgumentNameNormalization02() throws NoSuchMethodException {
        CLI cli = new CLI(new Configuration()
                .disableScanPackages()
                .mapHelpCommand()
                .setScanMethods(this.getClass().getDeclaredMethod("argnorm01", int.class))
        );
    }

    @Command
    private int argdup00(
            @Argument(value = "a")
            int a,
            @Argument(value = "a")
            int b
    ) {
        return 1;
    }


    @Command
    private int argdup01(
            @Argument(value = "a")
                    int a,
            @Argument(value = "a, --a")
                    int b
    ) {
        return 1;
    }


    @Command
    private int argdup02(
            @Argument(value = "a, b, c")
                    int a,
            @Argument(value = "-a, a")
                    int b
    ) {
        return 1;
    }

    @Command
    private int cmddup00() {
        return 1;
    }

    @Command
    private int cmddup00(int a) {
        return 1;
    }

    @Command
    private int cmddup01() {
        return 1;
    }

    @Command(value = "cmddup01")
    private int cmddup01mirror() {
        return 1;
    }

    @Command(value = "cmddup02")
    private int cmddup02() {
        return 1;
    }

    @Command(value = "cmddup02")
    private int cmddup02(int a) {
        return 1;
    }

    @Command(value = "--help")
    private int help() {
        return 1;
    }

    @Command(value = ",")
    private int cmdnorm01() {
        return 1;
    }

    @Command(value = "$['")
    private int cmdnorm02() {
        return 1;
    }

    @Command(value = "argnorm01")
    private int argnorm01(
            @Argument(value = "$")
            int a
    ) {
        return 1;
    }

}