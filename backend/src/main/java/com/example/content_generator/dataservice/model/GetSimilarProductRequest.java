package com.example.content_generator.dataservice.model;

import jakarta.validation.constraints.NotNull;

public class GetSimilarProductRequest {
    @NotNull
    private Float[] queryEmbeddings;

    public @NotNull Float[] getQueryEmbeddings() {
        return queryEmbeddings;
    }

    public void setQueryEmbeddings(@NotNull Float[] queryEmbeddings) {
        this.queryEmbeddings = queryEmbeddings;
    }
}
