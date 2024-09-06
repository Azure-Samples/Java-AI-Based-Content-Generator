package com.example.content_generator.dataservice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

import static com.example.content_generator.dataservice.core.Common.generateCustomUUID;

@Document(collection = "Customers")
public class Customer {

    @Id
    private String id;

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull
    private Demographic demographic;

    @NotNull
    private Physiographic physiographic;

    @NotNull
    private Behavioral behavioral;

    @NotNull
    private Location location;

    @NotNull
    private DigitalBehavior digitalBehavior;

    // Constructors
    public Customer() {
        this.id = generateCustomUUID();
    }

    public Customer(
            @NotNull(message = "Name cannot be null") String name,
            @NotNull Demographic demographic,
            @NotNull Physiographic physiographic,
            @NotNull Behavioral behavioral,
            @NotNull Location location,
            @NotNull DigitalBehavior digitalBehavior
    ) {
        this.id = generateCustomUUID();
        this.name = name;
        this.demographic = demographic;
        this.physiographic = physiographic;
        this.behavioral = behavioral;
        this.location = location;
        this.digitalBehavior = digitalBehavior;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Customer data = (Customer) obj;
        return Objects.equals(id, data.id) &&
                Objects.equals(name, data.name) &&
                Objects.equals(demographic, data.demographic) &&
                Objects.equals(physiographic, data.physiographic) &&
                Objects.equals(behavioral, data.behavioral) &&
                Objects.equals(location, data.location) &&
                Objects.equals(digitalBehavior, data.digitalBehavior);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, demographic, physiographic, behavioral, location, digitalBehavior);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", demographic='" + demographic.toString() + '\'' +
                ", physiographic='" + physiographic.toString() + '\'' +
                ", behavioral='" + behavioral.toString() + '\'' +
                ", location='" + location.toString() + '\'' +
                ", digitalBehavior='" + digitalBehavior.toString() + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(@NotNull(message = "ID cannot be null") @NotBlank String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(@NotNull(message = "Name cannot be null") String name) {
        this.name = name;
    }

    public Demographic getDemographic() { return this.demographic; }

    public void setDemographic(@NotNull Demographic demographic) { this.demographic = demographic; }

    public Physiographic getPhysiographic() { return this.physiographic; }

    public void setPhysiographic(@NotNull Physiographic physiographic) { this.physiographic = physiographic; }

    public Behavioral getBehavioral() { return this.behavioral; }

    public void setBehavioral(@NotNull Behavioral behavioral) { this.behavioral = behavioral; }

    public Location getLocation() { return this.location; }

    public void setLocation(@NotNull Location location) { this.location = location; }

    public DigitalBehavior getDigitalBehavior() { return this.digitalBehavior; }

    public void  setDigitalBehavior(@NotNull DigitalBehavior digitalBehavior) { this.digitalBehavior = digitalBehavior; }
}
