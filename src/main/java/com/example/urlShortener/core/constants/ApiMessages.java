package com.example.urlShortener.core.constants;

public class ApiMessages {

    private ApiMessages() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // Error messages
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal server error.";
    public static final String INTERNAL_SERVER_ERROR_DETAILS = "An unexpected error occurred in our system. Our technical team has been notified.";
    public static final String UNPROCESSABLE_ENTITY_MESSAGE = "Invalid request.";
    public static final String REQUEST_BODY_MISSING_MESSAGE = "Request body missing or invalid.";

    // Health check message
    public static final String HEALTH_CHECK_MESSAGE = "Application is running!";
}
