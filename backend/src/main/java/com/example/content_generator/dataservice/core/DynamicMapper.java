package com.example.content_generator.dataservice.core;

import java.lang.reflect.Field;

public class DynamicMapper {

    public static <T, U> U mapToModel(T dto, Class<U> modelClass) {
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
                    modelField.set(modelInstance, value);
                } catch (NoSuchFieldException e) {
                    // Skip fields that don't exist in the model
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return modelInstance;
    }
}
