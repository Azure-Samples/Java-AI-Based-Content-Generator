package com.example.content_generator.aiservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BackendApiConfig {

    @Value("${backend.service.base_url}")
    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }

}
