package com.github.wnebyte.jcli.annotation;

import com.github.wnebyte.jcli.CLI;
import com.github.wnebyte.jcli.conf.Configuration;
import org.junit.Test;
import com.github.wnebyte.jcli.annotation.Argument;
import com.github.wnebyte.jcli.annotation.Command;
import com.github.wnebyte.jcli.exception.IllegalAnnotationException;
import com.github.wnebyte.jcli.util.Identifier;

public class AnnotationTest {

    @Test(expected = IllegalAnnotationException.class)
    public void testDuplicateArgumentNames00() {
        CLI cli = new CLI(new Configuration()
                .nullifyScanPackages()
                .nullifyHelpCommand()
                .setScanIdentifiers(new Identifier(this.getClass(), "argdup00"))
        );
    }

    @Test(expected = IllegalAnnotationException.class)
    public void testDuplicateArgumentNames01() {
        CLI cli = new CLI(new Configuration()
                .nullifyScanPackages()
                .nullifyHelpCommand()
                .setScanIdentifiers(new Identifier(this.getClass(), "argdup01"))
        );
    }

    @Test(expected = IllegalAnnotationException.class)
    public void testDuplicateArgumentNames02() {
        CLI cli = new CLI(new Configuration()
                .nullifyScanPackages()
                .nullifyHelpCommand()
                .setScanIdentifiers(new Identifier(this.getClass(), "argdup02"))
        );
    }

    @Test(expected = IllegalAnnotationException.class)
    public void testDuplicateCommandNames00() throws NoSuchMethodException {
        CLI cli = new CLI(new Configuration()
                .nullifyScanPackages()
                .nullifyHelpCommand()
                .setScanMethods(
                        this.getClass().getDeclaredMethod("cmddup00"),
                        this.getClass().getDeclaredMethod("cmddup00", int.class)
                )
        );
    }

    @Test(expected = IllegalAnnotationException.class)
    public void testDuplicateCommandNames01() throws NoSuchMethodException {
        CLI cli = new CLI(new Configuration()
                .nullifyScanPackages()
                .nullifyHelpCommand()
                .setScanMethods(
                        this.getClass().getDeclaredMethod("cmddup01"),
                        this.getClass().getDeclaredMethod("cmddup01mirror")
                )
        );
    }

    @Test(expected = IllegalAnnotationException.class)
    public void testDuplicateCommandNames02() throws NoSuchMethodException {
        CLI cli = new CLI(new Configuration()
                .nullifyScanPackages()
                .nullifyHelpCommand()
                .setScanMethods(
                        this.getClass().getDeclaredMethod("cmddup02"),
                        this.getClass().getDeclaredMethod("cmddup02", int.class)
                )
        );
    }

    @Test(expected = IllegalAnnotationException.class)
    public void testDuplicateHelpCommandNames00() {
        CLI cli = new CLI(new Configuration()
                .nullifyScanPackages()
                .setScanIdentifiers(new Identifier(this.getClass(), "--help"))
        );
    }

    @Test(expected = IllegalAnnotationException.class)
    public void testCommandNameNormalization00() throws NoSuchMethodException {
        CLI cli = new CLI(new Configuration()
                .nullifyScanPackages()
                .nullifyHelpCommand()
                .setScanMethods(this.getClass().getDeclaredMethod("cmdnorm01"))
        );
    }

    @Test(expected = IllegalAnnotationException.class)
    public void testCommandNameNormalization01() throws NoSuchMethodException {
        CLI cli = new CLI(new Configuration()
                .nullifyScanPackages()
                .nullifyHelpCommand()
                .setScanMethods(this.getClass().getDeclaredMethod("cmdnorm02"))
        );
    }

    @Test(expected = IllegalAnnotationException.class)
    public void testArgumentNameNormalization02() throws NoSuchMethodException {
        CLI cli = new CLI(new Configuration()
                .nullifyScanPackages()
                .nullifyHelpCommand()
                .setScanMethods(this.getClass().getDeclaredMethod("argnorm01", int.class))
        );
    }

    @Command
    private void argdup00(
            @Argument(name = "a")
            int a,
            @Argument(name = "a")
            int b
    ) { }


    @Command
    private void argdup01(
            @Argument(name = "a")
                    int a,
            @Argument(name = "a, --a")
                    int b
    ) { }


    @Command
    private void argdup02(
            @Argument(name = "a, b, c")
                    int a,
            @Argument(name = "-a, a")
                    int b
    ) { }

    @Command
    private void cmddup00() {

    }

    @Command
    private void cmddup00(int a) {

    }

    @Command
    private void cmddup01() {

    }

    @Command(name = "cmddup01")
    private void cmddup01mirror() {

    }

    @Command(name = "cmddup02")
    private void cmddup02() {

    }

    @Command(name = "cmddup02")
    private void cmddup02(int a) {

    }

    @Command(name = "--help")
    private void help() {

    }

    @Command(name = ",")
    private void cmdnorm01() {

    }

    @Command(name = "$['")
    private void cmdnorm02() {

    }

    @Command(name = "argnorm01")
    private void argnorm01(
            @Argument(name = "$")
            int a
    ) {

    }

}