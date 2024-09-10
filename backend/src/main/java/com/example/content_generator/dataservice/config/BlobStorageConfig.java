package com.example.content_generator.dataservice.config;

import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.example.content_generator.dataservice.util.KeyVaultConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class BlobStorageConfig {
    @Bean
    @Primary
    public BlobServiceClient blobServiceClient(SecretClient secretClient) {
        String storageConnectionString = secretClient.getSecret(KeyVaultConstants.AZURE_STORAGE_CONNECTION_STRING).getValue();
        return new BlobServiceClientBuilder()
                .connectionString(storageConnectionString)
                .buildClient();
    }
}
