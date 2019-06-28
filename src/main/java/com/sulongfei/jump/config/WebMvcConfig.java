package com.sulongfei.jump.config;

import com.sulongfei.jump.web.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Description
 * @Author sulongfei
 * @Date 2018/12/24 11:44
 * @Version 1.0
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private HandlerInterceptor userInterceptor;

    @Bean
    public HandlerInterceptor userInterceptor() {
        return new UserInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/test/**")
                .excludePathPatterns("/error/**")
                .excludePathPatterns("/swagger-resources/**") // 过滤swagger页面权限控制
                .excludePathPatterns("/*/*/*/login/**")
                .excludePathPatterns("/order/**")
        ;
        super.addInterceptors(registry);
    }
}
