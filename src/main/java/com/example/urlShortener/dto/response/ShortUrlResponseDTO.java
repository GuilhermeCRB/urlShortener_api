package com.example.urlShortener.dto.response;

public record ShortUrlResponseDTO (
        String title,
        String longUrl,
        String shortUrl
) {}
