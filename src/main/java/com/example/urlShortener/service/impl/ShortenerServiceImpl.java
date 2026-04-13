package com.example.urlShortener.service.impl;

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
    public ShortUrlResponseDTO shorten(String longUrl) {
        String code;

        do {
            code = generateRandomCode();
        } while (repository.existsByShortCode(code));

        UrlMapping urlMapping = new UrlMapping ();
        UrlMapping shortenUrl = repository.save(urlMapping);
        return new ShortUrlResponseDTO(SHORT_URL_DOMAIN + shortenUrl.getShortCode());
    }

    private String generateRandomCode() {
        char[] result = new char[MAX_CHARACTERS_CODE];

        for (int i = 0; i < MAX_CHARACTERS_CODE; i++) {
            result[i] = CHARS.charAt(RANDOM.nextInt(CHARS.length()));
        }

        return new String(result);
    }
}
