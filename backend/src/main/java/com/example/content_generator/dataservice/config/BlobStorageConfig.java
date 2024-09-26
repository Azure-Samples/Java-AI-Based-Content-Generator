package com.example.content_generator.dataservice.config;

import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.example.content_generator.dataservice.util.KeyVaultConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class BlobStorageConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlobStorageConfig.class);

    private final SecretClient secretClient;

    public BlobStorageConfig(SecretClient secretClient) {
        this.secretClient = secretClient;
    }

    @Bean
    @Primary
    public BlobContainerClient blobContainerClient() {
        String storageConnectionString = retrieveSecret(KeyVaultConstants.AZURE_STORAGE_CONNECTION_STRING, "Storage Connection String");
        String containerName = retrieveSecret(KeyVaultConstants.AZURE_STORAGE_CONTAINER_NAME, "brochure");

        BlobServiceClient blobServiceClient;
        try {
            blobServiceClient = new BlobServiceClientBuilder()
                    .connectionString(storageConnectionString)
                    .buildClient();
            LOGGER.info("Successfully created BlobServiceClient.");
        } catch (Exception e) {
            LOGGER.error("Failed to create BlobServiceClient with connection string: {}", storageConnectionString, e);
            throw new RuntimeException("Failed to create BlobServiceClient", e);
        }

        BlobContainerClient containerClient;
        try {
            containerClient = blobServiceClient.getBlobContainerClient(containerName);
            if (!containerClient.exists()) {
                LOGGER.info("Creating container: {}", containerName);
                containerClient.create();
                LOGGER.info("Container '{}' created.", containerName);
            } else {
                LOGGER.info("Container '{}' already exists.", containerName);
            }
        } catch (Exception e) {
            LOGGER.error("Failed to get or create container '{}': {}", containerName, e.getMessage(), e);
            throw new RuntimeException("Failed to get or create BlobContainerClient", e);
        }

        return containerClient;
    }

    private String retrieveSecret(String secretName, String defaultValue) {
        try {
            LOGGER.info("Retrieved secret for '{}'", secretName);
            return secretClient.getSecret(secretName).getValue();
        } catch (Exception e) {
            LOGGER.warn("Failed to retrieve secret '{}', using default value: {}", secretName, defaultValue, e);
            return defaultValue;
        }
    }
}
