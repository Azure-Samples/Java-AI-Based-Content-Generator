package com.example.content_generator.aiservice.config;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.AzureCliCredentialBuilder;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeyVaultConfig {

    @Value("${azure.keyvault.uri}")
    private String vaultUrl;

    @Bean
    public SecretClient secretClient() {
        // Use an environment variable or some other method to determine the environment
        String environment = System.getenv("ENVIRONMENT");

        // Default to AzureCliCredential if the environment is null or LOCAL
        TokenCredential credential = (environment == null || "LOCAL".equalsIgnoreCase(environment))
                ? new AzureCliCredentialBuilder().build()
                : new DefaultAzureCredentialBuilder().build();

        return new SecretClientBuilder()
                .vaultUrl(vaultUrl)
                .credential(credential)
                .buildClient();
    }
}
