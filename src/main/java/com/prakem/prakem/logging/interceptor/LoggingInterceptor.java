package com.prakem.prakem.logging.interceptor;

import com.prakem.prakem.logging.entity.RequestLog;
import com.prakem.prakem.logging.service.RequestLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.stream.Collectors;
import java.util.concurrent.CompletableFuture;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);
    private final RequestLogService requestLogService;

    @Autowired
    public LoggingInterceptor(RequestLogService requestLogService) {
        this.requestLogService = requestLogService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute("startTime", System.currentTimeMillis());
        request.setAttribute("requestTime", LocalDateTime.now());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        logRequestAndResponse(request, response);
    }

    private void logRequestAndResponse(HttpServletRequest request, HttpServletResponse response) {
        LocalDateTime requestTime = (LocalDateTime) request.getAttribute("requestTime");
        Long startTime = (Long) request.getAttribute("startTime");
        String httpMethod = request.getMethod();
        String url = request.getRequestURI();
        String clientIp = request.getRemoteAddr();
        String headers = getHeaders(request);
        int statusCode = response.getStatus();

        CompletableFuture.runAsync(() -> {
            try {
                RequestLog log = new RequestLog();
                log.setHttpMethod(httpMethod);
                log.setUrl(url);
                log.setRequestBody("<Not Implemented>");
                log.setResponseBody("<Not Implemented>");
                log.setStatusCode(statusCode);
                log.setClientIp(clientIp);
                log.setHeaders(headers);
                log.setRequestTime(requestTime);
                log.setResponseTime(LocalDateTime.now());

                if (startTime != null) {
                    log.setResponseTimeMs(System.currentTimeMillis() - startTime);
                }

                requestLogService.save(log);
            } catch (Exception e) {
                logger.error("Error while logging request", e);
            }
        });
    }

    private String getHeaders(HttpServletRequest request) {
        try {
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames == null) {
                return "{}";
            }

            StringBuilder headers = new StringBuilder("{");
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                headers.append("\"")
                        .append(headerName)
                        .append("\": \"")
                        .append(request.getHeader(headerName))
                        .append("\", ");
            }
            if (headers.length() > 1) {
                headers.setLength(headers.length() - 2); // Remove trailing comma and space
            }
            headers.append("}");
            return headers.toString();
        } catch (Exception e) {
            return "{}";
        }
    }
}
