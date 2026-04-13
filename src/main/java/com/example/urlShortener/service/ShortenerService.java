package com.example.urlShortener.service;

import com.example.urlShortener.dto.response.ShortUrlResponseDTO;

public interface ShortenerService {
    public ShortUrlResponseDTO shorten(String longUrl);
}
