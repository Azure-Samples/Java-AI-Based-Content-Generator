package com.example.content_generator.dataservice.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class BrochureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrochureService.class);
    private final BlobContainerClient blobContainerClient;

    public BrochureService(BlobContainerClient containerClient) {
        this.blobContainerClient = containerClient;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        try {
            // Create a unique blob name
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            BlobClient blobClient = blobContainerClient.getBlobClient(fileName);

            // Upload the file
            blobClient.upload(file.getInputStream(), file.getSize(), true);

            // Generate a SAS token valid for one month
            OffsetDateTime expiryTime = OffsetDateTime.now(ZoneOffset.UTC).plusMonths(1);
            BlobServiceSasSignatureValues sasValues = new BlobServiceSasSignatureValues(expiryTime, new BlobSasPermission().setReadPermission(true));
            String sasToken = blobClient.generateSas(sasValues);

            // Return the blob URL with SAS token
            return blobClient.getBlobUrl() + "?" + sasToken;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public void deleteFile(String fileName) {
        try {
            BlobClient blobClient = blobContainerClient.getBlobClient(fileName);
            blobClient.delete();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
