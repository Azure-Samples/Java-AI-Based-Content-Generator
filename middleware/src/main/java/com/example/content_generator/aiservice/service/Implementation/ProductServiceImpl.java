package com.example.content_generator.aiservice.service.Implementation;

import com.example.content_generator.aiservice.adapter.ProductAdapter;
import com.example.content_generator.aiservice.model.Product;
import com.example.content_generator.aiservice.model.SimilarProductReq;
import com.example.content_generator.aiservice.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductAdapter productAdapter;

    @Value("${BackendServiceSimilarProductEndpoint}")
    private String similarProductsEndpoint;

    @Value("${BackendServiceProductEndpoint}")
    private String productEndpoint;

    public ProductServiceImpl(ProductAdapter productAdapter) {
        this.productAdapter = productAdapter;
    }

    public Mono<List<Product>> fetchProductDetails(Map<String, String> params) {
        return productAdapter.getProductDetails(productEndpoint, params);
    }

    public Mono<List<Product>> fetchSimilarProductDetails(List<Float> queryEmbedding) {
        return productAdapter.getSimilarProductsDetails(similarProductsEndpoint, new SimilarProductReq(queryEmbedding));
    }
}
