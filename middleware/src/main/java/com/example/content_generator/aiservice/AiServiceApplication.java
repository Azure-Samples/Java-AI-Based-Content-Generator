package com.example.content_generator.aiservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AiServiceApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(AiServiceApplication.class);

    public static void main(String[] args) {
        try {
            SpringApplication.run(AiServiceApplication.class, args);
        } catch (Exception e) {
            LOGGER.error("Application failed to start : {}", e.getMessage());
        }
    }
}
