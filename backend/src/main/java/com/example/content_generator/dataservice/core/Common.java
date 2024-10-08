package com.example.content_generator.dataservice.core;

import java.util.UUID;

public class Common {

    private static final String BLOB_RESOURCE_PATTERN = "azure-blob://%s/%s";

    // Custom UUID v4 Generator Method
    public static String generateCustomUUID() {
        return UUID.randomUUID().toString();
    }

    // Get Blob Pattern Method
    public static String getBlobPattern(String containerName, String blobName) {
        return String.format(BLOB_RESOURCE_PATTERN, containerName, blobName);
    }
}
