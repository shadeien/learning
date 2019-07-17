//package com.shadeien.jmh;
//
//import org.openjdk.jmh.annotations.*;
//import org.openjdk.jmh.runner.Runner;
//import org.openjdk.jmh.runner.RunnerException;
//import org.openjdk.jmh.runner.options.Options;
//import org.openjdk.jmh.runner.options.OptionsBuilder;
//
//import java.util.concurrent.TimeUnit;
//
//@BenchmarkMode(Mode.All)
//@OutputTimeUnit(TimeUnit.MILLISECONDS)
//@State(Scope.Benchmark)
//public class SecondBenchmark {
//    @Param({"100000"})
//    private int length;
//
//    private int[] numbers;
//    private Calculator singleThreadCalc;
//    private Calculator multiThreadCalc;
//
//    public static void main(String[] args) throws RunnerException {
//        Options opt = new OptionsBuilder()
//                .include(SecondBenchmark.class.getSimpleName()) // .include("JMHF.*") 可支持正则
//                .forks(0)
//                .warmupIterations(2)
//                .measurementIterations(2).threads(10)
//                .build();
//
//        new Runner(opt).run();
//    }
//
//    @Benchmark
//    public long singleThreadBench() {
//        return singleThreadCalc.sum(numbers);
//    }
//
//    @Benchmark
//    public long multiThreadBench() {
//        return multiThreadCalc.sum(numbers);
//    }
//
//    @Setup(Level.Trial)
//    public void prepare() {
//        int n = length;
//        numbers =new int[n];
//        for (int i=0;i<n;i++){
//            numbers[i]=i;
//        }
//        singleThreadCalc = new SinglethreadCalculator();
//        multiThreadCalc = new MultithreadCalculator(Runtime.getRuntime().availableProcessors());
//    }
//
//
//    @TearDown
//    public void shutdown() {
//        singleThreadCalc.shutdown();
//        multiThreadCalc.shutdown();
//    }
//}
