package com.example.urlShortener.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record UrlMappingRequestDTO(
        @NotBlank(message = UrlMappingRequestDTO.TITLE_REQUIRED_MESSAGE)
        String title,

        @NotBlank(message = UrlMappingRequestDTO.LONG_URL_REQUIRED_MESSAGE)
        @URL(message = UrlMappingRequestDTO.LONG_URL_INVALID_MESSAGE)
        @Schema(description = "Original URL to be shortened", example = "https://www.google.com.br")
        String longUrl
){
    public static final String TITLE_REQUIRED_MESSAGE = "Title is required";
    public static final String LONG_URL_REQUIRED_MESSAGE = "Original URL is required";
    public static final String LONG_URL_INVALID_MESSAGE = "Original URL must be valid";
}
