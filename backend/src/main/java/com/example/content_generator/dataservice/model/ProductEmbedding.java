package com.example.content_generator.dataservice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

import static com.example.content_generator.dataservice.core.Common.generateCustomUUID;


@Document(collection = "ProductEmbedding")
public class ProductEmbedding {

    @Id
    private String id;

    @NotNull
    private String productId;

    @NotNull
    private List<Float> embedding;

    // Constructors
    public ProductEmbedding() {
        this.id = generateCustomUUID();
    }

    public ProductEmbedding(String productId, List<Float> embedding) {
        this.id = generateCustomUUID();
        this.productId = productId;
        this.embedding = embedding;
    }

    public String getId() {
        return id;
    }

    public void setId(@NotNull @NotBlank String id) {
        this.id = id;
    }

    public @NotNull String getProductId() {
        return productId;
    }

    public void setProductId(@NotNull String productId) {
        this.productId = productId;
    }

    public @NotNull List<Float> getEmbedding() {
        return embedding;
    }

    public void setEmbedding(@NotNull List<Float> embedding) {
        this.embedding = embedding;
    }
}
