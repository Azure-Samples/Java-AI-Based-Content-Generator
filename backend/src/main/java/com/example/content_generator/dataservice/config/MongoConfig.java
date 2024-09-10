package com.example.content_generator.dataservice.config;

import com.azure.security.keyvault.secrets.SecretClient;
import com.example.content_generator.dataservice.util.KeyVaultConstants;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.NonNull;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    private final SecretClient secretClient;

    public MongoConfig(SecretClient secretClient) {
        this.secretClient = secretClient;
    }

    @Override
    @NonNull
    public MongoClient mongoClient() {
        // Fetch MongoDB connection string from Azure Key Vault
        String connectionStringValue = secretClient.getSecret(KeyVaultConstants.AZURE_COSMOS_MONGODB_CONNECTION_STRING).getValue();

        if (connectionStringValue == null || connectionStringValue.isEmpty()) {
            throw new IllegalArgumentException("MongoDB connection string is not set in Azure Key Vault");
        }

        // Define the MongoDB connection string
        ConnectionString connectionString = new ConnectionString(connectionStringValue);

        // Configure the MongoClientSettings using a builder
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        // Build and return the MongoClient
        return MongoClients.create(mongoClientSettings);
    }

    @Override
    @NonNull
    protected String getDatabaseName() {
        // Fetch the database name from Azure Key Vault
        String databaseName = secretClient.getSecret(KeyVaultConstants.MONGODB_DATABASE_NAME).getValue();

        if (databaseName == null || databaseName.isEmpty()) {
            throw new IllegalArgumentException("MongoDB database name is not set in Azure Key Vault");
        }

        return databaseName;  // Return the database name
    }

    @Bean
    @Primary
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), getDatabaseName());
    }
}

