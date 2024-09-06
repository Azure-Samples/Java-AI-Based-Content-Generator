package com.example.content_generator.dataservice.model;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class DigitalBehavior {
    @NotBlank
    private String socialMediaUsage;

    @NotBlank
    private String browsingHabits;

    public DigitalBehavior(@NotBlank String socialMediaUsage, @NotBlank String browsingHabits) {
        this.socialMediaUsage = socialMediaUsage;
        this.browsingHabits = browsingHabits;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DigitalBehavior data = (DigitalBehavior) obj;
        return Objects.equals(socialMediaUsage, data.socialMediaUsage) &&
                Objects.equals(browsingHabits, data.browsingHabits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(socialMediaUsage, browsingHabits);
    }

    @Override
    public String toString() {
        return "DigitalBehavior{" +
                "socialMediaUsage='" + socialMediaUsage + '\'' +
                ", browsingHabits='" + browsingHabits + '\'' +
                '}';
    }

    public String getSocialMediaUsage() {
        return socialMediaUsage;
    }

    public void setSocialMediaUsage(@NotBlank String socialMediaUsage) {
        this.socialMediaUsage = socialMediaUsage;
    }

    public String getBrowsingHabits() {
        return browsingHabits;
    }

    public void setBrowsingHabits(@NotBlank String browsingHabits) {
        this.browsingHabits = browsingHabits;
    }
}
