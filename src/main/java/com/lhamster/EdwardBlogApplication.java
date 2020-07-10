package com.lhamster;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = "com.lhamster.mapper")
@SpringBootApplication
public class EdwardBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(EdwardBlogApplication.class, args);
    }

}
