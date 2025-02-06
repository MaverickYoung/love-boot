package com.yuan.loveboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LoveBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoveBootApplication.class, args);
    }

}
