package com.example.content_generator.aiservice.service;

import com.example.content_generator.aiservice.model.ContentRequest;

import java.util.List;

/**
 * This interface defines methods for interacting with the OpenAI service.
 */

public interface OpenAiService {

    /**
     * Generates content based on the provided content request.
     *
     * @param request the content request that includes parameters for generating content
     * @return a {@link String} object containing the generated content
     */
    String generateContent(ContentRequest request);
    List<Float> generateEmbeddings(String request);
}
