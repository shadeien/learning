package com.shadeien.leetcode.lambda;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
public class LambdaMain {
    public static void main(String[] args) {
        IntStream integers = IntStream.range(1, 11);
        OptionalInt res4 = integers.reduce((i, j) -> i + j);
        log.info("IntStream:{}", res4.getAsInt());

        List<String> list = Arrays.asList("test","hello","world","java","tom","C","javascript");
        list.stream().filter((s)->s.startsWith("j")).peek((st)->log.info("peek:{}", st)).collect(Collectors.toList());
        boolean res5 = list.stream().anyMatch((s) -> s.startsWith("j"));
        log.info("anyMatch((s) -> s.startsWith(\"j\")):{}", res5);
        long count = list.stream().filter((s)->s.startsWith("j")).count();
        log.info("startsWith(\"j\")).count:{}", count);
        Optional<String> res3 = list.stream().sorted((s1, s2) -> s1.length() - s2.length()).filter(s -> s.startsWith("nm")).map(s -> s = "abc_" + s).reduce((s1, s2) -> s1 + "|" + s2);
        log.info("Optional<String>:{}", res3.orElseGet(()->"1"));
        List<String> result = list.stream().filter(s -> s.length() > 4).collect(Collectors.toList());
        log.info("list filter:{}", result);

        Map<Integer, List<String>> res = list.stream().collect(Collectors.groupingBy(s->s.length()));
        log.info("list collect groupingBy:{}", res);

        Map<Boolean, List<String>> res1 = list.stream().collect(Collectors.partitioningBy(s -> s.indexOf("java") != -1));
        log.info("list collect partitioningBy:{}", res1);

        Stream.iterate(0, n -> n+2).limit(10).forEach((n)->log.info("Stream.iterate:{}", n));
        Stream.generate(()->Math.random()*100).limit(10).forEach((n)->log.info("Stream.generate:{}", n));
    }
}
