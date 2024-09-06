package com.example.content_generator.aiservice.service;

import com.example.content_generator.aiservice.config.BackendApiConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class ApiCallService {

    private final WebClient webClient;
    private final BackendApiConfig backendApiConfig;

    @Value("${backend.service.access_key}")
    private String backendServiceAccessKey;

    public ApiCallService(WebClient.Builder webClientBuilder, BackendApiConfig backendApiConfig) {
        this.webClient = webClientBuilder
                .baseUrl(backendApiConfig.getBaseUrl())
                .defaultHeader("api-key", backendServiceAccessKey) // To authenticate the backend service in order to access it.
                .build();
        this.backendApiConfig = backendApiConfig;
    }

    public <T> Mono<List<T>> get(String endpoint, Map<String, String> queryParams) {
        return webClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path(endpoint);
                    queryParams.forEach(uriBuilder::queryParam);
                    return uriBuilder.build();
                })
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }

    public BackendApiConfig getBackendApiConfig() {
        return backendApiConfig;
    }
}
