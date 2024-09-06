package com.example.content_generator.dataservice.service;


import com.example.content_generator.dataservice.model.Product;
import com.example.content_generator.dataservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);  // UUID is generated in Product constructor
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