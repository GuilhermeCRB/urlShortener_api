package com.example.urlShortener.core.handler;

public record ExceptionResponse(
        int status,
        String exception,
        String message
) {}
