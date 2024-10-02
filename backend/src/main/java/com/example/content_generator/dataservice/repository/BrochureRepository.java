package com.example.content_generator.dataservice.repository;

import com.example.content_generator.dataservice.model.Brochure;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * BrochureRepository is responsible for providing CRUD operations
 * and custom queries for the Brochure collection in Azure Cosmos DB.
 * This repository extends the CosmosRepository interface, leveraging
 * Azure Spring Data Cosmos to automatically generate the necessary
 * methods for data access.
 *
 */

public interface BrochureRepository extends MongoRepository<Brochure, String> {

    /**
     * Finds brochures by the associated product ID.
     *
     * @param productId the ID of the product
     * @return A list of brochures associated with the given product ID.
     */

    List<Brochure> findByProductId(String productId);

    /**
     * Finds brochures by a list of product IDs.
     *
     * @param productIds A list of product IDs.
     * @return A list of {@link Brochure} entities associated with the given product IDs.
     */
    @Query("{ 'productId': { $in: ?0 } }")
    List<Brochure> findByProductIds(List<String> productIds);
}
