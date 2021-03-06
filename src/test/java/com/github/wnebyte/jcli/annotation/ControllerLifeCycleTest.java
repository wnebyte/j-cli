package com.github.wnebyte.jcli.annotation;

import org.junit.Test;
import com.github.wnebyte.jarguments.util.Console;
import com.github.wnebyte.jarguments.util.IConsole;
import com.github.wnebyte.jcli.CLI;
import com.github.wnebyte.jcli.Configuration;
import com.github.wnebyte.jcli.exception.ConfigException;

public class ControllerLifeCycleTest {

    /*
    ###########################
    #          TEST00         #
    ###########################
    */

    @Test
    public void testTransientClassNested00() {
        CLI cli = new CLI(new Configuration()
                .disableScanPackages()
                .setScanClasses(TransientClassNested00.class)
                .registerDependency(IConsole.class, new Console())
        );
        cli.accept("foo");
        cli.accept("foo");
    }

    @Controller(scope = Scope.TRANSIENT)
    private static class TransientClassNested00 {

        @Inject
        IConsole console;

        @Command
        public void foo() {
            console.out().println(this);
        }
    }

    /*
    ###########################
    #          TEST01         #
    ###########################
    */

    @Test
    public void testTransientClassNested01() {
        CLI cli = new CLI(new Configuration()
                .disableScanPackages()
                .setScanClasses(TransientClassNested01.class)
                .registerDependency(IConsole.class, new Console())
        );
        cli.accept("foo");
        cli.accept("foo");
    }

    @Controller(scope = Scope.TRANSIENT)
    private static class TransientClassNested01 {

        private IConsole console;

        @Inject
        public TransientClassNested01(IConsole console) {
            this.console = console;
        }

        @Command
        public void foo() {
            console.out().println(this);
        }
    }

    /*
    ###########################
    #          TEST03         #
    ###########################
    */

    @Test
    public void testSingletonClassNested00() {
        CLI cli = new CLI(new Configuration()
                .disableScanPackages()
                .setScanClasses(SingletonClassNested00.class)
                .registerDependency(IConsole.class, new Console())
        );
        cli.accept("foo");
        cli.accept("foo");
    }

    @Controller(scope = Scope.SINGLETON)
    private static class SingletonClassNested00 {

        @Inject
        private IConsole console;

        @Command
        public void foo() {
            console.out().println(this);
        }
    }

    /*
    ###########################
    #          TEST04         #
    ###########################
    */

    @Test
    public void testSingletonClassNested01() {
        CLI cli = new CLI(new Configuration()
                .disableScanPackages()
                .setScanClasses(SingletonClassNested01.class)
                .registerDependency(IConsole.class, new Console())
        );
        cli.accept("foo");
        cli.accept("foo");
    }

    @Controller(scope = Scope.SINGLETON)
    private static class SingletonClassNested01 {

        private IConsole console;

        @Inject
        public SingletonClassNested01(IConsole console) {
            this.console = console;
        }

        @Command
        public void foo() {
            console.out().println(this);
        }
    }

    /*
    ###########################
    #          TEST05         #
    ###########################
    */

    @Test(expected = ConfigException.class)
    public void testNoRegisteredDependency00() {
        CLI cli = new CLI(new Configuration()
                .disableScanPackages()
                .setScanClasses(Cls0.class)
        );
    }

    @Controller(scope = Scope.SINGLETON)
    private static class Cls0 {

        @Inject
        private String junk; // has not been registered with the dependency container.

        @Command
        public void foo() {
            System.out.println(junk);
        }
    }

    /*
    ###########################
    #          TEST06         #
    ###########################
    */

    @Test(expected = ConfigException.class)
    public void testNoRegisteredDependency01() {
        CLI cli = new CLI(new Configuration()
                .disableScanPackages()
                .setScanClasses(Cls1.class)
        );
    }

    @Controller(scope = Scope.TRANSIENT)
    private static class Cls1 {

        @Inject
        private String junk; // has not been registered with the dependency container.

        @Command
        public void foo() {
            System.out.println(junk);
        }
    }

    /*
    ###########################
    #          TEST07         #
    ###########################
    */

    @Test(expected = ConfigException.class)
    public void testNoRegisteredDependency02() {
        CLI cli = new CLI(new Configuration()
                .disableScanPackages()
                .setScanClasses(Cls2.class)
        );
    }

    @Controller(scope = Scope.SINGLETON)
    private static class Cls2 {

        private String junk; // has not been registered with the dependency container.

        @Inject
        public Cls2(String junk) {
            this.junk = junk;
        }

        @Command
        public void foo() {
            System.out.println(junk);
        }
    }

    /*
    ###########################
    #          TEST08         #
    ###########################
    */

    @Test(expected = ConfigException.class)
    public void testNoRegisteredDependency03() {
        CLI cli = new CLI(new Configuration()
                .disableScanPackages()
                .setScanClasses(Cls3.class)
        );
    }

    @Controller(scope = Scope.TRANSIENT)
    private static class Cls3 {

        private String junk; // has not been registered with the dependency container.

        @Inject
        public Cls3(String junk) {
            this.junk = junk;
        }

        @Command
        public void foo() {
            System.out.println(junk);
        }
    }

    /*
    ###########################
    #          TEST08         #
    ###########################
    */

    /*
    expecting an exception because #foo is not static, and is declared within a non-static class.
     */
    @Test(expected = ConfigException.class)
    public void testNonStaticMethodNonStaticClass() {
        CLI cli = new CLI(new Configuration()
                .disableScanPackages()
                .setScanClasses(Cls4.class)
        );
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    private class Cls4 {

        @Command
        public void foo() {

        }
    }
}
