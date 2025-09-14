package com.amazingshop.personal.productservice.controllers;

import com.amazingshop.personal.productservice.dto.responses.ErrorResponse;
import com.amazingshop.personal.productservice.util.exceptions.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));

        log.warn("Validation failed: {}", errorMessage);
        return new ResponseEntity<>(ErrorResponse.makeErrorResponse(errorMessage),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("Illegal argument: {}", e.getMessage());
        return new ResponseEntity<>(ErrorResponse.makeErrorResponse("Invalid argument: " + e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException e) {
        log.warn("Product not found: {}", e.getMessage());
        return new ResponseEntity<>(ErrorResponse.makeErrorResponse(e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handlerException(Exception e) {
        log.error("Unexpected error occurred", e);
        return new ResponseEntity<>(ErrorResponse.makeErrorResponse("An unexpected error occurred!"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}