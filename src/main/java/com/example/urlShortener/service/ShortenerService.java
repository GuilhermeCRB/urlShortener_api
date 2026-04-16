package com.example.urlShortener.service;

import com.example.urlShortener.dto.request.ShortUrlRequestDTO;
import com.example.urlShortener.dto.response.ShortUrlResponseDTO;

public interface ShortenerService {
    ShortUrlResponseDTO shorten(ShortUrlRequestDTO longUrl);
}
