package com.example.content_generator.aiservice.service.Implementation;

import com.example.content_generator.aiservice.core.NoResourceFoundException;
import com.example.content_generator.aiservice.model.ContentRequest;
import com.example.content_generator.aiservice.model.EmbeddingResponse;
import com.example.content_generator.aiservice.model.Product;
import com.example.content_generator.aiservice.service.KeyVaultService;
import com.example.content_generator.aiservice.service.OpenAiService;
import com.example.content_generator.aiservice.service.ProductService;
import com.example.content_generator.aiservice.util.KeyVaultConstants;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class OpenAiServiceImpl implements OpenAiService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpenAiServiceImpl.class);
    private final ProductService productService;
    private final KeyVaultService keyVaultService;

    private final WebClient webClient;

    public OpenAiServiceImpl(ProductService productService, KeyVaultService keyVaultService, WebClient.Builder webClientBuilder) {
        this.productService = productService;
        this.keyVaultService = keyVaultService;

        this.webClient = webClientBuilder
                .filter((request, next) -> {
                    LOGGER.info("Request: {}", request.method().toString().toUpperCase() + " " + request.url());
                    return next.exchange(request);
                })
                .build();
    }

    public String generateContent(@NotNull ContentRequest request) {
        String finalPromptMessage = request.getMessage().
                concat(getTemplatePrompt(request.getType()))
                .concat(getProductPrompt(request.getMessage()))
                .concat(getPricePrompt())
                .replaceAll("\n", "");

        try {
            String openAIKey = keyVaultService.getSecretValue(KeyVaultConstants.AZURE_OPENAI_KEY);
            String endpointUrl = keyVaultService.getSecretValue(KeyVaultConstants.AZURE_OPENAI_ENDPOINT_URL);

            // Set up the headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("api-key", openAIKey);
            headers.set("Content-Type", "application/json");

            // Make the API call
            Mono<ResponseEntity<String>> responseMono = webClient.post()
                    .uri(endpointUrl)
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .bodyValue(getRequestData(finalPromptMessage))
                    .retrieve()
                    .toEntity(String.class);

            // Block to get the response synchronously (or handle asynchronously as needed)
            ResponseEntity<String> responseEntity = responseMono.block();

            // Return the response body
            return responseEntity != null ? responseEntity.getBody() : null;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getTemplatePrompt(String type) {
        return switch (type.toLowerCase()) {
            case "mail_template" ->
                    ", I need an HTML email template and a subject line for a promotional campaign. The background color should be white, default font color is black inside the container. The template should have a banner with the product title, a product image with a royal golden and green edge and a glowing effect (the image should be converted to base64 and included in the template), as well as campaign content. Please only respond if you can directly address this request. The deals and offers should be prominently displayed, and the email content should be easy to read, with important words emphasized. Remove body css style in html. Also, the email should be concise and take no longer than 5 minutes to read.";
            case "social_media" ->
                    ", I need the social media content includes campaign materials with emojis and product images featuring the main features. The promotions and offers should be prominently displayed, and the content should be easy to read with important words highlighted. The content should not take longer than 5 minutes to read";
            default ->
                    ", I need a blog or article that includes campaign details and product images showcasing the main features. The promotions and offers should be prominently highlighted, and the content should be easy to read with important words emphasized. The content should not take longer than 10 to 15 minutes to read.";
        };
    }

    public String getProductPrompt(String message) {
        List<Float> messageEmbedding = generateEmbeddings(message);
        // Get Product Details
        try{
            List<Product> productList = productService.fetchSimilarProductDetails(messageEmbedding).block();
            if(productList == null || productList.isEmpty()) {
                throw new NoResourceFoundException("No resources found: Product list is empty.");
            }
            return String.format(". Here is product list with details. %s", productList);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return "";
        }
    }

    public String getPricePrompt() {
        return "The price should only be displayed in USD.";
    }

    public String getRequestData(String message) {
        return String.format("""
                {
                  "messages": [
                    {
                      "role": "user",
                      "content": [
                        {
                          "type": "text",
                          "text": "%s"
                        }
                      ]
                    }
                  ],
                  "temperature": 0.7,
                  "max_tokens": 4096
                }""", message);
    }

    public List<Float> generateEmbeddings(String inputData) {
        String embeddingUrl = keyVaultService.getSecretValue(KeyVaultConstants.AZURE_OPENAI_EMBEDDING_ENDPOINT_URL);

        WebClient client = WebClient.create();

        Mono<List<Float>> responseMono = client.post()
                .uri(embeddingUrl)
                .header("Content-Type", "application/json")
                .header("api-key", keyVaultService.getSecretValue(KeyVaultConstants.AZURE_OPENAI_EMBEDDING_KEY))
                .bodyValue(String.format("""
                        {
                            "input": "%s"
                        }
                        """,inputData))
                .retrieve()
                .bodyToMono(EmbeddingResponse.class)
                .map(embeddingResponse -> embeddingResponse.getData().get(0).getEmbedding());

        // Return the embedding list
        return responseMono.block();
    }
}
