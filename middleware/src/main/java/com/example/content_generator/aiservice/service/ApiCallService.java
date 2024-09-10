package com.example.content_generator.aiservice.service;

import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * This interface defines methods for making API calls.
 *
 * @param <T> the type of the elements in the response list
 */

public interface ApiCallService {
    /**
     * Sends a GET request to the specified endpoint with query parameters.
     *
     * @param endpoint the API endpoint to which the GET request is sent
     * @param queryParams a map of query parameters to include in the request
     * @return a {@link Mono} containing a list of responses of type {@code T}
     */
    <T> Mono<List<T>> get(String endpoint, Map<String, String> queryParams);
    <T> Mono<List<T>> post(String endpoint, Object body);
}
