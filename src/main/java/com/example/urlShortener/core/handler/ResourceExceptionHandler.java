package com.example.urlShortener.core.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ResourceExceptionHandler.class);

    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal server error.";
    public static final String INTERNAL_SERVER_ERROR_DETAILS = "An unexpected error occurred in our system. Our technical team has been notified.";
    public static final String UNPROCESSABLE_ENTITY_MESSAGE = "Invalid request.";
    public static final String REQUEST_BODY_MISSING_MESSAGE = "Request body missing or invalid.";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGeneralException(Exception ex, WebRequest request) {
        logger.error("Internal server error: {}", ex.getMessage(), ex);
        ExceptionResponse errorResponse = new ExceptionResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                INTERNAL_SERVER_ERROR_MESSAGE,
                INTERNAL_SERVER_ERROR_DETAILS
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .findFirst()
                .orElse(UNPROCESSABLE_ENTITY_MESSAGE);

        logger.warn("Argument validation error: {}", errorMessage, ex);

        ExceptionResponse errorResponse = new ExceptionResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                UNPROCESSABLE_ENTITY_MESSAGE,
                errorMessage
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        logger.warn("Request body missing or invalid: {}", ex.getMessage(), ex);
        ExceptionResponse errorResponse = new ExceptionResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                REQUEST_BODY_MISSING_MESSAGE,
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
    }
}
