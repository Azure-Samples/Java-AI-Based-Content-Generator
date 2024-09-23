package com.example.content_generator.dataservice.adapter;

import com.example.content_generator.dataservice.service.ApiCallService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class ProductEmbeddingAdapter {

    private final ApiCallService apiCallService;

    public ProductEmbeddingAdapter(ApiCallService apiCallService) {
        this.apiCallService = apiCallService;
    }

    public Mono<List<Float>> getProductEmbeddings(String endpoint, Object req) {
        return apiCallService.post(endpoint, req);
    }
}
