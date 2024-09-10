package com.example.content_generator.aiservice.service.Implementation;

import com.example.content_generator.aiservice.adapter.ProductAdapter;
import com.example.content_generator.aiservice.model.Product;
import com.example.content_generator.aiservice.model.SimilarProductReq;
import com.example.content_generator.aiservice.service.KeyVaultService;
import com.example.content_generator.aiservice.service.ProductService;
import com.example.content_generator.aiservice.util.KeyVaultConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductAdapter productAdapter;
    private final KeyVaultService keyVaultService;

    public ProductServiceImpl(ProductAdapter productAdapter, KeyVaultService keyVaultService) {
        this.productAdapter = productAdapter;
        this.keyVaultService = keyVaultService;
    }

    public Mono<List<Product>> fetchProductDetails(Map<String, String> params) {
        String productEndpoint = "/api/v1/products";

        try {
            // Retrieve product endpoint from Key Vault
            productEndpoint = keyVaultService.getSecretValue(KeyVaultConstants.BACKEND_SERVICE_PRODUCT_ENDPOINT);
        } catch (Exception e) {
            LOGGER.error("Failed to retrieve product endpoint from Key Vault: {}", e.getMessage(), e);
            // Proceed with the default product endpoint (/api/v1/products)
            LOGGER.info("Using default product endpoint : {}", productEndpoint);
        }
        return productAdapter.getProductDetails(productEndpoint, params);
    }

    public Mono<List<Product>> fetchSimilarProductDetails(List<Float> queryEmbedding) {
        String similarProductsEndpoint = "/api/v1/products/similar";

        try {
            // Retrieve similar products endpoint from Key Vault
            similarProductsEndpoint = keyVaultService.getSecretValue(KeyVaultConstants.BACKEND_SERVICE_SIMILAR_PRODUCT_ENDPOINT);
        } catch (Exception e) {
            LOGGER.error("Failed to retrieve similar products endpoint from Key Vault: {}", e.getMessage(), e);
            // Proceed with the default product endpoint (/api/v1/products/similar)
            LOGGER.info("Using default similar products endpoint : {}", similarProductsEndpoint);
        }
        return productAdapter.getSimilarProductsDetails(similarProductsEndpoint, new SimilarProductReq(queryEmbedding));
    }
}
