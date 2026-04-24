package com.example.urlShortener.integration;

import com.example.urlShortener.core.constants.ApiMessages;
import com.example.urlShortener.core.constants.ApiPaths;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HealthControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should return status 200 and success message")
    void shouldReturnStatus200AndSuccessMessage() throws Exception {
        mockMvc.perform(get(ApiPaths.HEALTH))
                .andExpect(status().isOk())
                .andExpect(content().string(ApiMessages.HEALTH_CHECK_MESSAGE));
    }
}
