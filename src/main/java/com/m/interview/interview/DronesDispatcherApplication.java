package com.m.interview.interview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DronesDispatcherApplication {
    public static void main(String[] args) {
        SpringApplication.run(DronesDispatcherApplication.class, args);
    }
}