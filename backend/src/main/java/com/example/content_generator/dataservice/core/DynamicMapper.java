package com.example.content_generator.dataservice.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class DynamicMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicMapper.class);

    public static <T, U> U mapToModel(T dto, Class<U> modelClass) {
        if (dto == null || modelClass == null) {
            throw new IllegalArgumentException("DTO and model class must not be null");
        }

        U modelInstance = null;
        try {
            // Create a new instance of the model class
            modelInstance = modelClass.getDeclaredConstructor().newInstance();

            // Get all fields from DTO class
            Field[] dtoFields = dto.getClass().getDeclaredFields();

            for (Field dtoField : dtoFields) {
                dtoField.setAccessible(true); // Allow access to private fields
                Object value = dtoField.get(dto); // Get the field value from the DTO

                // Get the corresponding field in the model class
                Field modelField;
                try {
                    modelField = modelClass.getDeclaredField(dtoField.getName());
                    modelField.setAccessible(true); // Allow access to private fields

                    // Set the value of the field in the model
                    if (modelField.getType().isAssignableFrom(dtoField.getType())) {
                        modelField.set(modelInstance, value);
                    } else {
                        LOGGER.warn("Type mismatch for field: {}", dtoField.getName());
                    }
                } catch (NoSuchFieldException e) {
                    LOGGER.warn("Field not found in model class: {}", dtoField.getName());
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error mapping DTO to model", e);
        }

        return modelInstance;
    }
}