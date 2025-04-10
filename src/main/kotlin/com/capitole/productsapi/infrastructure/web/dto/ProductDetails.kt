package com.capitole.productsapi.infrastructure.web.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

@Schema(description = "Entity that represents a product with its details")
data class ProductDetails(
    @Schema(
        description = "Stock Keeping Unit - unique identifier for the product",
        example = "SKU0007",
        required = true
    )
    val sku: String,

    @Schema(
        description = "Original price of the product before any discounts",
        example = "1299.99",
        required = true
    )
    val originalPrice: BigDecimal,

    @Schema(
        description = "Final price in USD after applying discounts",
        example = "999.99",
        required = true
    )
    @Positive
    val finalPrice: BigDecimal,

    @Schema(
        description = "Percentage discount applied to the product",
        example = "23.5",
        required = true
    )
    val discountPercent: BigDecimal,

    @Schema(
        description = "Type of discount applied (if any), e.g., 'seasonal', 'clearance', 'promotional'",
        example = "seasonal",
        required = false
    )
    val discountType: String?,

    @Schema(
        description = "Detailed description of the product",
        example = "High-performance wireless headphones with noise cancellation",
        required = true
    )
    val description: String,

    @Schema(
        description = "Category the product belongs to",
        example = "electronics",
        required = true
    )
    val category: String,
)
