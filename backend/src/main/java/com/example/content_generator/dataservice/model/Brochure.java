package com.example.content_generator.dataservice.model;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

import static com.example.content_generator.dataservice.core.Common.generateCustomUUID;

@Document(collection = "Brochures")
public class Brochure {

    @Id
    private String id;

    @NotNull(message = "ProductId cannot be null")
    private String productId;

    @NotNull(message = "URL cannot be null")
    private String url;

    // Constructors
    public Brochure() {
        this.id = generateCustomUUID();
    }

    public Brochure( String productId, String url ) {
        this.id = generateCustomUUID();
        this.productId = productId;
        this.url = url;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Brochure data = (Brochure) obj;
        return Objects.equals(id, data.id) &&
                Objects.equals(productId, data.productId) &&
                Objects.equals(url, data.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, url);
    }

    @Override
    public String toString() {
        return "Brochure{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(@NotNull(message = "ProductId cannot be null") String productId) {
        this.productId = productId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(@NotNull(message = "URL cannot be null") String url) {
        this.url = url;
    }
}
