package com.example.content_generator.dataservice.repository;

import com.example.content_generator.dataservice.model.Brochure;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * BrochureRepository is responsible for providing CRUD operations
 * and custom queries for the Brochure collection in MongoDB. This repository
 * extends the MongoRepository interface, leveraging Spring Data MongoDB to
 * automatically generate the necessary methods for data access.
 * Finds brochures by the associated product ID.
 * @param productId the ID of the product
 *
 * It returns a list of brochures associated with the given product ID
 *
 */

public interface BrochureRepository extends MongoRepository<Brochure, String> {
    List<Brochure> findByProductId(String productId);
}
