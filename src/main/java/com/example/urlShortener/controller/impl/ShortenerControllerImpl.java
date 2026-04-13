package com.example.urlShortener.controller.impl;

import com.example.urlShortener.controller.ShortenerController;
import com.example.urlShortener.dto.request.ShortUrlRequestDTO;
import com.example.urlShortener.dto.response.ShortUrlResponseDTO;
import com.example.urlShortener.service.ShortenerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "URL Shortener", description = "Endpoint para encurtamento de URLs")
@RestController
@RequestMapping("/api/shortener")
public class ShortenerControllerImpl implements ShortenerController {

    private final ShortenerService shortenerService;

    public ShortenerControllerImpl(ShortenerService shortenerService) {
        this.shortenerService = shortenerService;
    }

    @Override
    @PostMapping(path = "/", produces = "application/json")
    public ResponseEntity<ShortUrlResponseDTO> generateShortUrl(@Valid @RequestBody ShortUrlRequestDTO request) {
        ShortUrlResponseDTO response = shortenerService.shorten(request.longUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
