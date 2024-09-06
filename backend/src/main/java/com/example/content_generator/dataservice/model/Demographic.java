package com.example.content_generator.dataservice.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class Demographic {
    @NotNull(message = "Gender cannot be null")
    private String gender;

    @NotNull(message = "Age cannot be null")
    @Min(value = 0, message = "Age must be greater than 0")
    private int age;

    @NotBlank(message = "Education cannot be blank")
    private String education;

    @NotBlank(message = "Occupation cannot be null")
    private String occupation;

    public Demographic(
            @NotNull(message = "Gender cannot be null") String gender,
            @NotNull(message = "Age cannot be null")
            @Min(value = 0, message = "Age must be greater than 0") int age,
            @NotBlank(message = "Education cannot be blank") String education,
            @NotBlank(message = "Occupation cannot be null") String occupation
    ) {
        this.gender = gender;
        this.age = age;
        this.education = education;
        this.occupation = occupation;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Demographic data = (Demographic) obj;
        return Objects.equals(gender, data.gender) &&
                Objects.equals(age, data.age) &&
                Objects.equals(education, data.education) &&
                Objects.equals(occupation, data.occupation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gender, age, education, occupation);
    }

    @Override
    public String toString() {
        return "Demographic{" +
                "gender='" + gender + '\'' +
                ", age='" + age + '\'' +
                ", education='" + education + '\'' +
                ", occupation='" + occupation + '\'' +
                '}';
    }

    public String getGender() {
        return gender;
    }

    public void setGender(@NotNull(message = "Gender cannot be null") String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(@NotNull(message = "Age cannot be null")
                       @Min(value = 0, message = "Age must be greater than 0") int age) {
        this.age = age;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(@NotBlank(message = "Education cannot be blank") String education) {
        this.education = education;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(@NotBlank(message = "Occupation cannot be null") String occupation) {
        this.occupation = occupation;
    }
}
