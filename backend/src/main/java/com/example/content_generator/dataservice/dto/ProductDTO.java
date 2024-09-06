package com.example.content_generator.dataservice.dto;

import com.example.content_generator.dataservice.model.Price;

import java.util.List;

public class ProductDTO {
    private String name;
    private String description;
    private String sku;
    private List<Price> prices;

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
