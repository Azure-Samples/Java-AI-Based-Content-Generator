package com.example.content_generator.dataservice.service;

import com.example.content_generator.dataservice.adapter.ProductEmbeddingAdapter;
import com.example.content_generator.dataservice.model.EmbeddingReq;
import com.example.content_generator.dataservice.model.Product;
import com.example.content_generator.dataservice.model.ProductEmbedding;
import com.example.content_generator.dataservice.repository.ProductEmbeddingRepository;
import com.example.content_generator.dataservice.util.KeyVaultConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductEmbeddingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductEmbeddingService.class);

    private final ProductEmbeddingRepository productEmbeddingRepository;
    private final KeyVaultService keyVaultService;
    private final ProductEmbeddingAdapter productEmbeddingAdapter;

    public ProductEmbeddingService(ProductEmbeddingRepository productEmbeddingRepository, KeyVaultService keyVaultService, ProductEmbeddingAdapter productEmbeddingAdapter) {
        this.productEmbeddingRepository = productEmbeddingRepository;
        this.keyVaultService = keyVaultService;
        this.productEmbeddingAdapter = productEmbeddingAdapter;
    }

    public List<ProductEmbedding> getAllProductEmbeddings() {
        return productEmbeddingRepository.findAll();
    }

    public Optional<ProductEmbedding> getProductEmbeddingById(String productId) {
        Optional<ProductEmbedding> productEmbeddingOpt = productEmbeddingRepository.findByProductId(productId);
        return productEmbeddingOpt.filter(productEmbedding -> productEmbedding.getProductId().equals(productId));

    }

    public Optional<ProductEmbedding> getProductEmbeddingByProductId(String id) {
        return productEmbeddingRepository.findById(id);
    }

    public ProductEmbedding saveProductEmbedding(Product product) {
        ProductEmbedding productEmbedding = new ProductEmbedding(product.getId(), fetchProductEmbeddings(product));
        return productEmbeddingRepository.save(productEmbedding);  // UUID is generated in Product constructor
    }

    public ProductEmbedding updateProductEmbedding(String id, ProductEmbedding productEmbedding) {
        Optional<ProductEmbedding> existingProduct = productEmbeddingRepository.findById(id);
        if (existingProduct.isPresent()) {
            ProductEmbedding updatedProduct = existingProduct.get();
            updatedProduct.setProductId(productEmbedding.getProductId());
            updatedProduct.setEmbedding(productEmbedding.getEmbedding());
            return productEmbeddingRepository.save(updatedProduct);
        } else {
            return null;
        }
    }

    public void deleteProductEmbeddingById(String id) {
        productEmbeddingRepository.deleteById(id);
    }

    public List<String> searchSimilarProductEmbeddings(Float[] queryEmbedding) {

        // Fetch all product embeddings
        List<ProductEmbedding> allEmbeddings = productEmbeddingRepository.findAll();

        // Calculate cosine similarity and filter based on score > 0.85
        List<ProductEmbedding> similarProductEmbeddings = allEmbeddings.stream()
                .filter(embedding -> cosineSimilarity(queryEmbedding, embedding.getEmbedding()) > 0.85)
                .limit(5)  // Limit to top 5 results
                .toList();

        return similarProductEmbeddings.stream().map(ProductEmbedding::getProductId).toList();
    }

    private double cosineSimilarity(Float[] vectorA, List<Float> vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB.get(i);
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB.get(i), 2);
        }

        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    public List<Float> fetchProductEmbeddings(Product product) {
        String productEmbeddingEndpoint = "/api/v1/generate/embeddings";

        try {
            // Retrieve product embedding endpoint from Key Vault
            productEmbeddingEndpoint = keyVaultService.getSecretValue(KeyVaultConstants.MIDDLEWARE_SERVICE_PRODUCT_EMBEDDING_ENDPOINT);
        } catch (Exception e) {
            LOGGER.error("Failed to retrieve product embedding endpoint from Key Vault: {}", e.getMessage(), e);
            // Proceed with the default product endpoint (/api/v1/generate/embeddings)
            LOGGER.info("Using default product embedding endpoint : {}", productEmbeddingEndpoint);
        }
        return productEmbeddingAdapter.getProductEmbeddings(
                productEmbeddingEndpoint,
                new EmbeddingReq(product)).block();
    }
}