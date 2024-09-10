package com.example.content_generator.aiservice.model;

import jakarta.validation.constraints.NotNull;

public class EmbeddingReq {
    @NotNull
    private Product input;

    public Product getInput() {
        return input;
    }

    public void setInput(@NotNull Product input) {
        this.input = input;
    }
}
