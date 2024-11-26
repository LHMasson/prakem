package com.prakem.prakem.logging.service;

import com.prakem.prakem.logging.entity.RequestLog;
import com.prakem.prakem.logging.repository.RequestLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class RequestLogService {

    private final RequestLogRepository requestLogRepository;

    @Autowired
    public RequestLogService(RequestLogRepository requestLogRepository) {
        this.requestLogRepository = requestLogRepository;
    }

    @Async
    public void save(RequestLog requestLog) {
        requestLogRepository.save(requestLog);
    }
}
