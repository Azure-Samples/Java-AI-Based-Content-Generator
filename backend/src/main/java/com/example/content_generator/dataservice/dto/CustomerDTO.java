package com.example.content_generator.dataservice.dto;

import com.example.content_generator.dataservice.model.*;

public class CustomerDTO {
    private String name;
    private Demographic demographic;
    private Physiographic physiographic;
    private Behavioral behavioral;
    private Location location;
    private DigitalBehavior digitalBehavior;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Demographic getDemographic() {
        return demographic;
    }

    public void setDemographic(Demographic demographic) {
        this.demographic = demographic;
    }

    public Physiographic getPhysiographic() {
        return physiographic;
    }

    public void setPhysiographic(Physiographic physiographic) {
        this.physiographic = physiographic;
    }

    public Behavioral getBehavioral() {
        return behavioral;
    }

    public void setBehavioral(Behavioral behavioral) {
        this.behavioral = behavioral;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public DigitalBehavior getDigitalBehavior() {
        return digitalBehavior;
    }

    public void setDigitalBehavior(DigitalBehavior digitalBehavior) {
        this.digitalBehavior = digitalBehavior;
    }
}
