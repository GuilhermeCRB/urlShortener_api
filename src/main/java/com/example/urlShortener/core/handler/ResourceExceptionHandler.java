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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGeneralException(Exception ex, WebRequest request) {
        logger.error("Erro interno no servidor: {}", ex.getMessage(), ex);
        ExceptionResponse errorResponse = new ExceptionResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno no servidor.",
                "Ocorreu um erro inesperado em nosso sistema. Nossa equipe técnica já foi notificada."
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        logger.warn("Requisição inválida: {}", ex.getMessage(), ex);
        ExceptionResponse errorResponse = new ExceptionResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Requisição inválida.",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        logger.warn("Erro de validação nos argumentos: {}", ex.getMessage(), ex);
        ExceptionResponse errorResponse = new ExceptionResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Requisição inválida.",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        logger.warn("Corpo da requisição ausente ou inválido: {}", ex.getMessage(), ex);
        ExceptionResponse errorResponse = new ExceptionResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Corpo da requisição ausente ou inválido.",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
