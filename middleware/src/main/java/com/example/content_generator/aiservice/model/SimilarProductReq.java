package com.example.content_generator.aiservice.model;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class SimilarProductReq {
    @NotNull
    private List<Float> queryEmbeddings;

    public @NotNull List<Float> getQueryEmbeddings() {
        return queryEmbeddings;
    }

    public void setQueryEmbeddings(@NotNull List<Float> queryEmbeddings) {
        this.queryEmbeddings = queryEmbeddings;
    }

    public SimilarProductReq(@NotNull List<Float> queryEmbeddings) {
        this.queryEmbeddings = queryEmbeddings;
    }
}
