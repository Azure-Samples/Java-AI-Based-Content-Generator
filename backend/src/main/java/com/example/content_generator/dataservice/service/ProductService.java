package com.example.content_generator.dataservice.service;

import com.example.content_generator.dataservice.model.Product;
import com.example.content_generator.dataservice.model.ProductEmbedding;
import com.example.content_generator.dataservice.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final ProductEmbeddingService productEmbeddingService;

    public ProductService(ProductRepository productRepository, ProductEmbeddingService productEmbeddingService) {
        this.productRepository = productRepository;
        this.productEmbeddingService = productEmbeddingService;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> searchSimilarProducts(Float[] embeddings) {
        return getProductsByIds(productEmbeddingService.searchSimilarProductEmbeddings(embeddings));
    }

    public List<Product> getProductsByIds(List<String> similarProductIds) {
        // This will return products where the productId is in the provided list of similarProductIds
        return productRepository.findByIdIn(similarProductIds);
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        try {
            // Save product embeddings into database
            ProductEmbedding productEmbedding = productEmbeddingService.saveProductEmbedding(product);
            LOGGER.info("Product Embedding Created : {}", productEmbedding.getId() );
            return productRepository.save(product);  // UUID is generated in Product constructor
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Product updateProduct(String id, Product product) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product updatedProduct = existingProduct.get();
            updatedProduct.setName(product.getName());
            updatedProduct.setDescription(product.getDescription());
            updatedProduct.setPrices(product.getPrices());
            updatedProduct.setSku(product.getSku());
            return productRepository.save(updatedProduct);
        } else {
            return null;
        }
    }

    public void deleteProductById(String id) {
        productRepository.deleteById(id);
    }
}