package com.shadeien.leetcode.lambda;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
public class LambdaMain {

    @Data
    static
    class RuleIndex {
        private List<Object> areaId;

        public RuleIndex(List<Object> asList) {
            this.areaId = asList;
        }
    }

    public static void print(String s, Print<String> print) {
        print.print(s);
    }

    public static void main(String[] args) {

        print("abc", (s) -> System.out.println(s));

        List<Long> userIds = new ArrayList<>();
        List<Long> arrs = Arrays.asList(1l, 2l, 3l, 4l, 5l);
        for (Long cardId : arrs) {
            List<Long> currentList = new ArrayList<>();
            currentList.add(1l);
            currentList.add(3l);
            currentList.add(cardId);
            if (userIds.size() == 0) {
                userIds = currentList;
            } else {
                userIds = (List<Long>) org.apache.commons.collections4.CollectionUtils.intersection(currentList, userIds);
            }
        }
        log.info("list:{}", userIds);

//        String[] arg = {"1", "2"};
//        Stream.of(arg).peek(o->log.info("test:{}", o)).collect();
        RuleIndex ruleIndex1 = new RuleIndex(Arrays.asList(100l,101l));
        RuleIndex ruleIndex2 = new RuleIndex(Arrays.asList(100l,101l));
        List<RuleIndex> ruleIndexList = Arrays.asList(ruleIndex2, ruleIndex1);

        Set<Long> longSet = new HashSet<>();
        for (RuleIndex ruleIndex : ruleIndexList) {
            longSet.addAll((List)ruleIndex.getAreaId());
        }

//        ruleIndexList.parallelStream().map(ruleIndex -> ruleIndex.getAreaId()).filter();


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
