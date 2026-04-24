package com.example.urlShortener.controller.impl;

import com.example.urlShortener.controller.HealthController;
import com.example.urlShortener.core.constants.ApiMessages;
import com.example.urlShortener.core.constants.ApiPaths;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthControllerImpl implements HealthController {

    @Override
    @GetMapping(path = ApiPaths.HEALTH)
    public ResponseEntity<String> healthCeck() {
        return ResponseEntity.ok(ApiMessages.HEALTH_CHECK_MESSAGE);
    }
}
