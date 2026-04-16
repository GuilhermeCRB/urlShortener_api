package com.example.urlShortener.service.impl;

import com.example.urlShortener.dto.request.ShortUrlRequestDTO;
import com.example.urlShortener.dto.response.ShortUrlResponseDTO;
import com.example.urlShortener.model.UrlMapping;
import com.example.urlShortener.repository.ShortenerRepository;
import com.example.urlShortener.service.ShortenerService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class ShortenerServiceImpl implements ShortenerService {

    private static final String SHORT_URL_DOMAIN = "http://zg.com.br/";
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int MAX_CHARACTERS_CODE = 5;
    private static final SecureRandom RANDOM = new SecureRandom();

    private final ShortenerRepository repository;

    public ShortenerServiceImpl(ShortenerRepository repository) {
        this.repository = repository;
    }

    @Override
    public ShortUrlResponseDTO shorten(ShortUrlRequestDTO request) {
        String generatedCode;

        do {
            generatedCode = generateRandomCode();
        } while (repository.existsByCode(generatedCode));

        UrlMapping urlMapping = new UrlMapping ();
        urlMapping.setTitle(request.title());
        urlMapping.setLongUrl(request.longUrl());
        urlMapping.setCode(generatedCode);
        UrlMapping response = repository.save(urlMapping);
        return new ShortUrlResponseDTO(
                response.getTitle(),
                response.getLongUrl(),
                SHORT_URL_DOMAIN + response.getCode()
        );
    }

    private String generateRandomCode() {
        char[] result = new char[MAX_CHARACTERS_CODE];

        for (int i = 0; i < MAX_CHARACTERS_CODE; i++) {
            result[i] = CHARS.charAt(RANDOM.nextInt(CHARS.length()));
        }

        return new String(result);
    }
}
