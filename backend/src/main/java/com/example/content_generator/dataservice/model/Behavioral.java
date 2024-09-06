package com.example.content_generator.dataservice.model;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class Behavioral {
    @NotBlank
    private String purchasingHabits;
    @NotBlank
    private String brandLoyalty;

    public Behavioral( String purchasingHabits, String brandLoyalty ) {
        this.purchasingHabits = purchasingHabits;
        this.brandLoyalty = brandLoyalty;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Behavioral data = (Behavioral) obj;
        return Objects.equals(purchasingHabits, data.purchasingHabits) &&
                Objects.equals(brandLoyalty, data.brandLoyalty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(purchasingHabits, brandLoyalty);
    }

    @Override
    public String toString() {
        return "Behavioral{" +
                "purchasingHabits='" + purchasingHabits + '\'' +
                ", brandLoyalty='" + brandLoyalty + '\'' +
                '}';
    }

    public String getPurchasingHabits() {
        return purchasingHabits;
    }

    public void setPurchasingHabits(String purchasingHabits) {
        this.purchasingHabits = purchasingHabits;
    }

    public String getBrandLoyalty() {
        return brandLoyalty;
    }

    public void setBrandLoyalty(String brandLoyalty) {
        this.brandLoyalty = brandLoyalty;
    }
}
