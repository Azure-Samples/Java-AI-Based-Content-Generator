package com.example.content_generator.dataservice.service;

import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class KeyVaultService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeyVaultService.class);
    private final SecretClient secretClient;

    public KeyVaultService(SecretClient secretClient) {
        this.secretClient = secretClient;
    }

    public String getSecretValue(String secretName) {
        try {
            KeyVaultSecret secret = secretClient.getSecret(secretName);
            return secret.getValue();
        } catch (Exception e) {
            LOGGER.error("Error fetching secret from Key Vault: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
