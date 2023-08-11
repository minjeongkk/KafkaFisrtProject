package com.example.firstProject.config;

import com.example.firstProject.service.Consumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResourceConfig {
    @Bean
    public Consumer consumer(){
        return new Consumer();
    }
}
