package com.github.wnebyte.jshell;

import org.openjdk.jmh.annotations.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@State(Scope.Benchmark)
public class MatchPerformanceTest {

    public HashMap<String, String> stringHashMap;

    public HashMap<Pattern, String> patternHashMap;

    public String input = "test45 1000 arg1 9090 arg2 5 arg3 400 arg4 2000";

    @Setup(Level.Invocation)
    public void setup() {
        stringHashMap = new LinkedHashMap<>(50);
        patternHashMap = new LinkedHashMap<>(50);

        for (int i = 0; i < 50; i++) {
            String string = "^test" + i + "((\\s([^\\s\"]*|\"[^\"]*\")\\sarg1\\s([^\\s\"]*|\"[^\"]*\")(\\sarg2\\s([^\\s\"]*|\"[^\"]*\")|)(\\sarg3\\s([^\\s\"]*|\"[^\"]*\")|)(\\sarg4\\s([^\\s\"]*|\"[^\"]*\")|))|(\\s([^\\s\"]*|\"[^\"]*\")(\\sarg3\\s([^\\s\"]*|\"[^\"]*\")|)(\\sarg4\\s([^\\s\"]*|\"[^\"]*\")|)(\\sarg2\\s([^\\s\"]*|\"[^\"]*\")|)\\sarg1\\s([^\\s\"]*|\"[^\"]*\"))|(\\s([^\\s\"]*|\"[^\"]*\")(\\sarg3\\s([^\\s\"]*|\"[^\"]*\")|)(\\sarg4\\s([^\\s\"]*|\"[^\"]*\")|)\\sarg1\\s([^\\s\"]*|\"[^\"]*\")(\\sarg2\\s([^\\s\"]*|\"[^\"]*\")|))|(\\s([^\\s\"]*|\"[^\"]*\")(\\sarg4\\s([^\\s\"]*|\"[^\"]*\")|)\\sarg1\\s([^\\s\"]*|\"[^\"]*\")(\\sarg2\\s([^\\s\"]*|\"[^\"]*\")|)(\\sarg3\\s([^\\s\"]*|\"[^\"]*\")|))|(\\s([^\\s\"]*|\"[^\"]*\")(\\sarg2\\s([^\\s\"]*|\"[^\"]*\")|)\\sarg1\\s([^\\s\"]*|\"[^\"]*\")(\\sarg3\\s([^\\s\"]*|\"[^\"]*\")|)(\\sarg4\\s([^\\s\"]*|\"[^\"]*\")|))|(\\s([^\\s\"]*|\"[^\"]*\")(\\sarg3\\s([^\\s\"]*|\"[^\"]*\")|)(\\sarg2\\s([^\\s\"]*|\"[^\"]*\")|)(\\sarg4\\s([^\\s\"]*|\"[^\"]*\")|)\\sarg1\\s([^\\s\"]*|\"[^\"]*\"))|(\\s([^\\s\"]*|\"[^\"]*\")(\\sarg2\\s([^\\s\"]*|\"[^\"]*\")|)(\\sarg3\\s([^\\s\"]*|\"[^\"]*\")|)(\\sarg4\\s([^\\s\"]*|\"[^\"]*\")|)\\sarg1\\s([^\\s\"]*|\"[^\"]*\"))|(\\s([^\\s\"]*|\"[^\"]*\")(\\sarg4\\s([^\\s\"]*|\"[^\"]*\")|)(\\sarg3\\s([^\\s\"]*|\"[^\"]*\")|)\\sarg1\\s([^\\s\"]*|\"[^\"]*\")(\\sarg2\\s([^\\s\"]*|\"[^\"]*\")|))|(\\s([^\\s\"]*|\"[^\"]*\")\\sarg1\\s([^\\s\"]*|\"[^\"]*\")(\\sarg2\\s([^\\s\"]*|\"[^\"]*\")|)(\\sarg4\\s([^\\s\"]*|\"[^\"]*\")|)(\\sarg3\\s([^\\s\"]*|\"[^\"]*\")|))|(\\s([^\\s\"]*|\"[^\"]*\")(\\sarg4\\s([^\\s\"]*|\"[^\"]*\")|)\\sarg1\\s([^\\s\"]*|\"[^\"]*\")(\\sarg3\\s([^\\s\"]*|\"[^\"]*\")|)(\\sarg2\\s([^\\s\"]*|\"[^\"]*\")|))|(\\s([^\\s\"]*|\"[^\"]*\")(\\sarg2\\s([^\\s\"]*|\"[^\"]*\")|)(\\sarg3\\s([^\\s\"]*|\"[^\"]*\")|)\\sarg1\\s([^\\s\"]*|\"[^\"]*\")(\\sarg4\\s([^\\s\"]*|\"[^\"]*\")|))|(\\s([^\\s\"]*|\"[^\"]*\")\\sarg1\\s([^\\s\"]*|\"[^\"]*\")(\\sarg4\\s([^\\s\"]*|\"[^\"]*\")|)(\\sarg2\\s([^\\s\"]*|\"[^\"]*\")|)(\\sarg3\\s([^\\s\"]*|\"[^\"]*\")|)))$";
            Pattern pattern = Pattern.compile(string);
            stringHashMap.put(string, null);
            patternHashMap.put(pattern, null);
        }
    }

    @Benchmark()
    @Fork(value = 1, warmups = 2)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public int stringTest(MatchPerformanceTest test) {
        for (Map.Entry<String, String> kv : test.stringHashMap.entrySet()) {
            boolean matches = Pattern.compile(kv.getKey()).matcher(test.input).matches();
            if (matches) {
                break;
            }
        }
        return 1;
    }

    @Benchmark()
    @Fork(value = 1, warmups = 2)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public int patternTest(MatchPerformanceTest test) {
        for (Map.Entry<Pattern, String> kv : test.patternHashMap.entrySet()) {
            boolean matches = kv.getKey().matcher(test.input).matches();
            if (matches) {
                break;
            }
        }
        return 1;
    }
}
