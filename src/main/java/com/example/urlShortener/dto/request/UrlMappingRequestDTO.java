package com.example.urlShortener.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record UrlMappingRequestDTO(
        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Original URL is required")
        @URL(message = "Original URL must be valid")
        @Schema(description = "Original URL to be shortened", example = "https://www.google.com.br")
        String longUrl
){}
