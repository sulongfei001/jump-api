package com.sulongfei.jump.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
                .antMatchers("/test/**")
                .antMatchers("/*/*/*/user/**")
                .antMatchers("/*/*/*/room/**")
                .antMatchers("/*/*/*/goods/**")
                .antMatchers("/*/*/*/game/**")
                .antMatchers("/*/*/*/file/**")
                .antMatchers("/*/*/*/charge/**")
                .and()
                .authorizeRequests()
                .antMatchers("/test/**").authenticated()
                .antMatchers("/*/*/*/user/**").authenticated()
                .antMatchers("/*/*/*/room/**").authenticated()
                .antMatchers("/*/*/*/goods/**").authenticated()
                .antMatchers("/*/*/*/game/**").authenticated()
                .antMatchers("/*/*/*/file/**").authenticated()
                .antMatchers("/*/*/*/charge/**").authenticated()
        ;


    }
}
