package com.prakem.logging.repository;

import com.prakem.logging.entity.RequestLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestLogRepository extends MongoRepository<RequestLog, String> {

}
