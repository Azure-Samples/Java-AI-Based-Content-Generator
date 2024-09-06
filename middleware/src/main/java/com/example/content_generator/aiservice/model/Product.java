package com.example.content_generator.aiservice.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

import static com.example.content_generator.aiservice.core.Common.generateCustomUUID;

public class Product {

    private String id;

    @NotBlank(message = "Product name cannot be blank")
    private String name;

    @NotBlank(message = "Product description cannot be blank")
    private String description;

    @NotNull(message = "SKU cannot be null")
    @NotBlank
    private String sku;

    @NotNull(message = "Prices cannot be null")
    @Valid // Validate each Price object in the list
    private List<Price> prices;

    // Constructors
    public Product() {
        this.id = generateCustomUUID();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
