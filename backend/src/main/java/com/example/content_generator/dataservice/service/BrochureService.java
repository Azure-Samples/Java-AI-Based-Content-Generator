package com.example.content_generator.dataservice.service;

import com.example.content_generator.dataservice.dto.BrochureDTO;
import com.example.content_generator.dataservice.model.Brochure;
import com.example.content_generator.dataservice.repository.BrochureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.content_generator.dataservice.core.Common.getBlobPattern;

/**
 * Service class for managing brochures.
 * Provides methods for uploading, retrieving, and deleting brochures
 * using Azure Blob Storage.
 */
@Service
public class BrochureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrochureService.class);

    // Log messages
    private static final String FILE_UPLOAD_SUCCESS = "File uploaded successfully: {}";
    private static final String FILE_UPLOAD_ERROR = "Error uploading file: {}";
    private static final String FILE_DELETE_SUCCESS = "File deleted successfully: {}";
    private static final String FILE_DELETE_ERROR = "Error deleting file: {}";
    private static final String BROCHURE_NOT_FOUND = "Brochure with ID {} not found.";
    private static final String BROCHURE_DELETE_SUCCESS = "Brochure with ID {} deleted successfully.";
    private static final String BROCHURE_DELETE_ERROR = "Brochure with ID {} not found for deletion.";

    private final BrochureRepository brochureRepository;
    private final String containerName;
    private final ResourceLoader resourceLoader;

    /**
     * Constructs a new BrochureService.
     *
     * @param brochureRepository the BrochureRepository to interact with the database
     * @param containerName      the Azure Blob container name
     * @param resourceLoader     the resource loader for accessing Azure Blob storage
     */
    @Autowired
    public BrochureService(BrochureRepository brochureRepository,
                           @Value("${spring.cloud.azure.storage.blob.container-name}") String containerName,
                           ResourceLoader resourceLoader) {
        this.brochureRepository = brochureRepository;
        this.containerName = containerName;
        this.resourceLoader = resourceLoader;
    }

    /**
     * Uploads a file to Azure Blob Storage and returns its URL.
     *
     * @param file the file to upload
     * @return the URL of the uploaded file
     * @throws IOException if an I/O error occurs during file upload
     */
    public String uploadFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IOException("Cannot upload an empty file.");
        }

        String fileName = file.getOriginalFilename();
        String blobUrl = getBlobPattern(containerName, fileName);
        Resource resource = resourceLoader.getResource(blobUrl);

        try (OutputStream os = ((WritableResource) resource).getOutputStream()) {
            os.write(file.getBytes());
            LOGGER.info(FILE_UPLOAD_SUCCESS, fileName);
            return resource.getURL().toString();
        } catch (IOException e) {
            LOGGER.error(FILE_UPLOAD_ERROR, e.getMessage());
            throw new IOException("File upload failed: " + e.getMessage(), e);
        }
    }

    /**
     * Uploads a file and associates it with a product ID.
     *
     * @param productId the product ID to associate with the file
     * @param file      the file to upload
     * @return the BrochureDTO of the saved brochure
     * @throws IOException if an I/O error occurs during file upload
     */
    public BrochureDTO uploadFileUsingProductId(String productId, MultipartFile file) throws IOException {
        String fileUrl = uploadFile(file);
        Brochure brochure = new Brochure(productId, fileUrl);
        return convertToDTO(brochureRepository.save(brochure));
    }

    /**
     * Updates a brochure by uploading a new file and associating it with an existing brochure ID.
     *
     * @param id   the brochure ID to update
     * @param file the new file to upload
     * @return the BrochureDTO of the updated brochure, or null if not found
     * @throws IOException if an I/O error occurs during file upload
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
        return convertToDTO(brochureRepository.save(brochure));
    }

    /**
     * Deletes a file from Azure Blob Storage.
     *
     * @param fileName the name of the file to delete
     */
    public void deleteFile(String fileName) {
        try {
            Resource resource = resourceLoader.getResource(getBlobPattern(containerName, fileName));
            if (resource.exists() && resource.getFile().delete()) {
                LOGGER.info(FILE_DELETE_SUCCESS, fileName);
            } else {
                LOGGER.warn(FILE_DELETE_ERROR, fileName);
            }
        } catch (IOException e) {
            LOGGER.error(FILE_DELETE_ERROR, e.getMessage());
        }
    }

    /**
     * Retrieves all brochures associated with a product ID.
     *
     * @param productId the product ID to search for brochures
     * @return a list of BrochureDTOs
     */
    public List<BrochureDTO> getAllBrochuresUsingProductId(String productId) {
        return brochureRepository.findByProductId(productId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all brochures associated with a list of product IDs.
     *
     * @param productIds the list of product IDs to search for brochures
     * @return a list of BrochureDTOs
     */
    public List<BrochureDTO> getAllBrochuresUsingProductIds(List<String> productIds) {
        return brochureRepository.findByProductIds(productIds).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Deletes a brochure by its ID and removes the associated file from Azure Blob Storage.
     *
     * @param id the brochure ID to delete
     * @return true if the brochure was deleted, false otherwise
     */
    public boolean deleteBrochure(String id) {
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
