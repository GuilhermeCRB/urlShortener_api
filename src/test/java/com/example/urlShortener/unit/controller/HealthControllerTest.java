package com.example.urlShortener.unit.controller;

import com.example.urlShortener.controller.HealthController;
import com.example.urlShortener.core.constants.ApiMessages;
import com.example.urlShortener.core.constants.ApiPaths;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HealthController.class)
public class HealthControllerTest extends BaseControllerTest {

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
