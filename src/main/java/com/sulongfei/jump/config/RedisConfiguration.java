package com.sulongfei.jump.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.sulongfei.jump.service.impl.RedisService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

@Configuration
public class RedisConfiguration {

    @Bean
    public RedisTemplate<String, Serializable> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Serializable> template  = new RedisTemplate<>();
        RedisSerializer stringSerializer = new StringRedisSerializer();
        RedisSerializer genericFastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(stringSerializer);
        template.setValueSerializer(genericFastJsonRedisSerializer);
        template.setHashKeySerializer(genericFastJsonRedisSerializer);
        template.setHashValueSerializer(genericFastJsonRedisSerializer);
        return template;
    }

    @Bean
    public RedisService redisService(RedisTemplate<String,Serializable> redisTemplate) {
        return new RedisService(redisTemplate);
    }
}
