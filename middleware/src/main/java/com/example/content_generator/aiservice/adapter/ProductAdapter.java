package com.example.content_generator.aiservice.adapter;

import com.example.content_generator.aiservice.model.Product;
import com.example.content_generator.aiservice.service.ApiCallService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
public class ProductAdapter {

    private final ApiCallService apiCallService;

    public ProductAdapter(ApiCallService apiCallService) {
        this.apiCallService = apiCallService;
    }

    public Mono<List<Product>> getProductDetails(String endpoint, Map<String, String> params) {
        return apiCallService.get(endpoint, params);
    }

    public Mono<List<Product>> getSimilarProductsDetails(String endpoint, Object body) {
        return apiCallService.post(endpoint, body);
    }
}
