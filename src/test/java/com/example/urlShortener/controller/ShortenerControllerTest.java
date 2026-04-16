package com.example.urlShortener.controller;

import com.example.urlShortener.dto.request.ShortUrlRequestDTO;
import com.example.urlShortener.dto.response.ShortUrlResponseDTO;
import com.example.urlShortener.service.impl.ShortenerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShortenerController.class)
public class ShortenerControllerTest {

    private static final String SHORT_URL_DOMAIN = "http://zg.com.br/";
    private static final String PATH = "/api/shortener";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ShortenerServiceImpl shortenerService;

    @Test
    @DisplayName("Teste unitário dado uma URL válida quando generateShortUrl deve retornar status 200 e a URL encurtada com os novos campos")
    void testeDadoUrlValida_QuandoGenerateShortUrl_DeveRetornarStatus200EUrlEncurtadaComNovosCampos() throws Exception {
        // given
        String validUrl = "https://www.example.com";
        String shortUrl = SHORT_URL_DOMAIN + "abc123";
        String title = "Example Title";
        String description = "Example Description";
        ShortUrlResponseDTO responseDTO = new ShortUrlResponseDTO(title, description, shortUrl);
        ShortUrlRequestDTO requestDTO = new ShortUrlRequestDTO(validUrl, title);
        when(shortenerService.shorten(validUrl)).thenReturn(responseDTO);

        // when & then
        mockMvc.perform(post(PATH + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.shortUrl").value(shortUrl))
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.description").value(description));
    }
}
