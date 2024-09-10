package com.example.content_generator.aiservice.service;

import jakarta.validation.constraints.NotNull;

/**
 * This interface defines methods for interacting with a key vault service to retrieve secrets.
 */

public interface KeyVaultService {
    /**
     * Retrieves the value of a secret from the key vault.
     *
     * @param secretName the name of the secret to retrieve; must not be {@code null}
     * @return the value of the secret as a {@link String}
     * @throws IllegalArgumentException if {@code secretName} is {@code null}
     */
    String getSecretValue(@NotNull String secretName);
}
