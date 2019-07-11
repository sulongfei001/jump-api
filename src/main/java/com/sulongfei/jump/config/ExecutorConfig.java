package com.sulongfei.jump.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;


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
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(8);
        taskExecutor.setMaxPoolSize(16);
        taskExecutor.setQueueCapacity(16);
        taskExecutor.setKeepAliveSeconds(300);
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setThreadNamePrefix("taskExecutor-");
        return taskExecutor;
    }

    @Bean(name = "taskScheduler")
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(16);
        taskScheduler.setAwaitTerminationSeconds(300);
        taskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        taskScheduler.setThreadNamePrefix("taskScheduler-");
        return taskScheduler;
    }
}
