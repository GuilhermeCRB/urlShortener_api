package com.example.urlShortener.controller;

import com.example.urlShortener.dto.request.UrlMappingRequestDTO;
import com.example.urlShortener.dto.response.UrlMappingResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface ShortenerController {
    @Operation(summary = "Gerar URL Curta",
            description = "Gera uma URL curta a partir de uma URL longa fornecida.")
    @ApiResponse(
            responseCode = "201",
            description = "A comunicação com o serviço foi realizada com sucesso e a URL curta foi gerada.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UrlMappingResponseDTO.class),
                    examples = @ExampleObject(
                            value = """
                                {
                                  "title": "Exemplo de URL Encurtada",
                                  "longUrl": "http://www.exemplo.com/uma-url-muito-longa",
                                  "shortUrl": "http://www.zg.com.br/abc123"
                                }
                            """
                    )
            )
    )
    @ApiResponse(
            responseCode = "422",
            description = "Erro de validação",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Error.class),
                    examples = @ExampleObject(
                            value = """
                                {
                                  "status": 422,
                                  "message": "URL inválida"
                                }
                            """
                    )
            )
    )
    @ApiResponse(
            responseCode = "500",
            description = "Erro interno no servidor"
    )
    ResponseEntity<UrlMappingResponseDTO> generateShortUrl(@RequestBody UrlMappingRequestDTO request);
}
