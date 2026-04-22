package com.example.urlShortener.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record UrlMappingRequestDTO(
        @NotBlank(message = "O título é obrigatório")
        String title,

        @NotBlank(message = "A URL original é obrigatória")
        @URL(message = "A URL original deve ser válida")
        @Schema(description = "URL longa a ser encurtada", example = "https://www.google.com.br")
        String longUrl
){}
