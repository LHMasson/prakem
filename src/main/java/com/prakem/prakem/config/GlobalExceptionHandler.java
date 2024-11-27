package com.prakem.prakem.config;

import com.prakem.prakem.dto.ErrorResponse;
import com.prakem.prakem.exceptions.EmailAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    //@ExceptionHandler(Exception.class)
    //public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
    //    ErrorResponse error = new ErrorResponse("Internal Error", HttpStatus.INTERNAL_SERVER_ERROR.value());
    //    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    //}
}
