package com.sulongfei.jump.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import lombok.NonNull;
import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

public class MyRedisTemplate extends RedisTemplate<String, Serializable> {


    /**
     * Constructs a new <code>StringRedisTemplate</code> instance. {@link #setConnectionFactory(RedisConnectionFactory)}
     * and {@link #afterPropertiesSet()} still need to be called.
     */
    public MyRedisTemplate() {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        RedisSerializer genericFastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
        setKeySerializer(stringSerializer);
        setValueSerializer(genericFastJsonRedisSerializer);
        setHashKeySerializer(genericFastJsonRedisSerializer);
        setHashValueSerializer(genericFastJsonRedisSerializer);
    }

    /**
     * Constructs a new <code>StringRedisTemplate</code> instance ready to be used.
     *
     * @param connectionFactory connection factory for creating new connections
     */
    public MyRedisTemplate(RedisConnectionFactory connectionFactory) {
        this();
        setConnectionFactory(connectionFactory);
        afterPropertiesSet();
    }

    @NonNull
    protected RedisConnection preProcessConnection(@NonNull RedisConnection connection, boolean existingConnection) {
        return new DefaultStringRedisConnection(connection);
    }
}
