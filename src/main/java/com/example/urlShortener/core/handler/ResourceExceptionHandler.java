package com.example.urlShortener.core.handler;

import com.example.urlShortener.core.constants.ApiMessages;
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGeneralException(Exception ex, WebRequest request) {
        logger.error("Internal server error: {}", ex.getMessage(), ex);
        ExceptionResponse errorResponse = new ExceptionResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ApiMessages.INTERNAL_SERVER_ERROR_MESSAGE,
                ApiMessages.INTERNAL_SERVER_ERROR_DETAILS
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .findFirst()
                .orElse(ApiMessages.UNPROCESSABLE_ENTITY_MESSAGE);

        logger.warn("Argument validation error: {}", errorMessage, ex);

        ExceptionResponse errorResponse = new ExceptionResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                ApiMessages.UNPROCESSABLE_ENTITY_MESSAGE,
                errorMessage
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        logger.warn("Request body missing or invalid: {}", ex.getMessage(), ex);
        ExceptionResponse errorResponse = new ExceptionResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                ApiMessages.REQUEST_BODY_MISSING_MESSAGE,
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
    }
}
