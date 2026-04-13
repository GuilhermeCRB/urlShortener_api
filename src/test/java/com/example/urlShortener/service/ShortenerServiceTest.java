package com.example.urlShortener.service;

import com.example.urlShortener.dto.request.ShortUrlRequestDTO;
import com.example.urlShortener.dto.response.ShortUrlResponseDTO;
import com.example.urlShortener.model.UrlMapping;
import com.example.urlShortener.repository.ShortenerRepository;
import com.example.urlShortener.service.impl.ShortenerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ShortenerServiceTest {

    private static final String SHORT_URL_DOMAIN = "http://zg.com.br/";
    private static final String SHORT_CODE = "abc123";

    @Mock
    private ShortenerRepository repository;

    @InjectMocks
    private ShortenerServiceImpl service;

    @Test
    @DisplayName("Teste unitário dado uma URL válida quando shorten deve retornar a URL encurtada")
    void TesteDadoUrlValida_QuandoShorten_DeveRetornarUrlEncurtada() {
        // given
        String validUrl = "https://www.example.com";
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setLongUrl(validUrl);
        urlMapping.setShortCode(SHORT_CODE);
        when(repository.save(any(UrlMapping.class))).thenReturn(urlMapping);

        // when
        ShortUrlResponseDTO response = service.shorten(validUrl);

        // then
        assertNotNull(response, "A resposta não deve ser nula.");
        assertEquals(SHORT_URL_DOMAIN + SHORT_CODE, response.shortUrl(), "A URL encurtada retornada deve ser a esperada.");
    }

    @Test
    @DisplayName("Teste unitário dado uma URL inválida quando generateShortUrl deve retornar status 422")
    void TesteDadoUrlInvalida_QuandoShorten_DeveRetornarStatus422() {
        // given
        String invalidUrl = "invalid-url";

        // when & then
        assertThrows(IllegalArgumentException.class,
                () -> service.shorten(invalidUrl),
                "Deve lançar RequisicaoNaoProcessavelException para uma URL inválida.");
    }
}
