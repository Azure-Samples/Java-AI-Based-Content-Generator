package com.example.content_generator.dataservice.repository;

import com.example.content_generator.dataservice.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * CustomerMongoRepository is responsible for providing CRUD operations
 * and custom queries for the Customer collection in MongoDB. This repository
 * extends the MongoRepository interface, leveraging Spring Data MongoDB to
 * automatically generate the necessary methods for data access.
 *
 * @param <Customer> the domain type the repository manages
 * @param <String>  the type of the id of the entity the repository manages
 */

public interface CustomerRepository extends MongoRepository<Customer, String> { }
