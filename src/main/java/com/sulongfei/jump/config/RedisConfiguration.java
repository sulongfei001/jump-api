package com.sulongfei.jump.config;

import com.sulongfei.jump.service.RedisService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
public class RedisConfiguration {


    @Bean
    public MyRedisTemplate shopRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        MyRedisTemplate template = new MyRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    public RedisService redisService(MyRedisTemplate redisTemplate) {
        return new RedisService(redisTemplate);
    }
}
