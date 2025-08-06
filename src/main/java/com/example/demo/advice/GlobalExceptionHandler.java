package com.example.demo.advice;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import com.example.demo.dto.ApiError;
import com.example.demo.exception.ResourceNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ApiError error = new ApiError(
            HttpStatus.NOT_FOUND.value(), 
            ex.getMessage(), 
            request.getDescription(false),
            LocalDateTime.now()
        );
        return new ResponseEntity<>(
            error, 
            HttpStatus.NOT_FOUND
        );
    }
}
