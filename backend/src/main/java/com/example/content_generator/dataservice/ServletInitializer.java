package com.example.content_generator.dataservice;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServletInitializer extends SpringBootServletInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServletInitializer.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        try {
            return application.sources(DataServiceApplication.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
