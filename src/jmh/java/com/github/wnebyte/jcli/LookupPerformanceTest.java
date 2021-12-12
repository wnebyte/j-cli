package com.github.wnebyte.jcli;

import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

public class LookupPerformanceTest {

    private CLI cli;

    private String input = "";

    @Setup
    public void setup() {
        cli = new CLI(new Configuration()
                .nullifyScanPackages()
        );
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public int test() {
        cli.accept(input);
        return 0;
    }
}
