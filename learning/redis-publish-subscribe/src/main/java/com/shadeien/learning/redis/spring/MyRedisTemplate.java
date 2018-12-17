package com.shadeien.learning.redis.spring;

import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

public class MyRedisTemplate extends RedisTemplate<String, Object> {

    public MyRedisTemplate() {
        setKeySerializer(RedisSerializer.string());
        setHashKeySerializer(RedisSerializer.string());

        setValueSerializer(RedisSerializer.json());
        setHashValueSerializer(RedisSerializer.json());
    }

    public MyRedisTemplate(RedisConnectionFactory connectionFactory) {
        this();
        setConnectionFactory(connectionFactory);
        afterPropertiesSet();
    }

    protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
        return new DefaultStringRedisConnection(connection);
    }
}
