package com.example.urlShortener.controller;

import org.springframework.http.ResponseEntity;

public interface HealthController {
    ResponseEntity<String> healthCeck();
}
