package com.example.content_generator.dataservice.dto;

public class BrochureDTO {
    private String productId;
    private String url;

    public BrochureDTO(String productId, String url) {
        this.productId = productId;
        this.url = url;
    }

    public BrochureDTO() {}

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
