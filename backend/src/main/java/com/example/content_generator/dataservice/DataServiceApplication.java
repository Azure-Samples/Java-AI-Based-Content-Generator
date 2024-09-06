package com.example.content_generator.dataservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class DataServiceApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataServiceApplication.class);

    public static void main(String[] args) {
        try {
            SpringApplication.run(DataServiceApplication.class, args);
        } catch (Exception e) {
            LOGGER.error("Application failed to start", e);
        }
    }

}
