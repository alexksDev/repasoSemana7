package com.alexdev.week077;

import org.springframework.boot.SpringApplication;

public class TestWeek077Application {

    public static void main(String[] args) {
        SpringApplication.from(Week077Application::main).with(TestcontainersConfiguration.class).run(args);
    }

}
