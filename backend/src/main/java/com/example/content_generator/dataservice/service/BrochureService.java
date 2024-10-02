package com.example.content_generator.dataservice.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.example.content_generator.dataservice.dto.BrochureDTO;
import com.example.content_generator.dataservice.model.Brochure;
import com.example.content_generator.dataservice.repository.BrochureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for managing brochures.
 */
@Service
public class BrochureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrochureService.class);
    private static final String FILE_UPLOAD_SUCCESS = "File uploaded successfully: {}";
    private static final String FILE_UPLOAD_ERROR = "Error uploading file: {}";
    private static final String FILE_DELETE_SUCCESS = "File deleted successfully: {}";
    private static final String FILE_DELETE_ERROR = "Error deleting file: {}";
    private static final String BROCHURE_NOT_FOUND = "Brochure with id {} not found.";
    private static final String BROCHURE_DELETE_SUCCESS = "Brochure with id {} deleted successfully.";
    private static final String BROCHURE_DELETE_ERROR = "Brochure with id {} not found for deletion.";

    private final BlobContainerClient blobContainerClient;
    private final BrochureRepository brochureRepository;

    /**
     * Constructs a new BrochureService.
     *
     * @param containerClient the BlobContainerClient
     * @param brochureRepository the BrochureRepository
     */
    @Autowired
    public BrochureService(BlobContainerClient containerClient, BrochureRepository brochureRepository) {
        this.blobContainerClient = containerClient;
        this.brochureRepository = brochureRepository;
    }

    /**
     * Uploads a file to the blob storage.
     *
     * @param file the file to upload
     * @return the URL of the uploaded file
     * @throws IOException if an I/O error occurs
     */
    public String uploadFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IOException("Cannot upload an empty file.");
        }

        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            BlobClient blobClient = blobContainerClient.getBlobClient(fileName);
            blobClient.upload(file.getInputStream(), file.getSize(), true);

            LOGGER.info(FILE_UPLOAD_SUCCESS, fileName);
            return blobClient.getBlobUrl();

        } catch (IOException e) {
            LOGGER.error(FILE_UPLOAD_ERROR, e.getMessage());
            throw new IOException("File upload failed: " + e.getMessage(), e);
        }
    }

    /**
     * Uploads a file and associates it with a product ID.
     *
     * @param productId the product ID
     * @param file the file to upload
     * @return the BrochureDTO of the saved brochure
     * @throws IOException if an I/O error occurs
     */
    public BrochureDTO uploadFileUsingProductId(String productId, MultipartFile file) throws IOException {
        String fileUrl = uploadFile(file);
        Brochure brochure = new Brochure(productId, fileUrl);
        Brochure savedBrochure = brochureRepository.save(brochure);
        return convertToDTO(savedBrochure);
    }

    /**
     * Uploads a file and associates it with an existing brochure ID.
     *
     * @param id the brochure ID
     * @param file the file to upload
     * @return the BrochureDTO of the updated brochure, or null if the brochure is not found
     * @throws IOException if an I/O error occurs
     */
    public BrochureDTO uploadFileUsingBrochureId(String id, MultipartFile file) throws IOException {
        Optional<Brochure> optionalBrochure = brochureRepository.findById(id);
        if (optionalBrochure.isEmpty()) {
            LOGGER.warn(BROCHURE_NOT_FOUND, id);
            return null;
        }

        Brochure brochure = optionalBrochure.get();
        String fileUrl = uploadFile(file);
        brochure.setUrl(fileUrl);
        Brochure savedBrochure = brochureRepository.save(brochure);
        return convertToDTO(savedBrochure);
    }

    /**
     * Deletes a file from the blob storage.
     *
     * @param fileName the name of the file to delete
     */
    public void deleteFile(String fileName) {
        try {
            BlobClient blobClient = blobContainerClient.getBlobClient(fileName);
            blobClient.delete();
            LOGGER.info(FILE_DELETE_SUCCESS, fileName);
        } catch (Exception e) {
            LOGGER.error(FILE_DELETE_ERROR, e.getMessage());
        }
    }

    /**
     * Retrieves all brochures associated with a product ID.
     *
     * @param productId the product ID
     * @return a list of BrochureDTOs
     */
    public List<BrochureDTO> getAllBrochuresUsingProductId(String productId) {
        List<Brochure> brochures = brochureRepository.findByProductId(productId);
        return brochures.stream()
                .map(b -> new BrochureDTO(b.getProductId(), b.getUrl()))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all brochures associated with a list of product IDs.
     *
     * @param productIds the list of product IDs
     * @return a list of BrochureDTOs
     */
    public List<BrochureDTO> getAllBrochuresUsingProductIds(List<String> productIds) {
        List<Brochure> brochures = brochureRepository.findByProductIds(productIds);
        return brochures.stream()
                .map(b -> new BrochureDTO(b.getProductId(), b.getUrl()))
                .collect(Collectors.toList());
    }

    /**
     * Deletes a brochure by its ID.
     *
     * @param id the brochure ID
     * @return true if the brochure was deleted, false otherwise
     */
    public Boolean deleteBrochure(String id) {
        Optional<Brochure> optionalBrochure = brochureRepository.findById(id);
        if (optionalBrochure.isEmpty()) {
            LOGGER.warn(BROCHURE_DELETE_ERROR, id);
            return false;
        }

        Brochure brochure = optionalBrochure.get();
        deleteFile(brochure.getUrl());
        brochureRepository.deleteById(id);
        LOGGER.info(BROCHURE_DELETE_SUCCESS, id);
        return true;
    }

    /**
     * Converts a Brochure entity to a BrochureDTO.
     *
     * @param brochure the Brochure entity
     * @return the BrochureDTO
     */
    private BrochureDTO convertToDTO(Brochure brochure) {
        return new BrochureDTO(brochure.getProductId(), brochure.getUrl());
    }
}