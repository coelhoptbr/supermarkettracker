package com.coelho.supermarkettracker.repo;

import com.coelho.supermarkettracker.domain.Product;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, ObjectId> {
}
