package com.github.wnebyte.jshell;

import com.github.wnebyte.jshell.annotation.Command;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class ReflectionPerformanceTest {

    public Class<?> cls = this.getClass();

    public Class<?>[] parameterTypes = { int.class, int.class };

    public String methodName = "foo";

    public Method method;

    public Object[] args = { 0, 0 };

    @Setup(Level.Invocation)
    public void setup() throws NoSuchMethodException {
        method = cls.getMethod(methodName, parameterTypes);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public int lookupTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = cls.getMethod(methodName, parameterTypes);
        method.setAccessible(true);
        method.invoke(this, args);
        return 0;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public int controlTest() throws InvocationTargetException, IllegalAccessException {
        method.setAccessible(true);
        method.invoke(this, args);
        return 0;
    }

    @Command
    public int foo(int a, int b) {
        return 0;
    }
}
