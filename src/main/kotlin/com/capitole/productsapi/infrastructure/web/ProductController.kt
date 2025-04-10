package com.capitole.productsapi.infrastructure.web

import com.capitole.productsapi.application.ProductServiceImpl
import com.capitole.productsapi.infrastructure.web.dto.ErrorResponse
import com.capitole.productsapi.infrastructure.web.dto.ProductResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Product API", description = "Products management")
@Validated
class ProductController (
    private val productService: ProductServiceImpl
) {

    @Operation(
        summary = "Get paginated list of products",
        description = "Retrieves a paginated list of products with all their details. Results can be filtered and sorted."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved paginated list of products",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ProductResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad Request - Invalid parameters (sortBy, sortDirection, etc.)",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal Server Error",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @GetMapping
    fun getProducts(
        @Parameter(description = "Optional category filter", example = "electronics")
        @RequestParam(required = false)
        @Size(max = 50, message = "Category cannot exceed 50 characters")
        category: String?,

        @Parameter(
            description = "Property to sort by (available: sku, finalPrice, category)",
            example = "finalPrice"
        )
        @RequestParam(defaultValue = "sku")
        @Pattern(regexp = "sku|price|description|category",
            message = "SortBy must be one of: sku, price, description, category")
        sortBy: String,

        @Parameter(
            description = "Sort direction (ASC or DESC)",
            example = "DESC",
            schema = Schema(allowableValues = ["ASC", "DESC"])
        )
        @RequestParam(defaultValue = "asc")
        @Pattern(regexp = "asc|desc",
            message = "SortDirection must be 'asc' or 'desc'")
        sortDirection: String,

        @Parameter(description = "Page number (0-based)", example = "0")
        @RequestParam(defaultValue = "0")
        @Min(value = 0, message = "Page must be 0 or greater")
        page: Int,

        @Parameter(description = "Number of items per page", example = "10")
        @RequestParam(defaultValue = "10")
        @Min(value = 1, message = "Size must be 1 or greater")
        @Max(value = 100, message = "Size cannot exceed 100")
        size: Int

    ): ResponseEntity<ProductResponse> {
        val direction = Sort.Direction.fromString(sortDirection)
        val pageable = PageRequest.of(page, size, Sort.by(direction, sortBy))

        val products = productService.invoke(category, pageable)
        return ResponseEntity.ok(ProductResponse.from(products))
    }
}