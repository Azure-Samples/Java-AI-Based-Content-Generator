package com.example.content_generator.aiservice.service;

import com.example.content_generator.aiservice.model.Product;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * This interface defines methods for interacting with product-related services.
 */

public interface ProductService {

    /**
     * Fetches details of products based on the provided parameters.
     *
     * @param params a map of parameters to filter and fetch product details
     * @return a {@link Mono} containing a list of {@link Product} objects representing the fetched product details
     */
    Mono<List<Product>> fetchProductDetails(Map<String, String> params);
    Mono<List<Product>> fetchSimilarProductDetails(List<Float> queryEmbedding);
}
