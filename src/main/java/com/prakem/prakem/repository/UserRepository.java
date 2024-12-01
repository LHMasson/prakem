package com.prakem.prakem.repository;

import com.mongodb.lang.Nullable;
import com.prakem.prakem.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    @Nullable
    User findByEmail(String email);
}
