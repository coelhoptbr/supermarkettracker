package com.coelho.supermarkettracker.repo;

import com.coelho.supermarkettracker.domain.UserInfo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserInfoRepository extends MongoRepository<UserInfo, ObjectId> {

}
