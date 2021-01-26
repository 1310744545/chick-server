package com.xkx.chick;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author 肖可欣
 * @Create 2021-01-20 21:18
 */
@SpringBootApplication
@MapperScan("com.xkx.chick.*.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
