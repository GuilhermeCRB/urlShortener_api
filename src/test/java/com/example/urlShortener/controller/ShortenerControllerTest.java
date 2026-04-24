package com.example.urlShortener.controller;

import com.example.urlShortener.core.constants.ApiMessages;
import com.example.urlShortener.core.constants.ApiPaths;
import com.example.urlShortener.core.security.WebSecurityConfig;
import com.example.urlShortener.dto.request.UrlMappingRequestDTO;
import com.example.urlShortener.dto.response.UrlMappingResponseDTO;
import com.example.urlShortener.factory.UrlMappingFactory;
import com.example.urlShortener.service.impl.ShortenerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShortenerController.class)
@Import(WebSecurityConfig.class)
public class ShortenerControllerTest {

    @Value("${shortener.domain}")
    private String SHORTENER_DOMAIN;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ShortenerServiceImpl shortenerService;

    @Test
    @DisplayName("Should return status 201 and shortened URL when given a valid URL")
    void shouldReturnStatus201AndShortenedUrl_WhenGivenValidUrl() throws Exception {
        // given
        String shortUrl = SHORTENER_DOMAIN + "abc123";
        UrlMappingRequestDTO requestDTO = UrlMappingFactory.createDefaultRequestDTO();
        UrlMappingResponseDTO responseDTO = UrlMappingFactory.createDefaultResponseDTO(shortUrl);
        when(shortenerService.shorten(requestDTO)).thenReturn(responseDTO);

        // when & then
        mockMvc.perform(post(ApiPaths.SHORTENER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(UrlMappingFactory.DEFAULT_TITLE))
                .andExpect(jsonPath("$.longUrl").value(UrlMappingFactory.DEFAULT_LONG_URL))
                .andExpect(jsonPath("$.shortUrl").value(shortUrl));
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

    @Test
    @DisplayName("Should return status 500 when an unexpected error occurs")
    void shouldReturnStatus500_WhenUnexpectedErrorOccurs() throws Exception {
        // given
        UrlMappingRequestDTO requestDTO = UrlMappingFactory.createDefaultRequestDTO();
        when(shortenerService.shorten(any(UrlMappingRequestDTO.class))).thenThrow(new RuntimeException("Database connection failed"));

        // when & then
        mockMvc.perform(post(ApiPaths.SHORTENER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value(ApiMessages.INTERNAL_SERVER_ERROR_MESSAGE))
                .andExpect(jsonPath("$.details").value(ApiMessages.INTERNAL_SERVER_ERROR_DETAILS));
    }
}
