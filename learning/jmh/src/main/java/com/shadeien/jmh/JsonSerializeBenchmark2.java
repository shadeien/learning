package com.shadeien.jmh;

import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collection;

@Measurement(batchSize = 100000)
public class JsonSerializeBenchmark2 extends JsonSerializeBenchmarkBase {

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(JsonSerializeBenchmark2.class.getSimpleName())
                .forks(1)
                .warmupIterations(0)
                .build();
        Collection<RunResult> results =  new Runner(opt).run();
    }


}
