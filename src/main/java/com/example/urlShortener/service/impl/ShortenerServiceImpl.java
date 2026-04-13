package com.example.urlShortener.service.impl;

import com.example.urlShortener.dto.response.ShortUrlResponseDTO;
import com.example.urlShortener.service.ShortenerService;
import org.springframework.stereotype.Service;

@Service
public class ShortenerServiceImpl implements ShortenerService {
    @Override
    public ShortUrlResponseDTO shorten(String longUrl) {
            return new ShortUrlResponseDTO("");
    }
}
