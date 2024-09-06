package com.example.content_generator.dataservice.core;

import java.util.UUID;

public class Common {

    // Custom UUID v4 Generator Method
    public static String generateCustomUUID() {
        return UUID.randomUUID().toString();
    }
}
