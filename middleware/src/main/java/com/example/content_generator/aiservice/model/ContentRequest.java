package com.example.content_generator.aiservice.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Objects;

public class ContentRequest {
    @NotNull(message = "Message cannot be null")
    private String message;

    @NotNull(message = "Type cannot be null")
    @Pattern(regexp = "blog|mail_template|social_media", message = "Invalid type")
    private String type;

    public ContentRequest(
            @NotNull(message = "Message cannot be null") String message,
            @NotNull(message = "Type cannot be null")
            @Pattern(regexp = "blog|mail_template|social_media", message = "Invalid type") String type
    ) {
        this.message = message;
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ContentRequest data = (ContentRequest) obj;
        return Objects.equals(message, data.message) &&
                Objects.equals(type, data.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, type);
    }

    @Override
    public String toString() {
        return "ContentRequest{" +
                "message='" + message + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
