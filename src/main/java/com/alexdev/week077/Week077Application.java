package com.alexdev.week077;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Week077Application {

    public static void main(String[] args) {
        SpringApplication.run(Week077Application.class, args);
    }

}
