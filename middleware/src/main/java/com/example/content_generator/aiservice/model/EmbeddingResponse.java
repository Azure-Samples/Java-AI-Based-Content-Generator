package com.example.content_generator.aiservice.model;

import java.util.List;

public class EmbeddingResponse {
    private String object;
    private List<EmbeddingData> data;
    private String model;
    private Usage usage;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<EmbeddingData> getData() {
        return data;
    }

    public void setData(List<EmbeddingData> data) {
        this.data = data;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Usage getUsage() {
        return usage;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }
}

