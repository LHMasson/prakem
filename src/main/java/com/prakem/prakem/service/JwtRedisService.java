package com.prakem.prakem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class JwtRedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void saveToken(String token, String userId, long expirationTime) {
        redisTemplate.opsForValue().set("jwt:" + token, userId, expirationTime, TimeUnit.SECONDS);
    }

    public boolean isTokenValid(String token) {
        return redisTemplate.hasKey("jwt:" + token);
    }

    public void deleteToken(String token) {
        redisTemplate.delete("jwt:" + token);
    }
}
