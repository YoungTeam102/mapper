package com.igniubi.mapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.igniubi")
@ComponentScan(basePackages = "com.igniubi")
@EnableAutoConfiguration
public class MapperApplication {
    public static void main(String[] args) {
        SpringApplication.run(MapperApplication.class, args);
    }
}
