package com.alexdev.week077.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
