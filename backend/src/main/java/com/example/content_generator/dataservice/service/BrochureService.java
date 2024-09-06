package com.example.content_generator.dataservice.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class BrochureService {

    private static Logger LOGGER = LoggerFactory.getLogger(BrochureService.class);

    @Value("${azure.storage.container-name}")
    private String containerName;

    private final BlobServiceClient blobServiceClient;

    public BrochureService(BlobServiceClient blobServiceClient) {
        this.blobServiceClient = blobServiceClient;
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
