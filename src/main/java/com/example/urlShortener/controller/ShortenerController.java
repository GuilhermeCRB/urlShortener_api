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
    @Operation(
            summary = "Generate Short URL",
            description = "Generates a short URL from a provided long URL."
    )
    @ApiResponse(
            description = "Short URL created successfully",
            responseCode = "201",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UrlMappingResponseDTO.class),
                    examples = @ExampleObject(
                            value = """
                                {
                                  "title": "Shortened URL Example",
                                  "longUrl": "http://www.example.com/a-very-long-url",
                                  "shortUrl": "http://www.shortexample.com.br/abc123"
                                }
                            """
                    )
            )
    )
    @ApiResponse(
            description = "Unprocessable Entity - Invalid URL",
            responseCode = "422",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Error.class),
                    examples = @ExampleObject(
                            value = """
                                {
                                  "status": 422,
                                  "message": "Invalid URL"
                                }
                            """
                    )
            )
    )
    @ApiResponse(
            description = "Internal Server Error",
            responseCode = "500",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Error.class),
                    examples = @ExampleObject(
                            value = """
                                {
                                  "status": 500,
                                  "message": "An unexpected error occurred"
                                }
                            """
                    )
            )
    )
    ResponseEntity<UrlMappingResponseDTO> generateShortUrl(@RequestBody UrlMappingRequestDTO request);
}
