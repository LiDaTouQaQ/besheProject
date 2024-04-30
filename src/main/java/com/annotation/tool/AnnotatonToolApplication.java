package com.annotation.tool;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.annotation.tool.mapper")
public class AnnotatonToolApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnnotatonToolApplication.class, args);
    }
}
