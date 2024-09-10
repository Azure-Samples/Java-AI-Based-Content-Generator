package com.example.content_generator.dataservice.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.example.content_generator.dataservice.util.KeyVaultConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class BrochureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrochureService.class);

    private final BlobServiceClient blobServiceClient;
    private String containerName;

    public BrochureService(BlobServiceClient blobServiceClient, KeyVaultService keyVaultService) {
        this.blobServiceClient = blobServiceClient;

        String containerName = "brochure";

        try {
            // Retrieve container name from Key Vault
            containerName = keyVaultService.getSecretValue(KeyVaultConstants.AZURE_STORAGE_CONTAINER_NAME);
        } catch (Exception e) {
            LOGGER.error("Failed to retrieve container name from Key Vault: {}", e.getMessage(), e);
            // Proceed with the default container name brochure
            LOGGER.info("Using default container name: {}", containerName);
        }
    }

    public String uploadFile(MultipartFile file) throws IOException {
        try {

            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
            if (!containerClient.exists()) {
                containerClient.create();
            }

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            BlobClient blobClient = containerClient.getBlobClient(fileName);
            blobClient.upload(file.getInputStream(), file.getSize(), true);

            return blobClient.getBlobUrl();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return null;
        }

    }

    public void deleteFile(String fileName) {
        try {
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
            BlobClient blobClient = containerClient.getBlobClient(fileName);
            blobClient.delete();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
