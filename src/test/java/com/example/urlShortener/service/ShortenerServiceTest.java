package com.example.urlShortener.service;

import com.example.urlShortener.dto.request.UrlMappingRequestDTO;
import com.example.urlShortener.dto.response.UrlMappingResponseDTO;
import com.example.urlShortener.dto.mapper.UrlMappingMapper;
import com.example.urlShortener.factory.UrlMappingFactory;
import com.example.urlShortener.model.UrlMapping;
import com.example.urlShortener.repository.ShortenerRepository;
import com.example.urlShortener.service.impl.ShortenerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ShortenerServiceTest {

    @Value("${shortener.domain}")
    private String SHORTENER_DOMAIN;

    @MockBean
    private ShortenerRepository repository;

    @MockBean
    private UrlMappingMapper mapper;

    @Autowired
    private ShortenerServiceImpl service;

    @Nested
    @DisplayName("Testes do método shorten")
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
        @DisplayName("Teste unitário dado uma URL válida quando shorten deve retornar a URL encurtada")
        void TesteDadoUrlValida_QuandoShorten_DeveRetornarUrlEncurtada() {
            // given
            when(repository.existsByCode(any(String.class))).thenReturn(false);
            when(mapper.toEntity(requestDTO)).thenReturn(urlMapping);
            when(repository.save(any(UrlMapping.class))).thenReturn(urlMapping);

            // when
            UrlMappingResponseDTO response = service.shorten(requestDTO);

            // then
            assertNotNull(response, "A resposta não deve ser nula.");
            assertEquals(title, response.title(), "O título retornado deve ser o mesmo do título fornecido.");
            assertEquals(validUrl, response.longUrl(), "A URL longa retornada deve ser a mesma da URL fornecida.");
            assertTrue(response.shortUrl().startsWith(SHORTENER_DOMAIN), "A URL encurtada deve começar com o domínio configurado.");
            assertTrue(response.shortUrl().length() > SHORTENER_DOMAIN.length(), "A URL encurtada deve conter um código após o domínio.");
        }

        @Test
        @DisplayName("Teste unitário quando shorten deve gerar código com 5 caracteres")
        void testeQuandoShorten_DeveGerarCodigoCom5Caracteres() {
            // given
            when(repository.existsByCode(any(String.class))).thenReturn(false);
            when(mapper.toEntity(requestDTO)).thenReturn(urlMapping);
            when(repository.save(any(UrlMapping.class))).thenReturn(urlMapping);

            // when
            UrlMappingResponseDTO response = service.shorten(requestDTO);
            String code = response.shortUrl().substring(SHORTENER_DOMAIN.length());

            // then
            assertEquals(5, code.length(), "O código deve ter exatamente 5 caracteres.");
        }

        @Test
        @DisplayName("Teste unitário quando shorten deve gerar código apenas com caracteres válidos")
        void testeQuandoShorten_DeveGerarCodigoApenasComCaracteresValidos() {
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
                assertTrue(validChars.indexOf(c) >= 0, "O código deve conter apenas caracteres alfanuméricos: " + c);
            }
        }

        @Test
        @DisplayName("Teste unitário quando shorten deve gerar códigos diferentes (aleatoriedade)")
        void testeQuandoShorten_DeveGerarCodigosDiferentes() {
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
            assertEquals(100, generatedCodes.size(), "Deve gerar " + iterations + " códigos diferentes. Gerados: " + generatedCodes.size() + " únicos de " + iterations);
        }

        @Test
        @DisplayName("Teste unitário quando existsByCode retorna true deve gerar novo código")
        void testeQuandoExistsByCodeRetornaTrue_DeveGerarNovoCodigo() {
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
