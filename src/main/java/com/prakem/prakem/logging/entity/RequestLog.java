package com.prakem.prakem.logging.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Document("request_logs")
public class RequestLog {

    @Id
    private String id;

    private String httpMethod;
    private String url;
    private String requestBody;
    private String responseBody;
    private Integer statusCode;
    private String clientIp;
    private String headers;
    private Long responseTimeMs;
    private LocalDateTime requestTime;
    private LocalDateTime responseTime;

    public RequestLog(String httpMethod, String url, String requestBody, String responseBody, Integer statusCode,
                      String clientIp, String headers, Long responseTimeMs, LocalDateTime requestTime, LocalDateTime responseTime) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.requestBody = requestBody;
        this.responseBody = responseBody;
        this.statusCode = statusCode;
        this.clientIp = clientIp;
        this.headers = headers;
        this.responseTimeMs = responseTimeMs;
        this.requestTime = requestTime;
        this.responseTime = responseTime;
    }
}
