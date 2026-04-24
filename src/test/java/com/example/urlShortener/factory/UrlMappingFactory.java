package com.example.urlShortener.factory;

import com.example.urlShortener.dto.request.UrlMappingRequestDTO;
import com.example.urlShortener.dto.response.UrlMappingResponseDTO;
import com.example.urlShortener.model.UrlMapping;

public class UrlMappingFactory {
    public static final String DEFAULT_TITLE = "Example Title";
    public static final String DEFAULT_LONG_URL = "https://www.example.com";

    public static UrlMapping create(String title, String longUrl) {
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setTitle(title);
        urlMapping.setLongUrl(longUrl);
        return urlMapping;
    }

    public static UrlMapping createDefault() {
        return create(DEFAULT_TITLE, DEFAULT_LONG_URL);
    }

    public static UrlMappingRequestDTO createRequestDTO(String title, String longUrl) {
        return new UrlMappingRequestDTO(title, longUrl);
    }

    public static UrlMappingRequestDTO createDefaultRequestDTO() {
        return createRequestDTO(DEFAULT_TITLE, DEFAULT_LONG_URL);
    }

    public static UrlMappingResponseDTO createResponseDTO(String title, String longUrl, String shortUrl) {
        return new UrlMappingResponseDTO(title, longUrl, shortUrl);
    }

    public static UrlMappingResponseDTO createDefaultResponseDTO(String shortUrl) {
        return createResponseDTO(DEFAULT_TITLE, DEFAULT_LONG_URL, shortUrl);
    }
}
