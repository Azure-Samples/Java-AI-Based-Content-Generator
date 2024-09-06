package com.example.content_generator.aiservice.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class Price {

    @NotNull(message = "Currency cannot be null")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Invalid currency code. It should be a 3-letter ISO 4217 code.")
    private String currencyCode;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private double value;

    public Price(
            @NotNull(message = "Currency cannot be null")
            @Pattern(regexp = "^[A-Z]{3}$", message = "Invalid currency code. It should be a 3-letter ISO 4217 code.") String currencyCode,
            @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0") double value) {
        this.currencyCode = currencyCode;
        this.value = value;
    }


    public @NotNull(message = "Currency cannot be null") @Pattern(regexp = "^[A-Z]{3}$", message = "Invalid currency code. It should be a 3-letter ISO 4217 code.") String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(@NotNull(message = "Currency cannot be null") @Pattern(regexp = "^[A-Z]{3}$", message = "Invalid currency code. It should be a 3-letter ISO 4217 code.") String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    public double getValue() {
        return value;
    }

    public void setValue(@DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0") double value) {
        this.value = value;
    }
}
