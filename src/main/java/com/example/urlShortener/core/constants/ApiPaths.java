package com.example.urlShortener.core.constants;

public class ApiPaths {

    private ApiPaths() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // Base paths
    public static final String API_BASE = "/api";

    // Health endpoint
    public static final String HEALTH = API_BASE + "/health";

    // Shortener endpoints
    public static final String SHORTENER = API_BASE + "/shortener";
}
