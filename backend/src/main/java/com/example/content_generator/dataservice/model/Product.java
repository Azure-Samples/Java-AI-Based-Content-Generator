package com.example.content_generator.dataservice.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

import static com.example.content_generator.dataservice.core.Common.generateCustomUUID;


@Document(collection = "Products")
public class Product {

    @Id
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

    public void setId(@NotNull @NotBlank String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Product name cannot be blank") String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank(message = "Product description cannot be blank") String description) {
        this.description = description;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(
            @NotNull(message = "Prices cannot be null")
            @Valid List<Price> prices
    ) {
        this.prices = prices;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(
            @NotNull(message = "SKU cannot be null")
            @NotBlank String sku) {
        this.sku = sku;
    }
}
