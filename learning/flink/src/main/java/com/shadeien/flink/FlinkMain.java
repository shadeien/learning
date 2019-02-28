package com.shadeien.flink;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.FoldFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.wikiedits.WikipediaEditEvent;
import org.apache.flink.streaming.connectors.wikiedits.WikipediaEditsSource;
import org.apache.flink.util.Collector;

@Slf4j
public class FlinkMain {

    public static void main(String[] args) throws Exception {
        log.info("main");
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        wikieditsource(env);
        flatmap(env);
    }

    public static void flatmap(StreamExecutionEnvironment env) throws Exception {
        SingleOutputStreamOperator<Tuple2<String, Integer>> dataStream = env.socketTextStream("localhost", 9999)
                .flatMap(new Splitter())
                .keyBy(0)
                .timeWindow(Time.seconds(5))
//                .sum(1);
        .min(1);
        dataStream.print();
        env.execute("window wordcount");
    }

    public static class Splitter implements FlatMapFunction<String, Tuple2<String, Integer>> {

        @Override
        public void flatMap(String sentence, Collector<Tuple2<String, Integer>> collector) throws Exception {
            for (String word : sentence.split(" ")) {
                collector.collect(new Tuple2<String, Integer>(word, 1));
            }
        }
    }

    public static void wikieditsource(StreamExecutionEnvironment see) throws Exception {
        DataStream<WikipediaEditEvent> edits = see.addSource(new WikipediaEditsSource());

        KeyedStream<WikipediaEditEvent, String> keyedEdits = edits
                .keyBy(new KeySelector<WikipediaEditEvent, String>() {
                    @Override
                    public String getKey(WikipediaEditEvent event) {
                        return event.getUser();
                    }
                });

        DataStream<Tuple2<String, Long>> result = keyedEdits
                .timeWindow(Time.seconds(5))
                .fold(new Tuple2<>("", 0L), new FoldFunction<WikipediaEditEvent, Tuple2<String, Long>>() {
                    @Override
                    public Tuple2<String, Long> fold(Tuple2<String, Long> acc, WikipediaEditEvent event) {
                        acc.f0 = event.getUser();
                        acc.f1 += event.getByteDiff();
                        return acc;
                    }
                });

        result.print();

        see.execute();
    }
}
