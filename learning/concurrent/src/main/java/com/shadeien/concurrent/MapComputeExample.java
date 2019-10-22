package com.shadeien.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class MapComputeExample {

    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        String[] keys = {"a", "b", "a", "d"};
        String[] values = {"a1", "b", "a2", "d"};
        List<String> keyLists = Arrays.asList(keys);
        List<String> valueLists = Arrays.asList(values);
        Iterator<String> iterator1 = keyLists.iterator();
        while (iterator1.hasNext()) {
            log.info(iterator1.next());
            Iterator<String> iterator2 = valueLists.iterator();
            while (iterator2.hasNext()) {
                String next = iterator2.next();
                log.info("iterator2:{}", next);
                if (next.equals("a2")) {
                    break;
                }
            }
        }

        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            String value = values[i];
            String oldValue = map.compute(key, (k, v) -> {
                if (null != v) {
                    v += "-" + value;
                } else {
                    v = value;
                }
                return v;
            });
            log.info("compute old value :{}", oldValue);
        }
        log.info("compute:{}", map);

        map.clear();
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            String value = values[i];
            String oldValue = map.computeIfPresent(key, (k, v) -> {
                if (null != v) {
                    v += "-" + value;
                } else {
                    v = value;
                }
                return v;
            });
            log.info("computeIfPresent old value :{}", oldValue);
        }
        log.info("computeIfPresent:{}", map);

        map.clear();
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            String value = values[i];
            String oldValue = map.computeIfAbsent(key, (k) -> value);
            log.info("computeIfAbsent old value :{}", oldValue);
        }
        log.info("computeIfAbsent:{}", map);
    }
}
