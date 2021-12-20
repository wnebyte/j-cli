package com.github.wnebyte.jcli;

import com.github.wnebyte.jcli.conf.Configuration;
import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class LookupPerformanceTest {

    private CLI cli;

    private String input = "test00 arg1 10 999 arg4 100";

    @Setup
    public void setup() {
        cli = new CLI(new Configuration()
                .nullifyScanPackages()
                .nullifyHelpCommand()
                .setScanClasses(PerformanceTest.class)
        );
    }

    // 0.01 ms, 10000 ns
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public int test() {
        cli.accept(input);
        return 0;
    }
}
