package com.example.urlShortener.service;

import com.example.urlShortener.dto.request.UrlMappingRequestDTO;
import com.example.urlShortener.dto.response.UrlMappingResponseDTO;

public interface ShortenerService {
    UrlMappingResponseDTO shorten(UrlMappingRequestDTO longUrl);
}
