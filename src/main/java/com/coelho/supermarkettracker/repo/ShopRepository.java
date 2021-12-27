package com.coelho.supermarkettracker.repo;

import com.coelho.supermarkettracker.domain.Shop;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShopRepository extends MongoRepository<Shop, ObjectId> {
}
