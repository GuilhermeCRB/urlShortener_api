package com.example.urlShortener.unit.service;

import com.example.urlShortener.dto.request.UrlMappingRequestDTO;
import com.example.urlShortener.dto.response.UrlMappingResponseDTO;
import com.example.urlShortener.dto.mapper.UrlMappingMapper;
import com.example.urlShortener.factory.UrlMappingFactory;
import com.example.urlShortener.model.UrlMapping;
import com.example.urlShortener.repository.UrlMappingRepository;
import com.example.urlShortener.service.impl.ShortenerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ShortenerServiceTest extends BaseServiceTest {

    @Value("${shortener.domain}")
    private String SHORTENER_DOMAIN;

    @MockBean
    private UrlMappingRepository repository;

    @MockBean
    private UrlMappingMapper mapper;

    @Autowired
    private ShortenerServiceImpl service;

    @Nested
    @DisplayName("Tests for shorten() method")
    class ShortenTests {
        private String title;
        private String validUrl;
        private UrlMappingRequestDTO requestDTO;
        private UrlMapping urlMapping;

        @BeforeEach
        void setUp() {
            title = "Example Title";
            validUrl = "https://www.example.com";
            requestDTO = UrlMappingFactory.createRequestDTO(title, validUrl);
            urlMapping = UrlMappingFactory.create(title, validUrl);
        }

        @Test
        @DisplayName("Should return shortened URL when given a valid URL")
        void shouldReturnShortenedUrl_WhenGivenValidUrl() {
            // given
            when(repository.existsByCode(any(String.class))).thenReturn(false);
            when(mapper.toEntity(requestDTO)).thenReturn(urlMapping);
            when(repository.save(any(UrlMapping.class))).thenReturn(urlMapping);

            // when
            UrlMappingResponseDTO response = service.shorten(requestDTO);

            // then
            assertNotNull(response, "Response should not be null.");
            assertEquals(title, response.title(), "Returned title should match the provided title.");
            assertEquals(validUrl, response.longUrl(), "Returned long URL should match the provided URL.");
            assertTrue(response.shortUrl().startsWith(SHORTENER_DOMAIN), "Shortened URL should start with the configured domain.");
            assertTrue(response.shortUrl().length() > SHORTENER_DOMAIN.length(), "Shortened URL should contain a code after the domain.");
        }

        @Test
        @DisplayName("Should generate code with 5 characters")
        void shouldGenerateCodeWith5Characters() {
            // given
            when(repository.existsByCode(any(String.class))).thenReturn(false);
            when(mapper.toEntity(requestDTO)).thenReturn(urlMapping);
            when(repository.save(any(UrlMapping.class))).thenReturn(urlMapping);

            // when
            UrlMappingResponseDTO response = service.shorten(requestDTO);
            String code = response.shortUrl().substring(SHORTENER_DOMAIN.length());

            // then
            assertEquals(5, code.length(), "Code should have exactly 5 characters.");
        }

        @Test
        @DisplayName("Should generate code with only valid characters")
        void shouldGenerateCodeWithOnlyValidCharacters() {
            // given
            String validChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            when(repository.existsByCode(any(String.class))).thenReturn(false);
            when(mapper.toEntity(requestDTO)).thenReturn(urlMapping);
            when(repository.save(any(UrlMapping.class))).thenReturn(urlMapping);

            // when
            UrlMappingResponseDTO response = service.shorten(requestDTO);
            String code = response.shortUrl().substring(SHORTENER_DOMAIN.length());

            // then
            for (char c : code.toCharArray()) {
                assertTrue(validChars.indexOf(c) >= 0, "Code should contain only alphanumeric characters: " + c);
            }
        }

        @Test
        @DisplayName("Should generate different codes (randomness)")
        void shouldGenerateDifferentCodes() {
            // given
            Set<String> generatedCodes = new HashSet<>();
            int iterations = 100;

            when(repository.existsByCode(any(String.class))).thenReturn(false);

            // when
            for (int i = 0; i < iterations; i++) {
                when(mapper.toEntity(requestDTO)).thenReturn(urlMapping);
                when(repository.save(any(UrlMapping.class))).thenReturn(urlMapping);

                UrlMappingResponseDTO response = service.shorten(requestDTO);
                String code = response.shortUrl().substring(SHORTENER_DOMAIN.length());
                generatedCodes.add(code);
            }

            // then
            assertEquals(100, generatedCodes.size(), "Should generate " + iterations + " different codes. Generated: " + generatedCodes.size() + " unique out of " + iterations);
        }

        @Test
        @DisplayName("Should generate new code when existsByCode returns true")
        void shouldGenerateNewCode_WhenExistsByCodeReturnsTrue() {
            // given
            when(repository.existsByCode(any(String.class))).thenReturn(true, true, false);
            when(mapper.toEntity(requestDTO)).thenReturn(urlMapping);
            when(repository.save(any(UrlMapping.class))).thenReturn(urlMapping);

            // when
            UrlMappingResponseDTO response = service.shorten(requestDTO);

            // then
            assertNotNull(response);
            assertTrue(response.shortUrl().startsWith(SHORTENER_DOMAIN));
            verify(repository, times(3)).existsByCode(any(String.class));
        }
    }
}
