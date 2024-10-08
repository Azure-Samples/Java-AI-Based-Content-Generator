package com.example.content_generator.dataservice.model;

import org.springframework.web.multipart.MultipartFile;

public class BrochureUploadRequest {
    private String productId;
    private MultipartFile file;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
