package com.example.urlShortener.integration;

import com.example.urlShortener.core.constants.ApiMessages;
import com.example.urlShortener.core.constants.ApiPaths;
import com.example.urlShortener.dto.request.UrlMappingRequestDTO;
import com.example.urlShortener.factory.UrlMappingFactory;
import com.example.urlShortener.repository.UrlMappingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ShortenerControllerIntegrationTest extends BaseIntegrationTest {

    @Value("${shortener.domain}")
    private String SHORTENER_DOMAIN;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    @AfterEach
    void tearDown() {
        urlMappingRepository.deleteAll();
    }

    @Test
    @DisplayName("Should create shortened URL and persist in database")
    void shouldCreateShortenedUrlAndPersistInDatabase() throws Exception {
        // given
        UrlMappingRequestDTO requestDTO = UrlMappingFactory.createDefaultRequestDTO();

        // when & then
        mockMvc.perform(post(ApiPaths.SHORTENER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(UrlMappingFactory.DEFAULT_TITLE))
                .andExpect(jsonPath("$.longUrl").value(UrlMappingFactory.DEFAULT_LONG_URL))
                .andExpect(jsonPath("$.shortUrl").value(startsWith(SHORTENER_DOMAIN)));
    }

    @Test
    @DisplayName("Should return status 422 when title is blank")
    void shouldReturnStatus422_WhenTitleIsBlank() throws Exception {
        // given
        UrlMappingRequestDTO requestDTO = UrlMappingFactory.createRequestDTO("", UrlMappingFactory.DEFAULT_LONG_URL);

        // when & then
        mockMvc.perform(post(ApiPaths.SHORTENER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").value(ApiMessages.UNPROCESSABLE_ENTITY_MESSAGE))
                .andExpect(jsonPath("$.details").value(UrlMappingRequestDTO.TITLE_REQUIRED_MESSAGE));
    }

    @Test
    @DisplayName("Should return status 422 when longUrl is blank")
    void shouldReturnStatus422_WhenLongUrlIsBlank() throws Exception {
        // given
        UrlMappingRequestDTO requestDTO = UrlMappingFactory.createRequestDTO(UrlMappingFactory.DEFAULT_TITLE, "");

        // when & then
        mockMvc.perform(post(ApiPaths.SHORTENER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").value(ApiMessages.UNPROCESSABLE_ENTITY_MESSAGE))
                .andExpect(jsonPath("$.details").value(UrlMappingRequestDTO.LONG_URL_REQUIRED_MESSAGE));
    }

    @Test
    @DisplayName("Should return status 422 when longUrl is invalid")
    void shouldReturnStatus422_WhenLongUrlIsInvalid() throws Exception {
        // given
        UrlMappingRequestDTO requestDTO = UrlMappingFactory.createRequestDTO(UrlMappingFactory.DEFAULT_TITLE, "invalid-url");

        // when & then
        mockMvc.perform(post(ApiPaths.SHORTENER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").value(ApiMessages.UNPROCESSABLE_ENTITY_MESSAGE))
                .andExpect(jsonPath("$.details").value(UrlMappingRequestDTO.LONG_URL_INVALID_MESSAGE));
    }
}
