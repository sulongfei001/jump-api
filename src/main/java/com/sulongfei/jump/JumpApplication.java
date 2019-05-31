package com.sulongfei.jump;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/22 11:59
 * @Version 1.0
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan({"com.sulongfei.jump.mapper"})
public class JumpApplication {
    public static void main(String[] args) {
        SpringApplication.run(JumpApplication.class, args);
    }
}
