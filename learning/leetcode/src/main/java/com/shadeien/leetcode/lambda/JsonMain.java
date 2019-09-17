package com.shadeien.leetcode.lambda;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@Slf4j
@SpringBootApplication
public class JsonMain {
    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(JsonMain.class, args);
        User user = new User();
        user.setName("a");
        user.setAge(10);
        user.setSex(Sex.MALE);

        String str = JSON.toJSONString(user);
        String str2 = "{\"age\":10,\"name\":\"a\",\"sex\":\"\"}";
        log.info("{}", str);
        User users = JSON.parseObject(str2, User.class);
        log.info("fast JSON:{}", users);

        ObjectMapper objectMapper = context.getBean(ObjectMapper.class);
        User user1 = objectMapper.readValue(str2, User.class);
        log.info("jackson:{}", user1);

        context.close();

    }

    @Data
    public static class User {
        Sex sex;
        int age;
        String name;
    }

    public enum Sex {
        MALE("male", 0),
        FEMALE("female", 1),
        ;

        private String name;
        private int value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        Sex(String name, int value) {
            this.name = name;
            this.value = value;
        }
    }
}
