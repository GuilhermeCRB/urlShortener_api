package com.example.urlShortener.service.impl;

import com.example.urlShortener.dto.request.UrlMappingRequestDTO;
import com.example.urlShortener.dto.response.UrlMappingResponseDTO;
import com.example.urlShortener.dto.mapper.UrlMappingMapper;
import com.example.urlShortener.model.UrlMapping;
import com.example.urlShortener.repository.ShortenerRepository;
import com.example.urlShortener.service.ShortenerService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class ShortenerServiceImpl implements ShortenerService {
    private static final String SHORT_URL_DOMAIN = "http://www.zg.com.br/";
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int MAX_CHARACTERS_CODE = 5;
    private static final SecureRandom RANDOM = new SecureRandom();

    private final ShortenerRepository repository;
    private final UrlMappingMapper mapper;

    public ShortenerServiceImpl(ShortenerRepository repository, UrlMappingMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public UrlMappingResponseDTO shorten(UrlMappingRequestDTO request) {
        String generatedCode;

        do {
            generatedCode = generateRandomCode();
        } while (repository.existsByCode(generatedCode));

        UrlMapping urlMapping = mapper.toEntity(request);
        urlMapping.setCode(generatedCode);
        UrlMapping saved = repository.save(urlMapping);
        
        return new UrlMappingResponseDTO(
                saved.getTitle(),
                saved.getLongUrl(),
                SHORT_URL_DOMAIN + saved.getCode()
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
