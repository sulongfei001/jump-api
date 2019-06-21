package com.sulongfei.jump.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


/**
 * @Description
 * @Author sulongfei
 * @Date 2018/10/23 16:45
 * @Version 1.0
 */
@Configuration
public class ExecutorConfig {
    @Bean(name = "taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();
        poolTaskExecutor.setCorePoolSize(8);
        poolTaskExecutor.setMaxPoolSize(16);
        poolTaskExecutor.setQueueCapacity(16);
        poolTaskExecutor.setKeepAliveSeconds(300);
        poolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        return poolTaskExecutor;
    }
}
