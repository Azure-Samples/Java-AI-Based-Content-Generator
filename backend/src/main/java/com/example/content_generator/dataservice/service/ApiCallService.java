package com.example.content_generator.dataservice.service;

import com.example.content_generator.dataservice.util.KeyVaultConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ApiCallService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiCallService.class);
    private final WebClient webClient;

    public ApiCallService(WebClient.Builder webClientBuilder, KeyVaultService keyVaultService) {
        String baseUrl = keyVaultService.getSecretValue(KeyVaultConstants.MIDDLEWARE_SERVICE_BASE_URL);
        String backendServiceAccessKey = keyVaultService.getSecretValue(KeyVaultConstants.MIDDLEWARE_SERVICE_ACCESS_KEY);

        LOGGER.info("Initializing ApiCallServiceImpl with baseUrl: {}", baseUrl);

        this.webClient = webClientBuilder
                .baseUrl(baseUrl)
                .defaultHeader("api-key", backendServiceAccessKey) // To authenticate the middleware service in order to access it.
                .filter((request, next) -> {
                    LOGGER.info("Request: {}", request.method().toString().toUpperCase() + " " + request.url());
                    return next.exchange(request);
                })
                .build();
    }

    public <T> Mono<List<T>> post(String endpoint, Object req) {
        return webClient.post()
                .uri(uriBuilder -> {
                    uriBuilder.path(endpoint);
                    return uriBuilder.build();
                })
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    LOGGER.error("4xx error: {}", clientResponse.statusCode());
                    return clientResponse.createException().flatMap(Mono::error);
                })
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                    LOGGER.error("5xx error: {}", clientResponse.statusCode());
                    return clientResponse.createException().flatMap(Mono::error);
                })
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }
}
