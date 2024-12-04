package com.prakem.prakem.repository;

import com.mongodb.lang.Nullable;
import com.prakem.prakem.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByEmail(String email);
    User findByEmail(String email);
}
