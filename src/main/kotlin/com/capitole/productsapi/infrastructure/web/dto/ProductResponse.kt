package com.capitole.productsapi.infrastructure.web.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.domain.Page

@Schema(description = "Paginated response containing product details and metadata")
data class ProductResponse(
    @Schema(
        description = "List of product details in the current page",
        required = true
    )
    val content: List<ProductDetails>,

    @Schema(
        description = "Current page number (0-based index)",
        example = "0",
        required = true
    )
    val page: Int,

    @Schema(
        description = "Number of items per page",
        example = "10",
        required = true
    )
    val size: Int,

    @Schema(
        description = "Total number of products across all pages",
        example = "125",
        required = true
    )
    val totalElements: Long,

    @Schema(
        description = "Total number of pages available",
        example = "13",
        required = true
    )
    val totalPages: Int,

    @Schema(
        description = "Whether this is the last page of results",
        example = "false",
        required = true
    )
    val last: Boolean
) {
    companion object {
        @Schema(hidden = true)
        fun from(page: Page<ProductDetails>): ProductResponse {
            return ProductResponse(
                content = page.content,
                page = page.number,
                size = page.size,
                totalElements = page.totalElements,
                totalPages = page.totalPages,
                last = page.isLast
            )
        }
    }
}