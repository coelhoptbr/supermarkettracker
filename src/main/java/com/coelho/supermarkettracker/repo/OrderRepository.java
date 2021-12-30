package com.coelho.supermarkettracker.repo;

import com.coelho.supermarkettracker.domain.Order;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, ObjectId> {
}
