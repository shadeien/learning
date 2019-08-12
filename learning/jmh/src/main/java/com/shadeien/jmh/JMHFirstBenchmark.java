package com.shadeien.jmh;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@BenchmarkMode(Mode.AverageTime)// 测试方法平均执行时间
@OutputTimeUnit(TimeUnit.MICROSECONDS)// 输出结果的时间粒度为微秒
@State(Scope.Benchmark) // 每个测试线程一个实例
public class JMHFirstBenchmark {

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        volatile double x = Math.PI;
    }

    @State(Scope.Thread)
    public static class ThreadState {
        volatile double x = Math.PI;
    }

    @Benchmark
    public void measureUnshared(ThreadState state) {
        // All benchmark threads will call in this method.
        //
        // However, since ThreadState is the Scope.Thread, each thread
        // will have it's own copy of the state, and this benchmark
        // will measure unshared case.
        state.x++;
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("measureUnshared:"+ state.x);
    }

    @Benchmark
    public void measureShared(BenchmarkState state) {
        // All benchmark threads will call in this method.
        //
        // Since BenchmarkState is the Scope.Benchmark, all threads
        // will share the state instance, and we will end up measuring
        // shared case.
        state.x++;
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("measureShared:"+ state.x);
    }

    public static void main(String[] args) throws RunnerException {
//        const String a = "";
//        Maps.newHashMapWithExpectedSize(2);
        Maps.newHashMapWithExpectedSize(50);
        HashMap<Object, Object> map = new HashMap<>(50);
        map.put("a", "b");

        Object data = map.get("a");
        log.info("{}", data);
        // 可以通过注解
        Options opt = new OptionsBuilder()
                .include(JMHFirstBenchmark.class.getSimpleName())
                .warmupBatchSize(2)
                .warmupIterations(2).warmupTime(TimeValue.valueOf("3s")) // 预热3次
//                .measurementBatchSize(1)
                .measurementIterations(2).measurementTime(TimeValue.valueOf("1s")) // 运行5次，每次10秒
                .threads(2) // 10线程并发
                .forks(2)
                .build();

        new Runner(opt).run();
    }


}
