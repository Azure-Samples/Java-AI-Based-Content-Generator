package com.example.content_generator.dataservice.model;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class Physiographic {
    @NotBlank
    private String interests;

    @NotBlank
    private String values;

    @NotBlank
    private String lifestyle;

    public Physiographic(@NotBlank String interests, @NotBlank String values, @NotBlank String lifestyle) {
        this.interests = interests;
        this.values = values;
        this.lifestyle = lifestyle;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Physiographic data = (Physiographic) obj;
        return Objects.equals(interests, data.interests) &&
                Objects.equals(values, data.values) &&
                Objects.equals(lifestyle, data.lifestyle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(interests, values, lifestyle);
    }

    @Override
    public String toString() {
        return "Physiographic{" +
                "interests='" + interests + '\'' +
                ", values='" + values + '\'' +
                ", lifestyle='" + lifestyle + '\'' +
                '}';
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(@NotBlank String interests) {
        this.interests = interests;
    }

    public String getValues() {
        return values;
    }

    public void setValues(@NotBlank String values) {
        this.values = values;
    }

    public String getLifestyle() {
        return lifestyle;
    }

    public void setLifestyle(@NotBlank String lifestyle) {
        this.lifestyle = lifestyle;
    }
}
