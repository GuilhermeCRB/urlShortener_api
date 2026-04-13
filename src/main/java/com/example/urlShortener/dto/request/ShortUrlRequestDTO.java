package com.example.urlShortener.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record ShortUrlRequestDTO (
    @NotBlank(message = "A URL original é obrigatória")
    @Schema(description = "URL longa a ser encurtada", example = "https://www.google.com.br")
    String longUrl
){}
