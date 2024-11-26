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
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        CompletableFuture.runAsync(() -> {
            try {
                RequestLog log = new RequestLog();
                log.setHttpMethod(request.getMethod());
                log.setUrl(request.getRequestURI());
                log.setRequestBody(getRequestBody(request));
                log.setResponseBody("<Captured in ResponseWrapper if needed>");
                log.setStatusCode(response.getStatus());
                log.setClientIp(request.getRemoteAddr());
                log.setHeaders(getHeaders(request));
                log.setRequestTime((LocalDateTime) request.getAttribute("requestTime"));
                log.setResponseTime(LocalDateTime.now());
                long startTime = (Long) request.getAttribute("startTime");
                log.setResponseTimeMs(System.currentTimeMillis() - startTime);
                requestLogService.save(log); // Ensure this is async as well
            } catch (Exception e) {
                logger.error("Error while logging request", e); // Log the error
            }
        });
    }

    private String getRequestBody(HttpServletRequest request) {
        try {
            BufferedReader reader = request.getReader();
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (Exception e) {
            return "<Failed to capture request body>";
        }
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
            headers.append("}");
            return headers.toString();
        } catch (Exception e) {
            return "{}";
        }
    }
}
