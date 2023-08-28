package com.example.firstProject.config;

import com.example.firstProject.kafka.Consumer;
import com.example.firstProject.spark.SparkStreaming;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResourceConfig {
    @Bean
    public Consumer consumer() {
        return new Consumer();
    }

    @Bean
    public SparkStreaming sparkStreaming() {
        return new SparkStreaming();
    }
}
