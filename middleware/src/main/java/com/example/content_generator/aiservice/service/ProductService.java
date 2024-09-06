package com.example.content_generator.aiservice.service;

import com.example.content_generator.aiservice.adapter.ProductAdapter;
import com.example.content_generator.aiservice.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    @Value("${backend.service.product.endpoint}")
    private String productEndpoint;

    private final ProductAdapter productAdapter;

    public ProductService(ProductAdapter productAdapter) {
        this.productAdapter = productAdapter;
    }

    public Mono<List<Product>> fetchProductDetails(Map<String, String> params) {
        return productAdapter.getProductDetails(productEndpoint, params);
    }
}
