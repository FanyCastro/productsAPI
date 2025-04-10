package com.capitole.productsapi.infrastructure.web.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Standard error response returned by the API")
data class ErrorResponseDto(
    @Schema(
        description = "HTTP status code of the error",
        example = "400",
        required = true
    )
    val status: Int,

    @Schema(
        description = "Human-readable message describing the error",
        example = "Invalid request parameters",
        required = true
    )
    val message: String,

    @Schema(
        description = "List of specific error details (may be empty)",
        example = "[\"Invalid sortBy value: must be one of [sku, price, category]\", \"Page number cannot be negative\"]",
        required = true
    )
    val errors: List<String?>
)