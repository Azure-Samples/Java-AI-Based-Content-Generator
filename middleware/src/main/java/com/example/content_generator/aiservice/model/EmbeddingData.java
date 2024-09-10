package com.example.content_generator.aiservice.model;

import java.util.List;

public class EmbeddingData {
    private String object;
    private int index;
    private List<Float> embedding;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<Float> getEmbedding() {
        return embedding;
    }

    public void setEmbedding(List<Float> embedding) {
        this.embedding = embedding;
    }
}
