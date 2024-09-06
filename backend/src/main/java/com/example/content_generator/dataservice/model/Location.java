package com.example.content_generator.dataservice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;


public class Location {

    @NotNull(message = "City cannot be null")
    private String city;

    @NotBlank
    private String state;

    @NotNull(message = "Country cannot be null")
    private String country;

    public Location(
            @NotNull(message = "City cannot be null") String city,
            @NotBlank String state,
            @NotNull(message = "Country cannot be null") String country) {
        this.city = city;
        this.state = state;
        this.country = country;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Location data = (Location) obj;
        return Objects.equals(city, data.city) &&
                Objects.equals(state, data.state) &&
                Objects.equals(country, data.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, state, country);
    }

    @Override
    public String toString() {
        return "Location{" +
                "city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    public String getCity() {
        return city;
    }

    public void setCity(@NotNull(message = "City cannot be null") String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(@NotBlank String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(@NotNull(message = "Country cannot be null") String country) {
        this.country = country;
    }
}
