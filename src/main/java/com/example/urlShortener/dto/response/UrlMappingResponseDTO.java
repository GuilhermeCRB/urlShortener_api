package com.example.urlShortener.dto.response;

public record UrlMappingResponseDTO(
        String title,
        String longUrl,
        String shortUrl
) {}
