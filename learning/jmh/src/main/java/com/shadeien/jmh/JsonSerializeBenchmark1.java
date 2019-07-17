package com.shadeien.jmh;

import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collection;

public class JsonSerializeBenchmark1 extends JsonSerializeBenchmarkBase {

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include("JsonSerializeBenchmark(1|2)")
                .forks(1)
                .warmupIterations(0)
                .measurementBatchSize(100000)
                .build();
        Collection<RunResult> results =  new Runner(opt).run();
    }


}
